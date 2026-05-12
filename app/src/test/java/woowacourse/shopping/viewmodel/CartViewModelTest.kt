package woowacourse.shopping.viewmodel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.PurchaseProduct
import woowacourse.shopping.ui.viewmodel.CartViewModel
import woowacourse.shopping.viewmodel.fakes.FakeProductRepository
import woowacourse.shopping.viewmodel.fakes.FakePurchaseProductsRepository

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {
    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension()
    private lateinit var viewModel: CartViewModel
    private lateinit var fakeProductRepository: FakeProductRepository
    private lateinit var fakePurchaseProductsRepository: FakePurchaseProductsRepository

    @BeforeEach
    fun initViewModel() {
        fakePurchaseProductsRepository = FakePurchaseProductsRepository()
        fakeProductRepository = FakeProductRepository()

        viewModel = CartViewModel(
            purchaseProductsRepository = fakePurchaseProductsRepository,
            productRepository = fakeProductRepository
        )
    }

    @Test
    fun `현제 페이지 위치에 따라 페이지 이동 가능 여부가 결정되고 페이지를 이동할 수 있다`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.currentPage.collect {  }
        }

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.nextEnable.collect {  }

        }

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.prevEnable.collect {  }

        }

        val products = (1..6).map {
            Product(id = "$it", name = "상품$it", price = 1000, imageUri = "uri")
        }
        products.forEach { fakePurchaseProductsRepository.insert(PurchaseProduct(it, 1)) }

        assertEquals(0, viewModel.currentPage.value)
        assertFalse(viewModel.prevEnable.value)
        assertTrue(viewModel.nextEnable.value)

        viewModel.next()
        assertEquals(1, viewModel.currentPage.value)
        assertTrue(viewModel.prevEnable.value)
        assertFalse(viewModel.nextEnable.value)

        viewModel.prev()
        assertEquals(0, viewModel.currentPage.value)
    }

    @Test
    fun `현제 페이지에 해당하는 상품만큼 서버에서 불러와 _pagedCart를 구성한다`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.productCount.collect {  }

        }

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.pagedCart.collect {  }
        }

        val products = (1..7).map {
            Product(id = "$it", name = "상품$it", price = 1000, imageUri = "uri")
        }
        fakeProductRepository.setProducts(products)
        products.forEach { fakePurchaseProductsRepository.insert(PurchaseProduct(it, 1)) }

        assertEquals(5, viewModel.pagedCart.value.purchaseProducts.purchaseProducts.size)

        viewModel.next()
        assertEquals(2, viewModel.pagedCart.value.purchaseProducts.purchaseProducts.size)
    }

    @Test
    fun `마지막 페이지의 아이템을 모두 삭제하면 자동으로 이전 페이지로 이동한다`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.productCount.collect {  }
        }

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.currentPage.collect {  }
        }

        val products = (1..6).map {
            Product(id = "$it", name = "상품$it", price = 1000, imageUri = "uri")
        }
        fakeProductRepository.setProducts(products)
        products.forEach { fakePurchaseProductsRepository.insert(PurchaseProduct(it, 1)) }

        viewModel.next()
        assertEquals(1, viewModel.currentPage.value)

        viewModel.removeWithID("6")

        assertEquals(0, viewModel.currentPage.value)
    }
}