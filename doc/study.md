# long-running task & thread

뷰모델이 아닌 곳에서 스레드를 사용해야 하는 경우가 있다.  
예를 들어, 네트워크 요청이나 파일 다운로드 등이다.
이런 경우에는 코루틴을 사용하거나 RxJava를 사용하는 것이 좋다.  
하지만, 때로는 스레드를 직접 다루어야 하는 경우도 있다.

그런데 아래처럼 할 수 없다.

``` kotlin
class RemoteProductDataSource(
    private val productApiService: ProductApiService = MockProductApiService(),
) : ProductDataSource {
    override fun findByPaged(page: Int): List<ProductData> {
        var result: List<ProductData>
        thread {
            result = productApiService.loadPaged(page)
        }.join()
        return result // ERROR : Variable 'result' must be initialized
    }
}
// 위처럼은 안됨.
```

이런 경우에는 `Thread` 클래스를 사용하거나 `Executor`를 사용하는 것이 일반적이다.

예전에 코루틴을 공부할 때 RxJava 관련 글을 본적이 있다.  
이 때 RxJava 에서는 무슨 callback 이 어쩌구 저쩌구 했었는데...?

## 리뷰어의 리뷰

> 추가적으로 ViewModel 에서 thread {} 를 생성할 필요가 있었을까 싶습니다!
> 데이터를 가져오느라 들어가는 부분인데, 역활로 하자면 DataSource에 들어가야 하지 않을까 라는 생각이 듭니다!🤔

뷰모델에서 스레드를 생성하는 것이 맞는지에 대한 의문이 든다.  
뷰모델에서 스레드를 사용하지 않고 스레드를 사용하는 곳을 최대한 다른 계층으로 옮긴다면 (예를 들어 레포지토리나 데이터소스로) 더 좋은 설계가 될 것 같다.  
왜냐하면 뷰모델에서 어떠한 메서드를 실행할 때 이 메서드가 스레드가 필요한 동작인지 아닌지는 뷰모델이 알 필요가 없다.  
뷰모델 메서드 내부에 스레드를 여는 것이 있는 것조차 "이 동작이 오래 걸리는 동작이다, 혹은 스레드가 필요한 동작이다" 라는 정보를 노출하는 것이다.

여러 정보를 찾아보니까 데이터 소스가 LiveData 로 데이터를 감싼 타입을 리턴하는 것이 있다.  
그런데 이것은 별로인 것 같다.  
왜냐하면 라이브데이터는 안드 프레임워크에 의존적이다.  
그래서 레포지토리나, 데이터 소스가 다른 프레임워크에서 사용할 수 없는 코드가 된다.

다른 솔루션을 많이 찾아보았는데 외부 라이브러리를 사용하지 않으면서, 코루틴을 사용하지 않으면서는 뷰모델에서 직접 스레드를 사용하지 않는 방법은 없는 것 같다.  
~~(물론 있을 수도 있음. 내가 못 찾을 걸지도... )~~

그런데 callback 을 사용하는 것이 눈에 띈다.  
이런 형태로 사용할 수 있을 것 같다.

```kotlin
class LocalHistoryProductDataSource(private val dao: HistoryProductDao) : ProductHistoryDataSource {
    override fun saveProductHistory(productId: Long, callback: (Boolean) -> Unit) {
        thread {
            val id = dao.findById(productId)

            if (id != null) {
                dao.delete(id)
            }

            dao.insert(HistoryProduct(productId))
            callback(true)
        }
    }
    // ...
}
```

이렇게 해보자.

# 이거 된다!!!!!!!!!!!!!!

이제 이거로 모두 바꾸면서 테스트도 리팩토링 해보자!  
만약 위처럼 한다면 어떻게 테스트를 작성해야 하는가

데이터 소스, 레포지토리가 따로 있다고 하면

```kotlin
class DefaultProductHistoryRepository(
    private val productHistoryDataSource: ProductHistoryDataSource,
    private val productDataSource: ProductDataSource,
) : ProductHistoryRepository {
    override fun saveProductHistoryAsync(productId: Long, callback: (Boolean) -> Unit) {
        productHistoryDataSource.saveProductHistoryAsync(productId, callback)
    }
    // ..
}
```

### 이 경우 테스트에서는 어떻게 해야 할까?

```kotlin
@Test
fun `내역에 없는 상품을 저장 async`() {
    // given setup
    val product = productTestFixture(5)

    // when
    productHistoryRepository.saveProductHistoryAsync(product.id) {
        // then
        productHistoryRepository.loadAllProductHistoryAsync { products ->
            assertThat(products).hasSize(4)
        }
    }
}

@Test
fun `이미 내역에 있는 상품을 저장하려고 하면 저장 안됨 aync`() {
    // given setup
    val product = productTestFixture(3)

    // when
    productHistoryRepository.saveProductHistoryAsync(product.id) {
        // then
        productHistoryRepository.loadAllProductHistoryAsync { products ->
            assertThat(products).hasSize(3)
        }
    }
}

@Test
fun `가장 최근 상품 검색 async 2`() {
    // given setup
    // when
    productHistoryRepository.loadLatestProductAsync { productId ->
        assertThat(productId).isEqualTo(-9999999) // 아무튼 테스트 실패해야 하는 경우
    }
}

```

위 경우 테스트는 제대로 수행되지 않는다.  
실제로는 아예 assertion 문장이 실행되지도 않는다.
![callback 에서 assertion 실행 시.png](callback%20%EC%97%90%EC%84%9C%20assertion%20%EC%8B%A4%ED%96%89%20%EC%8B%9C.png)
![실제로는 assertion 문장이 제대로 실행되지 않음.png](%EC%8B%A4%EC%A0%9C%EB%A1%9C%EB%8A%94%20assertion%20%EB%AC%B8%EC%9E%A5%EC%9D%B4%20%EC%A0%9C%EB%8C%80%EB%A1%9C%20%EC%8B%A4%ED%96%89%EB%90%98%EC%A7%80%20%EC%95%8A%EC%9D%8C.png)

테스트 코드가 제대로 작동하지 않는 이유는 두 가지 테스트 모두 비동기 방식으로 실행되기 때문이다.  
각각의 saveProductHistoryAsync와 loadAllProductHistoryAsync 호출이 비동기적이기 때문에 **콜백이 실행되기 전에 테스트가 종료될 수 있기 때문**입니다.  
이로 인해 assertThat 구문이 실행되지 않거나, 올바른 시점에 실행되지 않을 수 있습니다.

이를 해결하기 위해 CountDownLatch를 사용하여 비동기 작업이 완료될 때까지 대기하는 방식을 적용해보겠습니다.  
이렇게 하면 테스트 코드가 비동기 작업이 완료된 후 검증을 진행할 수 있게 됩니다.

```kotlin
@Test
fun `가장 최근 상품 검색 async`() {
    // given setup
    val latch = CountDownLatch(1)
    var result: Long = 0
    // when
    productHistoryRepository.loadLatestProductAsync { productId ->
        result = productId
        latch.countDown()
    }
    latch.await()

    assertThat(result).isEqualTo(20000)
}

// 혹은


```

assert

그리고 프로덕션 코드에서는 아래처럼 callback 에서 ui 의 상태를 담는 LiveData 를 변경해주면 될 것 같다.

```kotlin
fun loadAll() {
    val page = currentPage.value ?: currentPageIsNullException()
    productsRepository.loadAllProductsAsync(page) { products ->
        _loadedProducts.postValue(products)
    }

    productsRepository.isFinalPageAsync(page) {
        _isLastPage.postValue(it)
    }

    productsRepository.shoppingCartProductQuantityAsync { count ->
        _cartProductTotalCount.postValue(count)
    }

    productHistoryRepository.loadAllProductHistoryAsync { productHistory ->
        _productsHistory.postValue(productHistory)
    }
}

```

# 의문점

뷰모델에서 스레드를 연다면 아래가 되는 구나

```kotlin
thread {
    val loadAllProducts = productsRepository.loadAllProducts(page)
    val isLastPage = productsRepository.isFinalPage(page)
    val shoppingCartProductQuantity = productsRepository.shoppingCartProductQuantity()
    val loadAllProductHistory = productHistoryRepository.loadAllProductHistory()

    _loadedProducts.postValue(loadAllProducts)
    _isLastPage.postValue(isLastPage)
    _cartProductTotalCount.postValue(shoppingCartProductQuantity)
    _productsHistory.postValue(loadAllProductHistory)
}.join()

```

### 뷰모델이 아닌 데이터 소스에서 thread 를 연다면?

아래처럼 되면 에러가 뜸. 이렇게 못한다..

```kotlin
override fun loadPaged(page: Int): List<ProductIdsCountData> {
    val offset = (page - 1) * 5
    var result: List<ProductIdsCountData>

    thread {
        result = dao.findPaged(offset)
    }.join()

    return result // Variable 'result' must be initialized
}

// 혹은
override fun findByPaged(page: Int): List<ProductData> {
    var result: List<ProductData>
    val thread = thread {
        result = productApiService.loadPaged(page)
    }
    thread.join()
    return result
}
```

아 아래처럼 이렇게 하면 되나?

```kotlin
override fun loadProductHistory(productId: Long): Long? {
    var result: Long? = null
    val thread = thread {
        result = dao.findById(productId)?.id
    }
    thread.join()
    return result
}
```

# Result

Result 로 감싼 값과 위에서의 callback 을 같이 사용하려니까 헷갈리네.

아래 두 코드의 차이점은 매우 미묘하지만 중요한 차이점이 있습니다.  
첫 번째 코드는 `runCatching`을 사용하여 예외를 포착하고, 그 결과를 `callback` 함수에 전달합니다.  
두 번째 코드는 `runCatching` 안에서 `findByProductId` 메서드를 호출한 후, `let`을 사용하여 `callback` 함수를 전달하지만, 이는 의도한 대로 동작하지 않을 수 있습니다.

```kotlin
override fun findByProductIdAsyncResult(productId: Long, callback: (Result<ProductIdsCountData?>) -> Unit) {
    thread {
        callback(
            runCatching {
                findByProductId(productId)
            }
        )
    }
}
```

첫 번째 코드는 `runCatching`을 사용하여 예외를 포착하고, 그 결과를 `callback` 함수에 전달합니다.

이 코드는 `findByProductId`를 호출한 결과를 `runCatching`으로 감싸서 `callback`에 전달합니다.  
`runCatching`은 예외가 발생하면 이를 `Result` 객체로 포장하여 반환합니다.  
따라서, `callback` 함수는 항상 `Result` 객체를 받게 됩니다.

```kotlin
override fun findByProductIdAsyncResult(productId: Long, callback: (Result<ProductIdsCountData?>) -> Unit) {
    thread {
        runCatching {
            findByProductId(productId).let { callback }
        }
    }
}
```

두 번째 코드는 `runCatching` 안에서 `findByProductId` 메서드를 호출한 후, `let`을 사용하여 `callback` 함수를 전달하지만, 이는 의도한 대로 동작하지 않을 수 있습니다.

이 코드는 `runCatching` 블록 내에서 `findByProductId`를 호출하고,그 결과를 `let`을 통해 `callback`에 전달하려고 합니다.  
그러나 이 방법은 의도한 대로 동작하지 않습니다.  
`let` 블록의 인자로 `callback` 함수를 전달하지만, `callback`을 호출하지 않고 단순히 참조를 넘깁니다.

### 올바른 두 번째 코드 수정

두 번째 코드의 의도를 반영하기 위해서는 `callback`을 호출해야 합니다. 올바르게 수정된 코드는 다음과 같습니다:

```kotlin
override fun findByProductIdAsyncResult(productId: Long, callback: (Result<ProductIdsCountData?>) -> Unit) {
    thread {
        runCatching {
            findByProductId(productId)
        }.let(callback)
    }
}

override fun findByProductIdAsyncResult(productId: Long, callback: (Result<ProductIdsCountData?>) -> Unit) {
    thread {
        runCatching {
            findByProductId(productId)
        }.let(callback)
    }
}


```

### 요약

1. **첫 번째 코드**는 `runCatching`으로 `findByProductId` 호출을 감싸고 그 결과를 직접 `callback`에 전달합니다.
2. **두 번째 코드**는 `runCatching` 블록 내에서 `findByProductId` 호출 후 `callback`을 호출하려 했으나, 잘못된 `let` 사용으로 인해 올바르게 동작하지 않습니다. 이를
   수정하여 `let` 블록 내에서 `callback`을 호출하도록 해야 합니다.

수정된 두 번째 코드와 첫 번째 코드는 기능적으로 동일하게 동작합니다. 각각의 예외를 처리하고 그 결과를 `callback`에 전달합니다.

스레드를 만들어서 어떤 것을 호출하는데, 그 안에서 또 다른 스레드를 열어서 호출한느 경우는?
예를 들어서 아래와 같은 경우에

```kotlin
override fun loadAllProductHistory(): List<Product> {
    val productIds = productHistoryDataSource.loadAllProductHistory()
    return productIds.map {
        productDataSource.findById(it).toDomain(quantity = 0)
    }
}
```

이 때 productHistoryDataSource.loadAllProductHistory() 의 경우 내부에서 스레드를 열어서 동작하게 만들 수도 잇따.  
그리고 productDataSource.findById(it) 의 경우도 마찬가지로 스레드를 열어서 동작하게 만들 수도 있다.

```kotlin
fun loadAllProductHistoryAsync(callback: (List<Long>) -> Unit)
```

```kotlin
fun findByIdAsync(
    id: Long,
    callback: (ProductData) -> Unit,
)
```

```kotlin
override fun loadAllProductHistoryAsync(callback: (List<Product>) -> Unit) {
    productHistoryDataSource.loadAllProductHistoryAsync { ids ->
        val products: MutableList<Product> = synchronizedList(mutableListOf())
        val remaining = AtomicInteger(ids.size)
        ids.map { id ->
            productDataSource.findByIdAsync(id) {
                products.add(it.toDomain())
                if (remaining.decrementAndGet() == 0) {
                    callback(products.toList())
                }
            }
        }
    }
}
```

이렇게 하면 `productHistoryDataSource.loadAllProductHistoryAsync` 를 사용했을 때 워커 스레드에서 동작하는 것과  
그리고 `productDataSource.findByIdAsync` 가 워커 스레드에서 동작함에도 불구하고,  
완전히 리스트에 추가되었을 때 콜백이 호출되도록 할 수 있다.

그런데 리스트에 추가되는 순서가 보장되지 않는다.  
원래는 0, 1, 2, 3 순서로 추가되어야 하지만, 0, 1, 3, 2 순서로 추가될 수도 있다.

이러한 경우를 방지하려면 인덱스 기반으로 삽입해야 한다고 한다..

```kotlin
override fun loadAllProductHistoryAsync(callback: (List<Product>) -> Unit) {
    productHistoryDataSource.loadAllProductHistoryAsync { ids ->
        val products = synchronizedList(List(ids.size) { Product.NULL })
        val remaining = AtomicInteger(ids.size)

        ids.forEachIndexed { index, id ->
            productDataSource.findByIdAsync(id) { product ->
                products[index] = product.toDomain(quantity = 0)

                if (remaining.decrementAndGet() == 0) {
                    callback(products.filterNotNull())
                }
            }
        }
    }
}
```

여기다가 Result 까지 감싸서 리턴을 해준다고 하면?

```kotlin
override fun loadAllProductHistoryAsyncResult(callback: (Result<List<Product>>) -> Unit) {
    productHistoryDataSource.loadAllProductHistoryAsyncResult { resultLoadAllIds ->
        resultLoadAllIds.onSuccess { ids ->
            val products = synchronizedList(List(ids.size) { Product.NULL })
            val remaining = AtomicInteger(ids.size)

            ids.forEachIndexed { index, id ->
                productDataSource.findByIdAsyncResult(id) { resultIdProduct ->
                    resultIdProduct.onSuccess { productdata ->
                        products[index] = productdata.toDomain(quantity = 0)
                        if (remaining.decrementAndGet() == 0) {
                            callback(Result.success(products.toList()))
                        }
                    }
                    resultIdProduct.onFailure {
                        callback(Result.failure(it))
                    }
                }
            }
        }
            .onFailure {
                callback(Result.failure(it))
            }
    }
}

```

가독성 문제

```kotlin
override fun addedNewProductsIdAsyncResult(
    productIdsCountData: ProductIdsCountData,
    callback: (Result<Unit>) -> Unit
) {
    thread {
        runCatching {
            dao.insert(productIdsCountData).let(callback)
        }
    }
}
```

VS

```kotlin
override fun addedNewProductsIdAsyncResult(
    productIdsCountData: ProductIdsCountData,
    callback: (Result<Long>) -> Unit
) {
    thread {
        callback(
            runCatching {
                dao.insert(productIdsCountData)
            }
        )
    }
}
```

# ViewModel 의 추상 클래스와 구현체 분리

ViewModel 의 추상 클래스가 프로퍼티를 LiveData 로 가지게 하고,  
구현체에서는 해당 프로퍼티를 MutableLiveData 로 오버리이드 한다.

그리고 프래그먼트에서는 추상 클래스에 의존하도록 한다.  
이렇게 되면 viewmodel 에서 `_state: MutableLiveData, state: LiveData` 로 가지는 것을 없앨 수 있다.


> ?? 그렇게 되면 프래그먼트에서 viewModel 의 state 를 변경할 수 있는 거 아닌가요?

일반적인 방법으로는 불가능하다.  
왜냐하면 프래그먼트는 뷰모델 구현체가 아닌, 뷰모델 추상 클래스에 의존하기 때문에  
프래그먼트가 바라보는 뷰모델의 프로퍼티는 LiveData 타입이다.

물론 아래처럼은 가능한다.

```kotlin
(viewModel as DefaultProductListViewModel).uiState.loadedProducts.value = emptyList()
```

물론 아래처럼은 불가능

```kotlin
viewModel.uiState.loadedProducts.value = emptyList() // cannot assign to value: 
```

> ?? 그러면 안되는 거 아닌가요????
> 기존에는 그렇게 못했던 것 같은데..?

사실 기존에도 그렇게 할 수 있었다.

```kotlin
(viewModel.uiState as MutableLiveData).value = emptyList()
```

즉, 이렇게 해도 문제 없으며 효과적으로 `_state: MutableLiveData, state: LiveData` 의 보일러 플레이트를 없앨 수 있다.

# [해결 못함] 뷰모델에서 시나리오 테스트 🔥

뮤모델에서 시나리오 테스트를 하고 싶어서 여러 메서드를 호출한다면, 어떻게 해야 할까?

```kotlin
@Test
fun `장바구니에 아무것도 들어가 있지 않을 때 첫 페이지에서 다음 페이지 더보기를 누르면 상품 40개 로드`() {
    // given setup
    viewModel = DefaultProductListViewModel(shoppingProductRepository, historyRepository)

    // when
    viewModel.loadAll()

    viewModel.loadNextPageProducts()


    val loadedProducts = viewModel.uiState.loadedProducts.getOrAwaitValue()
    assertThat(loadedProducts).isEqualTo(
        productDomainsTestFixture(40)
    )
}
```

위와 같은 경우 loadedProducts 의 getOrAwaitValue 는   
viewModel.loadAll() 직후의 값을 가져오거나, viewModel.loadNextPageProducts() 직후의 값을 가져온다.  
그래서 테스트가 실패할 수도 있고 성공할 수도 있게 된다.

# ViewModelFactory 부분

두개 차이 비교

```kotlin
private val factory: UniversalViewModelFactory = DefaultProductListViewModel.factory()

private val viewModel1: ProductListViewModel by lazy {
    ViewModelProvider(this, factory)[ProductListViewModel::class.java]
}
```

```kotlin
/**
 * Creates `ViewModelProvider`, which will create `ViewModels` via the given
 * `Factory` and retain them in a store of the given `ViewModelStoreOwner`.
 *
 * @param owner   a `ViewModelStoreOwner` whose [ViewModelStore] will be used to
 * retain `ViewModels`
 * @param factory a `Factory` which will be used to instantiate
 * new `ViewModels`
 */
public constructor (owner: ViewModelStoreOwner, factory: Factory) : this(
    owner.viewModelStore,
    factory,
    defaultCreationExtras(owner)
)
```

프래그먼트가  `ViewModelProvider` 를 사용하여 `ViewModel` 을 생성하고 초기화할 때,  
`ViewModelProvider` 는 `ViewModelStoreOwner` 와 `Factory` 를 인자로 받는다.  
`ViewModelStoreOwner` 는 `ViewModel` 을 저장할 `ViewModelStore` 를 제공,  
`Factory` 는 `ViewModel` 을 생성하고 초기화하는 방법을 제공한다.
바로 그게 `ViewModelProvider(this, DefaultProductListViewModel.factory())`

그리고 뒤에 있는 [ProductListViewModel::class.java] 는 생성할 `ViewModel` 의 타입을 지정한다.  
아래의 get 을 operator function 으로 오버라이드한 것을 사용하는 것이다.

```kotlin
/**
 * Returns an existing ViewModel or creates a new one in the scope (usually, a fragment or
 * an activity), associated with this `ViewModelProvider`.
 *
 *
 * The created ViewModel is associated with the given scope and will be retained
 * as long as the scope is alive (e.g. if it is an activity, until it is
 * finished or process is killed).
 *
 * @param modelClass The class of the ViewModel to create an instance of it if it is not
 * present.
 * @return A ViewModel that is an instance of the given type `T`.
 * @throws IllegalArgumentException if the given [modelClass] is local or anonymous class.
 */
@MainThread
public open operator fun <T : ViewModel> get(modelClass: Class<T>): T {
    val canonicalName = modelClass.canonicalName
        ?: throw IllegalArgumentException("Local and anonymous classes can not be ViewModels")
    return get("$DEFAULT_KEY:$canonicalName", modelClass)
}
```

그렇다면 아래는?

```kotlin
private val viewModel by viewModels<ProductListViewModel> {
    DefaultProductListViewModel.factory()
}
```

먼저 viewModels<ProductListViewModel> 을 보자.

```kotlin
@MainThread
public inline fun <reified VM : ViewModel> Fragment.viewModels(
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    noinline extrasProducer: (() -> CreationExtras)? = null,
    noinline factoryProducer: (() -> Factory)? = null
): Lazy<VM> {
    val owner by lazy(LazyThreadSafetyMode.NONE) { ownerProducer() }
    return createViewModelLazy(
        VM::class,
        { owner.viewModelStore },
        {
            extrasProducer?.invoke()
                ?: (owner as? HasDefaultViewModelProviderFactory)?.defaultViewModelCreationExtras
                ?: CreationExtras.Empty
        },
        factoryProducer ?: {
            (owner as? HasDefaultViewModelProviderFactory)?.defaultViewModelProviderFactory
                ?: defaultViewModelProviderFactory
        })
}
```

위 코드는 `Fragment` 에서 `ViewModel` 을 생성하고 초기화하는 방법을 제공하는 확장 함수이다.  
`viewModels` 함수는 `Fragment` 의 `ViewModelStoreOwner` 를 사용하여 `ViewModel` 을 생성하고 초기화한다.  
기본 값은 this 이며 `Fragment` 를 의미한다.  
참고로 프래그먼트는 기본적으로 ViewModelStoreOwner 를 구현하고 있다. (그래서 this 로 가능한 것.)  
extrasProducer 는 CreationExtras 를 생성하는 람다 함수이다.  
ViewModel 을 생성할 때 추가적인 설정을 제공하는 것인데 모르겠으니까 넘어가자.

### 결론

Android KTX의 viewModels 확장 함수를 사용하여 더 간단하게 ViewModel을 생성합니다.  
코드가 간결하며, 자동으로 Fragment나 Activity의 범위 내에서 ViewModel을 관리합니다.
둘 다 ViewModel을 생성하고 사용할 수 있지만, viewModel2가 더 간결하고 사용하기 쉬운 구문을 제공합니다.  
특히 Android KTX 라이브러리를 사용하는 경우 viewModel2 접근 방식을 선호할 수 있습니다.

# toolbar 의 navigation icon 에 data binding 적용하기

# replace()와 add(), addToBackStack()의 동작과 차이점. 해당 이슈 관련 오류

### replace()

replace() 메서드는 FragmentTransaction을 통해 Fragment를 교체하는 메서드입니다.
replace() 메서드를 사용하면 Fragment를 교체할 때 이전 Fragment를 백 스택에 추가하지 않습니다.
따라서 이전 Fragment를 백 스택에 추가하려면 addToBackStack() 메서드를 사용해야 합니다.

### addToBackStack()

addToBackStack() 메서드는 Fragment를 백 스택에 추가하는 메서드입니다.

내가 단단히 착각하고 있었다...

fragment 를 replace 할 때 addToBackStack() 를 사용하고 commit 을 하면,  
이전 fragment 가 백스택에 추가되는 것이 아니라, 현재 fragment 가 백스택에 추가되는 것이다.

코드로 보면

```kotlin
fun navigateToShoppingCart() {
    supportFragmentManager.commit {
        replace(R.id.container, ShoppingCartFragment::class.java, null, ShoppingCartFragment.TAG)
        addToBackStack(DefaultProductListFragment.TAG)
    }
}
```

위와 같은 함수가 있다고 하자.  
이 때 addToBackStack(DefaultProductListFragment.TAG) 를 사용하면,  
ShoppingCartFragment 가 백스택에 추가되는 것이 아니라, DefaultProductListFragment 가 백스택에 추가된다.  
**현재 트랜잭션** 이 백스택에 추가되는 것!!

# DefaultProductDetailViewModelTest 의 최근 상품이 없으면 fake 객체 가 깨지는 이유

viewModel 의 함수 loadAll 은 세 함수를 부른다

```kotlin
fun loadAll() {
    loadCurrentProduct()
    loadLatestProduct()
    saveCurrentProductInHistory()
}

```

loadLatestProduct 는 history 에서 값을 가져와서 사용한다.  
그런데 이 때 saveCurrentProductInHistory 가 먼저 불리고 그 후에 loadLatestProduct 를 부르게 된다면??  
history 에 값이 먼저 들어ㅏ게 되고, loadLatestProduct 에서 이를 가져오게 되므로  
테스트가 깨지게 된다......   
스레드 가지고 하는 테스트 너무나 어렵다......

# async result 마무리 작업

```kotlin
override fun loadProductsInCartAsyncResult(page: Int, callback: (Result<List<Product>>) -> Unit) {
    cartSource.loadPagedAsyncResult(page) { cartResult ->
        cartResult.map { cartsData ->
            cartsData.map { cartData ->
                productsSource.findByIdAsyncResult(cartData.productId) { productResult ->
                    productResult.onSuccess { productData ->
                        productData.toDomain(cartData.quantity)
                    }
                }
            }
        }
    }
}
```
왜 안될까.


# ExecutorService?

직접 스레드를 생성하고 관리하는 복잡한 작업을 피할 수 있습니다.  
대신, 스레드 풀을 통해 작업을 제출하고 실행하며, 이를 통해 시스템 리소스를 효율적으로 사용할 수 있습니다.  
Runnable 또는 Callable 작업을 ExecutorService에 제출할 수 있습니다.  
작업 제출 시 Future 객체를 반환받아 작업의 결과나 상태를 확인할 수 있습니다.

## 스레드 풀

스레드 풀이란 여러 스레드 인스턴스를 미리 생성해 두고,  
작업을 실행할 때마다 새로운 스레드를 생성하는 대신 이들 스레드를 재사용하는 기법입니다.

스레드 풀이 시스템 자원을 효율적으로 사용하고, 스레드 생성 및 소멸의 오버헤드를 줄여줍니다.

### shutdown 및 termination

shutdown() 메서드를 호출하면 더 이상 새로운 작업을 받지 않지만, 이미 제출된 작업들은 완료될 때까지 실행됩니다.  
shutdownNow() 메서드는 가능한 한 즉시 실행 중인 작업을 중단하고 종료를 시도합니다.  
awaitTermination() 메서드는 모든 작업이 완료될 때까지 대기합니다.

### Memory consistency effects:

"happen-before" 관계가 보장됩니다.  
즉, 스레드가 Runnable 또는 Callable 작업을 ExecutorService에 제출하기 전에  
수행한 모든 작업은 해당 작업 내의 모든 작업보다 먼저 발생합니다.  
또한, 해당 작업의 결과를 Future.get()을 통해 가져오기 전에 작업 내의 모든 작업이 완료됩니다.