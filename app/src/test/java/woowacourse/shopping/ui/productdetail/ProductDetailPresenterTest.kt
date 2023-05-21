package woowacourse.shopping.ui.productdetail

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.repository.BasketRepository
import woowacourse.shopping.ui.model.UiPrice
import woowacourse.shopping.ui.model.UiProduct

class ProductDetailPresenterTest() {
    private lateinit var view: ProductDetailContract.View
    private lateinit var basketRepository: BasketRepository
    private lateinit var product: UiProduct
    private lateinit var presenter: ProductDetailPresenter

    @Before
    fun initProductDetailPresenter() {
        view = mockk(relaxed = true)
        basketRepository = mockk(relaxed = true)
        product = UiProduct(
            1,
            "돼지1",
            UiPrice(1000),
            "https://pbs.twimg.com/media/FpFzjV-aAAAIE-v?format=jpg&name=large"
        )
        presenter = ProductDetailPresenter(view, basketRepository, product, product)
    }

    @Test
    fun `장바구니에 담으면 장바구니 화면으로 전환하는 함수가 호출된다`() {
        // given
        every { view.showBasket() } just runs
        every { basketRepository.add(any()) } just runs

        // when
        val thread = presenter.addBasketProduct()
        thread.join()

        // then
        verify(exactly = 1) { view.showBasket() }
    }
}
