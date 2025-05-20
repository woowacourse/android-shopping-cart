package woowacourse.shopping.ui.viewmodel

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.dummyProducts
import woowacourse.shopping.presentation.viewmodel.cart.CartViewModel
import woowacourse.shopping.util.InstantTaskExecutorExtension
import woowacourse.shopping.util.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var viewModel: CartViewModel

    @BeforeEach
    fun setup() {
        viewModel = CartViewModel(FakeCartRepository().apply { this.storage = dummyProducts.take(9).toMutableList() })
    }

    @Test
    fun `초기화 시 products 값이 장바구니 목록으로 설정된다`() {
        val products = viewModel.products.getOrAwaitValue()
        assertTrue(products.isNotEmpty())
    }

    @Test
    fun `removeCartProduct 호출 시 products 값에서 해당 상품이 제거된다`() {
        val before = viewModel.products.getOrAwaitValue()
        val targetId = before.first().id

        viewModel.removeCartProduct(targetId)
        val after = viewModel.products.getOrAwaitValue()

        assertFalse(after.any { it.id == targetId })
    }

    @Test
    fun `increasePage 호출 시 currentPage 값이 증가하고 products가 갱신된다`() {
        val beforePage = viewModel.currentPage.getOrAwaitValue()

        viewModel.increasePage()
        val afterPage = viewModel.currentPage.getOrAwaitValue()

        assertEquals(beforePage + 1, afterPage)
        assertTrue(viewModel.products.getOrAwaitValue().isNotEmpty())
    }

    @Test
    fun `decreasePage 호출 시 currentPage 값이 감소하고 products가 갱신된다`() {
        viewModel.increasePage()
        val increased = viewModel.currentPage.getOrAwaitValue()

        viewModel.decreasePage()
        val decreased = viewModel.currentPage.getOrAwaitValue()

        assertEquals(increased - 1, decreased)
    }

    @Test
    fun `updateMaxPage 호출 시 maxPage 값이 업데이트된다`() {
        viewModel.updateMaxPage()
        val maxPage = viewModel.maxPage.getOrAwaitValue()

        assertTrue(maxPage >= 1)
    }
}
