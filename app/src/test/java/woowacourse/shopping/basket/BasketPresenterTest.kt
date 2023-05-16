package woowacourse.shopping.basket

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.PageNumber
import woowacourse.shopping.domain.repository.BasketRepository
import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.basket.BasketContract
import woowacourse.shopping.ui.basket.BasketPresenter

internal class BasketPresenterTest {

    private lateinit var presenter: BasketContract.Presenter
    private lateinit var view: BasketContract.View
    private lateinit var basketRepository: BasketRepository

    @Before
    fun setUp() {
        basketRepository = mockk(relaxed = true)
        view = mockk(relaxed = true)
        presenter = BasketPresenter(view, basketRepository)
    }

    @Test
    internal fun 장바구니를_목록을_갱신하면_현재_페이지에_해당하는_아이템을_보여주고_네비게이터를_갱신한다() {
        // given
        val page = 1

        // when
        presenter.fetchBasket(page)

        // then
        verify(exactly = 1) { basketRepository.getPartially(any()) }
        verify(exactly = 1) { view.updateBasket(any()) }
        verify(exactly = 1) { view.updateNavigatorEnabled(any(), any()) }
        verify(exactly = 1) { view.updatePageNumber(any()) }
    }

    @Test
    internal fun 이전_장바구니를_불러오면_페이지를_변경하고_장바구니를_갱신한다() {
        // given
        val page = 2
        presenter = BasketPresenter(view, basketRepository)

        val currentPage = slot<PageNumber>()
        every { basketRepository.getPartially(capture(currentPage)) } returns mockk(relaxed = true)

        // when
        presenter.fetchBasket(page - 1)

        // then
        assertEquals(currentPage.captured, PageNumber(page - 1))
        verify(exactly = 1) { view.updateBasket(any()) }
        verify(exactly = 1) { view.updateNavigatorEnabled(any(), any()) }
        verify(exactly = 1) { view.updatePageNumber(any()) }
    }

    @Test
    internal fun 다음_장바구니를_불러오면_페이지를_변경하고_장바구니를_갱신한다() {
        // given
        val page = 1
        presenter = BasketPresenter(view, basketRepository)

        val currentPage = slot<PageNumber>()
        every { basketRepository.getPartially(capture(currentPage)) } returns mockk(relaxed = true)

        // when
        presenter.fetchBasket(page + 1)

        // then
        assertEquals(currentPage.captured, PageNumber(page + 1))
        verify(exactly = 1) { view.updateBasket(any()) }
        verify(exactly = 1) { view.updateNavigatorEnabled(any(), any()) }
        verify(exactly = 1) { view.updatePageNumber(any()) }
    }

    @Test
    internal fun 장바구니_목록에_있는_제품을_제거하면_뷰를_갱신한다() {
        // given
        val product = mockk<Product>(relaxed = true)

        // when
        presenter.removeBasketProduct(product)

        // then
        verify(exactly = 1) { basketRepository.remove(any()) }
        verify(exactly = 1) { basketRepository.getPartially(any()) }
        verify(exactly = 1) { view.updateBasket(any()) }
        verify(exactly = 1) { view.updateNavigatorEnabled(any(), any()) }
        verify(exactly = 1) { view.updatePageNumber(any()) }
    }

    @Test
    internal fun 종료하면_화면을_닫는다() {
        // given
        /* ... */

        // when
        presenter.closeScreen()

        // then
        verify(exactly = 1) { view.closeScreen() }
    }
}
