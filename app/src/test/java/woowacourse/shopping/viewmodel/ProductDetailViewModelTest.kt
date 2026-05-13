package woowacourse.shopping.viewmodel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.extension.RegisterExtension
import woowacourse.shopping.domain.Product
import woowacourse.shopping.ui.viewmodel.ProductDetailViewModel
import woowacourse.shopping.viewmodel.fakes.FakeProductRepository
import woowacourse.shopping.viewmodel.fakes.FakePurchaseProductsRepository
import woowacourse.shopping.viewmodel.fakes.FakeRecentlyViewedProductRepository

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailViewModelTest {
    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension()

    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var fakeProductRepository: FakeProductRepository
    private lateinit var fakePurchaseProductsRepository: FakePurchaseProductsRepository
    private lateinit var fakeRecentlyViewedProductRepository: FakeRecentlyViewedProductRepository
    private val testProductId = "1"
    private val testProduct = Product(
        id = testProductId,
        imageUri = "테스트",
        name = "테스트",
        price = 1000
    )

    @BeforeEach
    fun setUp() {
        fakeProductRepository = FakeProductRepository()
        fakePurchaseProductsRepository = FakePurchaseProductsRepository()
        fakeRecentlyViewedProductRepository = FakeRecentlyViewedProductRepository()

        fakeProductRepository.setProducts(listOf(testProduct))

        viewModel = ProductDetailViewModel(
            purchaseProductsRepository = fakePurchaseProductsRepository,
            recentlyViewedProductRepository = fakeRecentlyViewedProductRepository,
            productRepository = fakeProductRepository,
            selectedProductId = testProductId,
            lastViewedProductId = null
        )
    }


    @Test
    fun `선택된 상품 ID로 서버에서 상품을 조회할 수 있다`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.selectedProduct.collect {  }
        }

        val product = viewModel.selectedProduct.value

        assertNotNull(product)
        assertEquals(testProductId, product.id)
        assertEquals(testProduct, product)
    }

    @Test
    fun `장바구니에 담을 상품의 수량을 조절할 수 있다`() = runTest {
        assertEquals(1, viewModel.countState.value)

        viewModel.addCount()
        assertEquals(2, viewModel.countState.value)

        viewModel.minusCount()
        assertEquals(1, viewModel.countState.value)
    }

    @Test
    fun `수량은 1에서 더이상 감소하지 않는다`() = runTest {
        assertEquals(1, viewModel.countState.value)
        viewModel.minusCount()

        assertEquals(1, viewModel.countState.value)
    }

    @Test
    fun `마지막으로 본 상품을 조회하면 viewHistory가 업데이트 된다`() = runTest {
        viewModel.updateHistory(testProduct)

        val history = fakeRecentlyViewedProductRepository.getLatestItem()
        assertEquals(testProductId, history.first())
    }
}