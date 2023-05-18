package woowacourse.shopping.ui.productdetail

import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.repository.BasketRepository

internal class ProductDetailPresenterTest {
    private lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var view: ProductDetailContract.View
    private lateinit var basketRepository: BasketRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        basketRepository = mockk(relaxed = true)
        presenter = ProductDetailPresenter(view, basketRepository, mockk(relaxed = true))
    }

    @Test
    internal fun 프레젠터가_초기화될_때_상품_정보를_보여준다() {
        // given
        /* ... */

        // when
        /* init */

        // then
        verify(exactly = 1) { view.showProductImage(any()) }
        verify(exactly = 1) { view.showProductName(any()) }
        verify(exactly = 1) { view.showProductPrice(any()) }
    }

    @Test
    internal fun 장바구니에_상품을_추가한다() {
        // given
        /* ... */

        // when
        presenter.addBasketProduct()

        // then
        verify(exactly = 1) { basketRepository.plusProductCount(any()) }
        verify(exactly = 1) { view.navigateToBasketScreen() }
    }
}
