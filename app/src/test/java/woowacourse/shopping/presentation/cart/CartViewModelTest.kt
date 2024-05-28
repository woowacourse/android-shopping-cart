package woowacourse.shopping.presentation.cart

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
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
class CartViewModelTest {
    @RelaxedMockK
    lateinit var cartRepository: CartRepository

    private lateinit var cartViewModel: CartViewModel
    private val uiState get() = cartViewModel.uiState.getOrAwaitValue()

    @BeforeEach
    fun setUp() {
        every { cartRepository.cartProducts(any(), any()) } returns
            Result.success(
                listOf(
                    cartProduct(),
                ),
            )
        every { cartRepository.canLoadMoreCartProducts(any(), PAGE_SIZE) } returns
            Result.success(
                true,
            )
        cartViewModel = CartViewModel(cartRepository)
    }

    @Test
    @DisplayName("ViewModel 이 초기화될 때, 1 페이지의 장바구니 상품을 가져온다")
    fun test0() {
        verify(exactly = 1) { cartRepository.cartProducts(1, PAGE_SIZE) }
        cartViewModel.uiState.getOrAwaitValue().currentPage shouldBe 1
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
        cartViewModel.moveToNextPage()
        // then
        verify(exactly = 1) { cartRepository.cartProducts(nextPage, PAGE_SIZE) }
        uiState.currentPage shouldBe nextPage
    }

    @Test
    @DisplayName("현재 페이지가 1일 때, 다음 페이지로 이동하면, 페이지가 2가 된다")
    fun test2() {
        val nextPage = 2
        // given
        every { cartRepository.canLoadMoreCartProducts(1, PAGE_SIZE) } returns Result.success(true)
        every { cartRepository.canLoadMoreCartProducts(3, PAGE_SIZE) } returns Result.success(true)
        // when
        cartViewModel.moveToNextPage()
        // then
        verify(exactly = 1) { cartRepository.canLoadMoreCartProducts(1, PAGE_SIZE) }
        verify(exactly = 1) { cartRepository.canLoadMoreCartProducts(3, PAGE_SIZE) }
        uiState.currentPage shouldBe nextPage
    }

    companion object {
        const val PAGE_SIZE = 5
    }
}
