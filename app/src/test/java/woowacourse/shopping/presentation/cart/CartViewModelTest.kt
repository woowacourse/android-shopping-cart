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
class CartViewModelTest {
    @MockK
    lateinit var cartRepository: CartRepository

    private lateinit var cartViewModel: CartViewModel

    @BeforeEach
    fun setUp() {
        every { cartRepository.cartProducts(1) } returns listOf(cartProduct())
        every { cartRepository.canLoadMoreCartProducts(1) } returns true
        cartViewModel = CartViewModel(cartRepository)
    }

    @Test
    @DisplayName("ViewModel 이 초기화될 때, 첫 번째 페이지에 해당하는 상품들이 로드된다")
    fun test0() {
        every { cartRepository.cartProducts(1) } returns listOf(cartProduct())

        verify(exactly = 1) { cartRepository.cartProducts(1) }
        verify(exactly = 1) { cartRepository.canLoadMoreCartProducts(1) }
        cartViewModel.currentPage.getOrAwaitValue() shouldBe 1
        cartViewModel.canLoadNextPage.getOrAwaitValue() shouldBe true
        cartViewModel.canLoadPrevPage.getOrAwaitValue() shouldBe false
    }

    @Test
    @DisplayName("현재 페이지가 1일 때, 다음 페이지로 이동하면, 페이지가 2가 된다")
    fun test1() {
        // given & when
        val nextPage = 2
        cartViewModel.moveToNextPage()
        // then
        verify { cartRepository.canLoadMoreCartProducts(1) }
        cartViewModel.currentPage.getOrAwaitValue() shouldBe nextPage
    }

    @Test
    @DisplayName("현재 페이지가 1 페이지라면, 이전 페이지로 이동할 수 없다")
    fun test2() {
        // when
        cartViewModel.moveToPreviousPage()
        // then
        cartViewModel.currentPage.getOrAwaitValue() shouldBe 1
    }

    @Test
    @DisplayName("현재 페이지가 마지막 페이지라면, 다음 페이지로 이동할 수 없다")
    fun test3() {
        // given
        every { cartRepository.canLoadMoreCartProducts(2) } returns true
        every { cartRepository.canLoadMoreCartProducts(3) } returns true
        every { cartRepository.canLoadMoreCartProducts(4) } returns true
        every { cartRepository.canLoadMoreCartProducts(5) } returns false
        // when
        cartViewModel.moveToNextPage()
        cartViewModel.moveToNextPage()
        cartViewModel.moveToNextPage()
        cartViewModel.moveToNextPage()
        // then
        cartViewModel.canLoadNextPage.getOrAwaitValue() shouldBe false
    }
}
