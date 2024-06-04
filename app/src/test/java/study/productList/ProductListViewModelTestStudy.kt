package woowacourse.shopping.productList

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import study.ProductDataSourceStudy
import study.ProductHistoryDataSourceStudy
import study.ShoppingCartProductIdDataSourceStudy
import study.repository.DefaultProductHistoryRepositoryStudy
import study.repository.DefaultShoppingProductRepositoryStudy
import study.repository.ProductHistoryRepositoryStudy
import study.repository.ShoppingProductsRepositoryStudy
import study.source.FakeProductDataSourceStudy
import study.source.FakeShoppingCartProductIdDataSourceStudy
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.model.toDomain
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.productsTestFixture
import woowacourse.shopping.source.FakeProductHistorySourceStudy
import woowacourse.shopping.testfixture.productDomainsTestFixture
import woowacourse.shopping.testfixture.productsIdCountDataTestFixture
import woowacourse.shopping.ui.productList.DefaultProductListUiState
import woowacourse.shopping.ui.productList.ProductListUiState
import woowacourse.shopping.ui.productList.ProductListViewModel
import woowacourse.shopping.ui.productList.event.ProductListError
import woowacourse.shopping.ui.productList.event.ProductListEvent
import woowacourse.shopping.ui.util.MutableSingleLiveData
import java.util.concurrent.CountDownLatch

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductListViewModelTestStudy {
    private lateinit var productSource: ProductDataSourceStudy
    private lateinit var cartSource: ShoppingCartProductIdDataSourceStudy
    private lateinit var historyDataSource: ProductHistoryDataSourceStudy

    private lateinit var shoppingProductRepository: ShoppingProductsRepositoryStudy
    private lateinit var historyRepository: ProductHistoryRepositoryStudy

    private lateinit var viewModel: DefaultProductListViewModelStudy

    /**
     * setup 에서 장바구니에는 아무런 데이터도 없도록 만든다
     */
    @BeforeEach
    fun setUp() {
        productSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(60).toMutableList(),
            )
        cartSource = FakeShoppingCartProductIdDataSourceStudy()
        historyDataSource = FakeProductHistorySourceStudy()

        shoppingProductRepository = DefaultShoppingProductRepositoryStudy(productSource, cartSource)
        historyRepository = DefaultProductHistoryRepositoryStudy(historyDataSource, productSource)
    }


    @Test
    fun `장바구니에 아무것도 들어가 있지 않을 때 상품 20개 로드`() {
        // given setup
        viewModel = DefaultProductListViewModelStudy(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()

        // then
        val loadedProducts = viewModel.uiState.loadedProducts.getOrAwaitValue()
        assertThat(loadedProducts).isEqualTo(
            productsTestFixture(20).map { it.toDomain(0) },
        )
    }


    // TODO: 테스트 터짐. 이런 시나리오를 테스트 하려면?
    @Disabled
    @Test
    fun `장바구니에 아무것도 들어가 있지 않을 때 첫 페이지에서 다음 페이지 더보기를 누르면 상품 40개 로드`() {
        // given setup
        viewModel = DefaultProductListViewModelStudy(shoppingProductRepository, historyRepository)
        val latch = CountDownLatch(2)

        // when
        viewModel.loadAll().also {
            latch.countDown()
        }

        viewModel.loadNextPageProducts()

        latch.await()
        val loadedProducts = viewModel.uiState.loadedProducts.getOrAwaitValue()
        assertThat(loadedProducts).isEqualTo(
            productDomainsTestFixture(40)
        )
    }

    @Test
    fun `총 데이터가 15개일 때 현재 페이지는 1페이지`() {
        // given
        productSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(15).toMutableList(),
            )
        shoppingProductRepository = DefaultShoppingProductRepositoryStudy(productSource, cartSource)
        historyRepository = DefaultProductHistoryRepositoryStudy(historyDataSource, productSource)
        viewModel = DefaultProductListViewModelStudy(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()

        // then
        val currentPage = viewModel.uiState.page
        assertThat(currentPage.getOrAwaitValue()).isEqualTo(1)
    }

    @Test
    fun `총 데이터가 20개일 때 첫 페이지가 마지막 페이지이다`() {
        // given
        productSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(20).toMutableList(),
            )
        shoppingProductRepository = DefaultShoppingProductRepositoryStudy(productSource, cartSource)
        historyRepository = DefaultProductHistoryRepositoryStudy(historyDataSource, productSource)

        viewModel = DefaultProductListViewModelStudy(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()

        // then
        val isLastPage = viewModel.uiState.isLastPage.value
        assertThat(isLastPage).isTrue
    }

    @Test
    fun `총 데이터가 21개일 대 첫 페이지가 마지막 페이지가 아니다`() {
        // given
        productSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(21).toMutableList(),
            )
        shoppingProductRepository = DefaultShoppingProductRepositoryStudy(productSource, cartSource)
        historyRepository = DefaultProductHistoryRepositoryStudy(historyDataSource, productSource)

        viewModel = DefaultProductListViewModelStudy(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()

        // then
        val isLastPage = viewModel.uiState.isLastPage.getOrAwaitValue()
        assertThat(isLastPage).isFalse
    }

    @Test
    fun `총 데이터가 21개일 때 두번째 페이지가 마지막 페이지이다`() {
        // given
        productSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(21).toMutableList(),
            )
        shoppingProductRepository = DefaultShoppingProductRepositoryStudy(productSource, cartSource)
        historyRepository = DefaultProductHistoryRepositoryStudy(historyDataSource, productSource)
        viewModel = DefaultProductListViewModelStudy(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()
        viewModel.loadNextPageProducts()

        // then
        assertThat(viewModel.uiState.isLastPage.getOrAwaitValue()).isTrue
    }

    @Test
    fun `장바구니에 담긴 상품들의 개수를 로드`() {
        // given
        productSource = FakeProductDataSourceStudy(allProducts = productsTestFixture(21).toMutableList())
        cartSource = FakeShoppingCartProductIdDataSourceStudy(data = productsIdCountDataTestFixture(10).toMutableList())
        shoppingProductRepository = DefaultShoppingProductRepositoryStudy(productSource, cartSource)
        historyRepository = DefaultProductHistoryRepositoryStudy(historyDataSource, productSource)

        viewModel = DefaultProductListViewModelStudy(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()

        // then
        assertThat(viewModel.uiState.cartProductTotalCount.getOrAwaitValue()).isEqualTo(10)
    }

    @Test
    fun `상품 상세로 이동하기 위한 id 저장`() {
        // given
        productSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(21).toMutableList(),
            )
        cartSource =
            FakeShoppingCartProductIdDataSourceStudy(
                data = productsIdCountDataTestFixture(5).toMutableList(),
            )
        shoppingProductRepository =
            DefaultShoppingProductRepositoryStudy(
                productSource,
                cartSource,
            )
        historyRepository = DefaultProductHistoryRepositoryStudy(historyDataSource, productSource)
        viewModel = DefaultProductListViewModelStudy(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()
        viewModel.navigateToDetail(id = 3)

        // then
        val navigation = viewModel.navigationEvent.getValue()
        assertThat(navigation).isEqualTo(ProductListEvent.NavigateToProductDetail(3))
    }

    @Test
    fun `최근 본 상품 내역 로드`() {
        // given
        shoppingProductRepository = DefaultShoppingProductRepositoryStudy(productSource, cartSource)
        historyDataSource =
            FakeProductHistorySourceStudy(
                history = mutableListOf(0, 1, 2, 3, 4)
            )
        historyRepository = DefaultProductHistoryRepositoryStudy(historyDataSource, productSource)
        viewModel = DefaultProductListViewModelStudy(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()

        // then
        val actual = viewModel.uiState.productsHistory.getOrAwaitValue()
        assertThat(actual).isEqualTo(
            productDomainsTestFixture(5)
        )
    }
}


class DefaultProductListViewModelStudy(
    private val productsRepository: ShoppingProductsRepositoryStudy,
    private val productHistoryRepository: ProductHistoryRepositoryStudy,
) : ProductListViewModel() {
    override val uiState: ProductListUiState = DefaultProductListUiState()
    override val errorEvent: MutableSingleLiveData<ProductListError> = MutableSingleLiveData()
    override val navigationEvent: MutableSingleLiveData<ProductListEvent> = MutableSingleLiveData()

    override fun loadAll() {
        val page = uiState.currentPage()
        loadAllProducts(page)
        calculateFinalPage(page)
        calculateProductsQuantityInCart()
        loadProductsHistory()
    }

    private fun loadAllProducts(page: Int) {
        productsRepository.loadAllProductsAsyncResult(page) { result ->
            result.onSuccess { products ->
                val currentProducts = uiState.loadedProducts.value ?: emptyList()
                uiState.postLoadedProducts(currentProducts.union(products).toList())
            }.onFailure {
                errorEvent.postValue(ProductListError.LoadProducts)
            }
        }
    }

    private fun calculateFinalPage(page: Int) {
        productsRepository.isFinalPageAsyncResult(page) { result ->
            result.onSuccess { isLastPage ->
                uiState.postLastPage(isLastPage)
            }.onFailure {
                errorEvent.postValue(ProductListError.FinalPage)
            }
        }
    }

    private fun calculateProductsQuantityInCart() {
        productsRepository.shoppingCartProductQuantityAsyncResult { result ->
            result.onSuccess {
                uiState.postCartProductTotalCount(it)
            }.onFailure {
                errorEvent.postValue(ProductListError.CartProductQuantity)
            }
        }
    }

    private fun loadProductsHistory() {
        productHistoryRepository.loadAllProductHistoryAsyncResult { result ->
            result.onSuccess {
                uiState.postProductsHistory(it)
            }.onFailure {
                errorEvent.postValue(ProductListError.LoadProductHistory)
            }
        }
    }

    override fun loadNextPageProducts() {
        val nextPage = uiState.nextPage()

        calculateFinalPage(nextPage)
        addNextPageProducts(nextPage)
        calculateProductsQuantityInCart()
    }

    private fun addNextPageProducts(page: Int) {
        productsRepository.loadAllProductsAsyncResult(page) { result ->
            result.onSuccess { products ->
                uiState.addLoadedProducts(products)
            }.onFailure {
                errorEvent.postValue(ProductListError.LoadProducts)
            }
        }
    }

    override fun navigateToShoppingCart() {
        navigationEvent.setValue(ProductListEvent.NavigateToShoppingCart)
    }

    override fun navigateToDetail(id: Long) {
        navigationEvent.setValue(ProductListEvent.NavigateToProductDetail(id))
    }

    override fun onAdd(productId: Long) {
        productsRepository.addShoppingCartProductAsyncResult(productId) { result ->
            result.onSuccess {
                uiState.increaseProductQuantity(productId)
                calculateProductsQuantityInCart()
            }.onFailure {
                errorEvent.postValue(ProductListError.AddProductInCart)

            }
        }
    }

    override fun onIncrease(productId: Long) {
        productsRepository.increaseShoppingCartProductAsyncResult(productId) { result ->
            result.onSuccess {
                uiState.increaseProductQuantity(productId)
                calculateProductsQuantityInCart()
            }.onFailure {
                errorEvent.postValue(ProductListError.UpdateProductQuantity)

            }
        }
    }

    override fun onDecrease(productId: Long) {
        productsRepository.decreaseShoppingCartProductAsyncResult(productId) { result ->
            result.onSuccess {
                uiState.decreaseProductQuantity(productId)
                calculateProductsQuantityInCart()
            }.onFailure {
                errorEvent.postValue(ProductListError.UpdateProductQuantity)
            }
        }
    }
}
