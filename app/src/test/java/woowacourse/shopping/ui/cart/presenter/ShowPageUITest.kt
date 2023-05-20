package woowacourse.shopping.ui.cart.presenter

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.repository.CartItemRepository
import woowacourse.shopping.ui.cart.PageableView

internal class ShowPageUITest {

    private lateinit var view: FakePageableView
    private lateinit var cartItemRepository: CartItemRepository

    @BeforeEach
    fun setUp() {
        view = FakePageableView()
        cartItemRepository = mockk()
    }

    @Test
    fun `현재 페이지가 1이면 뷰는 이전 페이지를 요청할 수 없는 상태가 된다`() {
        val pageSize = 5
        every { cartItemRepository.countAll() } returns 10
        val sut = ShowPageUI(view, cartItemRepository, pageSize)
        val currentPage = 1

        sut.invoke(currentPage)

        assertThat(view.canRequestPreviousPage).isFalse
    }

    @Test
    fun `현재 페이지가 2 이상이면 뷰는 이전 페이지를 요청할 수 있는 상태가 된다`() {
        val pageSize = 5
        every { cartItemRepository.countAll() } returns 10
        val sut = ShowPageUI(view, cartItemRepository, pageSize)
        val currentPage = 2

        sut.invoke(currentPage)

        assertThat(view.canRequestPreviousPage).isTrue
    }

    @Test
    fun `현재 페이지가 최대 페이지보다 같다면 다음 페이지를 요청할 수 없는 상태가 된다`() {
        val pageSize = 5
        every { cartItemRepository.countAll() } returns 10
        val sut = ShowPageUI(view, cartItemRepository, pageSize)
        val currentPage = 2

        sut.invoke(currentPage)

        assertThat(view.canRequestNextPage).isFalse
    }

    @Test
    fun `현재 페이지가 최대 페이지보다 작다면 다음 페이지를 요청할 수 있는 상태가 된다`() {
        val pageSize = 5
        every { cartItemRepository.countAll() } returns 11
        val sut = ShowPageUI(view, cartItemRepository, pageSize)
        val currentPage = 2

        sut.invoke(currentPage)

        assertThat(view.canRequestNextPage).isTrue
    }

    @Test
    fun `페이지 UI 보여주기가 실행되면 입력된 페이지로 뷰의 페이지가 설정된다`() {
        val pageSize = 5
        every { cartItemRepository.countAll() } returns 11
        val sut = ShowPageUI(view, cartItemRepository, pageSize)
        val currentPage = 2

        sut.invoke(currentPage)

        assertThat(view.currentPage).isEqualTo(currentPage)
    }

    class FakePageableView : PageableView {
        var canRequestPreviousPage: Boolean? = null
        var canRequestNextPage: Boolean? = null
        var currentPage = -1

        override fun setStateThatCanRequestPreviousPage(canRequest: Boolean) {
            canRequestPreviousPage = canRequest
        }

        override fun setStateThatCanRequestNextPage(canRequest: Boolean) {
            canRequestNextPage = canRequest
        }

        override fun setPage(page: Int) {
            this.currentPage = page
        }

    }
}