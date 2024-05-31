
# long-running task & thread

뷰모델이 아닌 곳에서 스레드를 사용해야 하는 경우가 있다.  
예를 들어, 네트워크 요청이나 파일 다운로드 등이다.
이런 경우에는 코루틴을 사용하거나 RxJava를 사용하는 것이 좋다.  
하지만, 때로는 스레드를 직접 다루어야 하는 경우도 있다.   
이런 경우에는 `Thread` 클래스를 사용하거나 `Executor`를 사용하는 것이 일반적이다.  

예전에 코루틴을 공부할 때 RxJava 관련 글을 본적이 있다.  
이 때 RxJava 에서는 무슨 callback 이 어쩌구 저쩌구 했었는데...?

리뷰어의 리뷰  
> 추가적으로 ViewModel 에서 thread {} 를 생성할 필요가 있었을까 싶습니다!
데이터를 가져오느라 들어가는 부분인데, 역활로 하자면 DataSource에 들어가야 하지 않을까 라는 생각이 듭니다!🤔

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

# 이거 된다!!!!!!!!!!!!!!1
이제 이거로 모두 바꾸면서 테스트도 리팩토링 해보자!  
만약 위처럼 한다면 어떻게 테스트를 작성해야 하는가

데이터 소스, 레포지토리가 따로 있다고 하면
``` kotlin
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
   
테스트에서는
``` kotlin

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
```

