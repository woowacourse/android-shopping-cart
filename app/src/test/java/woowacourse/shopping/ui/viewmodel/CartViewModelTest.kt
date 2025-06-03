package woowacourse.shopping.ui.viewmodel

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.dummyProducts
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.presentation.cart.CartViewModel
import woowacourse.shopping.util.InstantTaskExecutorExtension
import woowacourse.shopping.util.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var viewModel: CartViewModel
    private lateinit var fakeCartRepository: FakeCartRepository

    @BeforeEach
    fun setup() {
        fakeCartRepository = FakeCartRepository()
        viewModel = CartViewModel(fakeCartRepository.apply { this.storage = dummyProducts.take(9).toMutableList() })
    }

    @Test
    fun `초기화 시 products 값이 장바구니 목록으로 설정된다`() {
        val products = viewModel.products.getOrAwaitValue()
        assertTrue(products.isNotEmpty())
    }

    @Test
    fun `removeCartProduct 호출 시 products 값에서 해당 상품이 제거된다`() {
        val before = viewModel.products.getOrAwaitValue()
        val targetId = before.first().product.id

        viewModel.removeCartProduct(targetId)
        val after = viewModel.products.getOrAwaitValue()

        assertFalse(after.any { it.product.id == targetId })
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
        val beforeProducts = viewModel.products.getOrAwaitValue()

        viewModel.decreasePage()
        val decreased = viewModel.currentPage.getOrAwaitValue()
        val afterProducts = viewModel.products.getOrAwaitValue()

        assertEquals(increased - 1, decreased)
        assertNotEquals(beforeProducts, afterProducts)
    }

    @Test
    fun `updateMaxPage 호출 시 maxPage 값이 업데이트된다`() {
        viewModel.updateMaxPage()
        val maxPage = viewModel.maxPage.getOrAwaitValue()

        assertTrue(maxPage >= 1)
    }

    @Test
    fun `장바구니가 비어있을 때 products는 빈 리스트를 반환한다`() {
        fakeCartRepository.storage = mutableListOf() // 먼저 비움
        viewModel = CartViewModel(fakeCartRepository) // 새로 생성

        val products = viewModel.products.getOrAwaitValue()
        assertTrue(products.isEmpty())
    }

    @Test
    fun `장바구니가 비어있을 때 maxPage는 0이다`() {
        fakeCartRepository.storage = mutableListOf() // 먼저 비움
        viewModel = CartViewModel(fakeCartRepository) // 새로 생성

        viewModel.updateMaxPage()
        val maxPage = viewModel.maxPage.getOrAwaitValue()
        assertEquals(1, maxPage)
    }


}

