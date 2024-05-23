package woowacourse.shopping.presentation.cart

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.cartProduct
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.presentation.util.InstantTaskExecutorExtension
import woowacourse.shopping.presentation.util.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class, MockKExtension::class)
class ShoppingCartViewModelTest {
    @MockK
    lateinit var cartRepository: CartRepository

    private lateinit var cartViewModel: CartViewModel

    @BeforeEach
    fun setUp() {
        every { cartRepository.cartProducts(1, PAGE_SIZE) } returns
            Result.success(
                listOf(
                    cartProduct(),
                ),
            )
        every { cartRepository.canLoadMoreCartProducts(2, PAGE_SIZE) } returns Result.success(true)
        every { cartRepository.canLoadMoreCartProducts(0, PAGE_SIZE) } returns Result.success(false)
        cartViewModel = CartViewModel(cartRepository)
    }

    @Test
    @DisplayName("ViewModel 이 초기화될 때, 첫 번째 페이지에 해당하는 상품들이 로드된다")
    fun test0() {
        verify(exactly = 1) { cartRepository.cartProducts(1, PAGE_SIZE) }
        cartViewModel.currentPage.getOrAwaitValue() shouldBe 1
        cartViewModel.canLoadNextPage.getOrAwaitValue() shouldBe true
        cartViewModel.canLoadPrevPage.getOrAwaitValue() shouldBe false
    }

    @Test
    @DisplayName("현재 페이지가 1일 때, 다음 페이지로 이동하면, 페이지가 2가 된다")
    fun test1() {
        val nextPage = 2
        // given
        every { cartRepository.cartProducts(nextPage, PAGE_SIZE) } returns
            Result.success(
                listOf(
                    cartProduct(),
                ),
            )
        // when
        cartViewModel.plusPage()
        // then
        verify(exactly = 1) { cartRepository.cartProducts(nextPage, PAGE_SIZE) }
        cartViewModel.currentPage.getOrAwaitValue() shouldBe nextPage
    }

    companion object {
        const val PAGE_SIZE = 5
    }
}
