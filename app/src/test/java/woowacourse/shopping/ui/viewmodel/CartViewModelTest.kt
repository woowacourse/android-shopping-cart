package woowacourse.shopping.ui.viewmodel

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.dummyProducts
import woowacourse.shopping.ui.cart.CartViewModel
import woowacourse.shopping.util.InstantTaskExecutorExtension
import woowacourse.shopping.util.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var viewModel: CartViewModel

    @BeforeEach
    fun setup() {
        CartDummyRepositoryImpl.clearCart()
        dummyProducts.take(10).forEach { CartDummyRepositoryImpl.addCartProduct(it) }
        viewModel = CartViewModel()
    }

    @Test
    fun `초기화 시 products 값이 장바구니 목록으로 설정된다`() {
        val products = viewModel.cartProducts.getOrAwaitValue()
        Assertions.assertTrue(products.isNotEmpty())
    }

    @Test
    fun `removeCartProduct 호출 시 products 값에서 해당 상품이 제거된다`() {
        val before = viewModel.cartProducts.getOrAwaitValue()
        val targetId = before.first().id

        viewModel.removeCartProduct(targetId)

        val after = viewModel.cartProducts.getOrAwaitValue()
        Assertions.assertFalse(after.any { it.id == targetId })
    }

    @Test
    fun `increasePage 호출 시 currentPage 값이 증가하고 products가 갱신된다`() {
        val beforePage = viewModel.pageState.getOrAwaitValue()
        viewModel.increasePage()

        val afterPage = viewModel.pageState.getOrAwaitValue()
        Assertions.assertEquals(beforePage + 1, afterPage)

        val products = viewModel.cartProducts.getOrAwaitValue()
        Assertions.assertTrue(products.isNotEmpty())
    }

    @Test
    fun `decreasePage 호출 시 currentPage 값이 감소하고 products가 갱신된다`() {
        viewModel.increasePage()
        val increasedPage = viewModel.pageState.getOrAwaitValue()

        viewModel.decreasePage()
        val decreasedPage = viewModel.pageState.getOrAwaitValue()

        Assertions.assertEquals(increasedPage - 1, decreasedPage)
    }

    @Test
    fun `loadMaxPage 호출 시 maxPage 값이 업데이트된다`() {
        viewModel.loadMaxPage()
        val maxPage = viewModel.maxPage.getOrAwaitValue()

        Assertions.assertTrue(maxPage >= 1)
    }
}
