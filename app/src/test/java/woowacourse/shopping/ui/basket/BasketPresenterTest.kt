package woowacourse.shopping.ui.basket

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.PageNumber
import woowacourse.shopping.domain.repository.BasketRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.UiPrice

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
        verify(exactly = 1) { basketRepository.getProductInBasketByPage(any()) }
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
        every { basketRepository.getProductInBasketByPage(capture(currentPage)) } returns mockk(relaxed = true)

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
        every { basketRepository.getProductInBasketByPage(capture(currentPage)) } returns mockk(relaxed = true)

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
        val products = MutableList(8) { id ->
            Product(id, "상품 $id", UiPrice(1000), "")
        }
        val product = Product(0, "상품 0", UiPrice(1000), "")
        every { basketRepository.decreaseCartCount(product.toDomain()) } answers { products.remove(product) }


        // when
        presenter.removeFromCart(product)

        // then
        verify(exactly = 1) { basketRepository.decreaseCartCount(product.toDomain()) }
        verify(exactly = 1) { basketRepository.getProductInBasketByPage(PageNumber(1)) }
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
        verify(exactly = 1) { view.navigateToHome() }
    }
}
