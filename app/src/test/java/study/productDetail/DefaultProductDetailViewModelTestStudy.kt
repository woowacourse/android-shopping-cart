package study.productDetail

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
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
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.productsTestFixture
import woowacourse.shopping.source.FakeProductHistorySourceStudy
import woowacourse.shopping.testfixture.productDomainTestFixture
import woowacourse.shopping.testfixture.productsIdCountDataTestFixture
import woowacourse.shopping.ui.productDetail.DefaultProductDetailUiState
import woowacourse.shopping.ui.productDetail.ProductDetailUiState
import woowacourse.shopping.ui.productDetail.ProductDetailViewModel
import woowacourse.shopping.ui.productDetail.event.ProductDetailError
import woowacourse.shopping.ui.productDetail.event.ProductDetailEvent
import woowacourse.shopping.ui.util.MutableSingleLiveData

@ExtendWith(InstantTaskExecutorExtension::class)
class DefaultProductDetailViewModelTestStudy {
    private var productId: Long = -1
    private lateinit var productsSource: ProductDataSourceStudy
    private lateinit var cartSource: ShoppingCartProductIdDataSourceStudy
    private lateinit var shoppingProductRepository: ShoppingProductsRepositoryStudy

    private lateinit var historyDataSource: ProductHistoryDataSourceStudy
    private lateinit var historyRepository: ProductHistoryRepositoryStudy
    private lateinit var viewModel: DefaultProductDetailViewModelStudy

    /**
     * setup 에서 장바구니에는 아무런 데이터도 없도록 만든다
     */
    @BeforeEach
    fun setUp() {
        productId = 1
        productsSource =
            FakeProductDataSourceStudy(
                allProducts = productsTestFixture(40).toMutableList(),
            )
        cartSource = FakeShoppingCartProductIdDataSourceStudy(data = mutableListOf())
        shoppingProductRepository = DefaultShoppingProductRepositoryStudy(productsSource, cartSource)

        historyDataSource = FakeProductHistorySourceStudy()
        historyRepository = DefaultProductHistoryRepositoryStudy(historyDataSource, productsSource)
    }

    @Test
    fun `현재 상품을 표시한다`() {
        // given
        cartSource =
            FakeShoppingCartProductIdDataSourceStudy(
                data = productsIdCountDataTestFixture(3, 2).toMutableList(),
            )
        shoppingProductRepository = DefaultShoppingProductRepositoryStudy(productsSource, cartSource)
        viewModel = DefaultProductDetailViewModelStudy(productId, shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()

        // then
        val actualProduct = viewModel.uiState.product.getOrAwaitValue()
        val expectedProduct = productDomainTestFixture(1, quantity = 2)
        assertThat(actualProduct).isEqualTo(expectedProduct)
    }

    @Test
    fun `현재 상품의 개수를 ui 에서만 더한다`() {
        // given
        cartSource = FakeShoppingCartProductIdDataSourceStudy(data = mutableListOf())
        shoppingProductRepository = DefaultShoppingProductRepositoryStudy(productsSource, cartSource)
        viewModel = DefaultProductDetailViewModelStudy(productId, shoppingProductRepository, historyRepository)
        viewModel.loadAll()

        // when
        viewModel.onIncrease(productId)

        // then
        val actualCount = viewModel.uiState.productCount.getOrAwaitValue()
        val expectedCount = 2
        assertThat(actualCount).isEqualTo(expectedCount)
    }

    @Test
    fun `현재 상품의 개수를 1 에서 더 줄여도 줄어들지 않는다 `() {
        // given
        viewModel = DefaultProductDetailViewModelStudy(productId, shoppingProductRepository, historyRepository)
        viewModel.loadAll()

        // when
        viewModel.onDecrease(productId)

        // then
        val actualCount = viewModel.uiState.productCount.getOrAwaitValue()
        val expectedCount = 1
        assertThat(actualCount).isEqualTo(expectedCount)
    }

    @Test
    fun `현재 상품의 개수를 2에서 1로 줄인다`() {
        // given
        cartSource =
            FakeShoppingCartProductIdDataSourceStudy(
                data = productsIdCountDataTestFixture(3, 2).toMutableList(),
            )
        shoppingProductRepository = DefaultShoppingProductRepositoryStudy(productsSource, cartSource)
        viewModel = DefaultProductDetailViewModelStudy(productId, shoppingProductRepository, historyRepository)
        viewModel.loadAll()

        // when
        viewModel.onDecrease(productId)

        // then
        val actualCount = viewModel.uiState.productCount.getOrAwaitValue()
        val expectedCount = 1

        assertThat(actualCount).isEqualTo(expectedCount)
    }

    @Test
    fun `현재 상품을 장바구니에 새로 담는다 aysnc result`() {
        // given
        productId = 1
        viewModel = DefaultProductDetailViewModelStudy(productId, shoppingProductRepository, historyRepository)
        viewModel.loadAll()

        // when
        viewModel.addProductToCart()

        // then
        val actual = viewModel.event.getOrAwaitValue()
        assertThat(actual).isEqualTo(ProductDetailEvent.AddProductToCart)
    }

    @Test
    fun `현재 상품이 이미 장바구니에 있을 때 장바구니에 담으면 장바구니에 수 만큼 더 담긴다`() {
        // given
        productsSource = FakeProductDataSourceStudy(
            allProducts = productsTestFixture(40).toMutableList(),
        )

        cartSource = FakeShoppingCartProductIdDataSourceStudy(
            data = productsIdCountDataTestFixture(3, 2).toMutableList(),
        )
        shoppingProductRepository = DefaultShoppingProductRepositoryStudy(productsSource, cartSource)
        viewModel = DefaultProductDetailViewModelStudy(productId, shoppingProductRepository, historyRepository)

        viewModel.loadAll()

        // when
        viewModel.onIncrease(productId)
        viewModel.addProductToCart()

        val actualEvent = viewModel.event.getOrAwaitValue()
        assertThat(actualEvent).isEqualTo(ProductDetailEvent.AddProductToCart)
    }

    @Test
    fun `최근 상품이 없으면 LoadedLatestProduct 에러가 된다`() {
        // given
        viewModel = DefaultProductDetailViewModelStudy(productId, shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()

        // then
        assertThat(viewModel.error.getOrAwaitValue()).isEqualTo(ProductDetailError.LoadLatestProduct)
    }

    @Test
    fun `최근 상품이 있으면 해당 객체`() {
        // given
        historyDataSource =
            FakeProductHistorySourceStudy(
                history = ArrayDeque<Long>(listOf(1, 2, 3)),
            )
        historyRepository = DefaultProductHistoryRepositoryStudy(historyDataSource, productsSource)
        viewModel = DefaultProductDetailViewModelStudy(productId, shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()

        // then
        val actualLatestProduct = viewModel.uiState.latestProduct.getOrAwaitValue()
        assertThat(actualLatestProduct).isEqualTo(
            productDomainTestFixture(3)
        )
    }
}


class DefaultProductDetailViewModelStudy(
    private val productId: Long,
    private val shoppingProductsRepository: ShoppingProductsRepositoryStudy,
    private val productHistoryRepository: ProductHistoryRepositoryStudy,
) : ProductDetailViewModel() {
    override val uiState: ProductDetailUiState = DefaultProductDetailUiState()

    override val event: MutableSingleLiveData<ProductDetailEvent> = MutableSingleLiveData()

    override val error: MutableSingleLiveData<ProductDetailError> = MutableSingleLiveData()

    override fun loadAll() {
        loadCurrentProduct()
        loadLatestProduct()
    }

    private fun loadCurrentProduct() {
        shoppingProductsRepository.loadProductAsyncResult(productId) { result ->
            result.onSuccess { product ->
                uiState.postCurrentProduct(product)
            }
            result.onFailure {
                error.postValue(ProductDetailError.LoadProduct)
            }
        }
    }

    private fun loadLatestProduct() {
        productHistoryRepository.loadLatestProductIdAsyncResult { result ->
            result.onSuccess { latestProductId ->
                loadLatestProductWithId(latestProductId)
            }
            result.onFailure {
                error.postValue(ProductDetailError.LoadLatestProduct)
            }
        }
    }

    private fun loadLatestProductWithId(latestProductId: Long) {
        shoppingProductsRepository.loadProductAsyncResult(latestProductId) { latestProduct ->
            latestProduct.onSuccess {
                uiState.postLatestProduct(it)
                saveCurrentProductInHistory()
            }
            latestProduct.onFailure {
                error.postValue(ProductDetailError.LoadProduct)
            }
        }
    }

    private fun saveCurrentProductInHistory() {
        productHistoryRepository.saveProductHistoryAsyncResult(productId) { result ->
            result.onFailure {
                error.postValue(ProductDetailError.SaveProductInHistory)
            }
        }
    }

    override fun addProductToCart() {
        shoppingProductsRepository.putItemInCartAsyncResult(productId, uiState.currentQuantity()) { result ->
            result.onSuccess {
                event.postValue(ProductDetailEvent.AddProductToCart)
            }
            result.onFailure {
                error.postValue(ProductDetailError.AddProductToCart)
            }
        }
    }

    override fun onFinishClick() {
        event.setValue(ProductDetailEvent.PopBackStack)
    }

    override fun onIncrease(productId: Long) {
        uiState.increaseProductCount()
    }

    override fun onDecrease(productId: Long) {
        uiState.decreaseProductCount()
    }

    override fun navigateToDetail(productId: Long) {
        event.setValue(ProductDetailEvent.NavigateToProductDetail(productId))
    }
}
