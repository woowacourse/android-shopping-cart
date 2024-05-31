package woowacourse.shopping.presentation.ui.cart

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import testCartItemResult0
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.presentation.state.UIState
import woowacourse.shopping.presentation.ui.InstantTaskExecutorExtension
import woowacourse.shopping.presentation.ui.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
@ExtendWith(MockKExtension::class)
class CartViewModelTest {
    @RelaxedMockK
    private lateinit var cartRepository: CartRepository

    @InjectMockKs
    private lateinit var viewModel: CartViewModel

    private lateinit var mockWebServer: MockWebServer

    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `장바구니에 담긴 상품이 비었을 때를 확인할 수 있다`() {
        // given
        every { cartRepository.size() } returns 0

        // when
        viewModel.loadPage(0)

        // then
        val state = viewModel.cartItemsState.getOrAwaitValue()
        assertThat(state).isEqualTo(UIState.Empty)
    }

    @Test
    fun `장바구니에 담긴 상품을 확인할 수 있다`() {
        // given
        every { cartRepository.findWithProductId(0L) } returns testCartItemResult0

        // when
        viewModel.loadPage(0)

        // then
        val state = viewModel.cartItemsState.getOrAwaitValue()
        assertThat(state).isEqualTo(UIState.Success(listOf(testCartItemResult0)))
    }

    @Test
    fun `장바구니에 담긴 상품이 5개 미만일 때 페이지 컨트롤이 보이지 않는다`() {
        // given
        every { cartRepository.size() } returns 4
        repeat(4) {
            every { cartRepository.findWithProductId(it.toLong()) } returns testCartItemResult0
        }

        // when
        viewModel.loadPage(0)

        // then
        val isVisible = viewModel.isPageControlVisible.getOrAwaitValue()
        assertThat(isVisible).isFalse()
    }

    @Test
    fun `장바구니에 담긴 상품이 5개 초과일 때 페이지 컨트롤이 보인다`() {
        // given
        every { cartRepository.size() } returns 6
        repeat(6) {
            every { cartRepository.findWithProductId(it.toLong()) } returns testCartItemResult0
        }

        // when
        viewModel.loadPage(0)

        // then
        val isVisible = viewModel.isPageControlVisible.getOrAwaitValue()
        assertThat(isVisible).isTrue()
    }

    @Test
    fun `아이템이 6개 이상이면 다음 페이지로 이동할 수 있다`() {
        // given
        every { cartRepository.size() } returns 6
        repeat(6) {
            every { cartRepository.findWithProductId(it.toLong()) } returns testCartItemResult0
        }
        viewModel.loadPage(0)

        // when
        viewModel.loadNextPage()

        // then
        val currentPage = viewModel.currentPage.getOrAwaitValue()
        assertThat(currentPage).isEqualTo(1)
    }

    @Test
    fun `첫 페이지에서 이전 페이지로 이동할 수 없다`() {
        // given
        every { cartRepository.size() } returns 6
        repeat(6) {
            every { cartRepository.findWithProductId(it.toLong()) } returns testCartItemResult0
        }
        viewModel.loadPage(0)

        // when
        viewModel.loadPreviousPage()

        // then
        val currentPage = viewModel.currentPage.getOrAwaitValue()
        assertThat(currentPage).isEqualTo(0)
    }

    @Test
    fun `아이템을 삭제할 수 있다`() {
        // given
        every { cartRepository.findWithProductId(0L) } returns testCartItemResult0
        every { cartRepository.size() } returns 1
        every { cartRepository.deleteByProductId(0L) } answers {
            every { cartRepository.size() } returns 0
        }

        // when
        viewModel.onDeleteItemClick(0)

        // then
        val state = viewModel.cartItemsState.getOrAwaitValue()
        assertThat(state).isEqualTo(UIState.Empty)
    }
}
