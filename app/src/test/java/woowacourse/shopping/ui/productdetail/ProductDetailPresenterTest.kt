package woowacourse.shopping.ui.productdetail

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
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
        presenter = ProductDetailPresenter(view, basketRepository, product, null)
    }

    @Test
    fun `현재 상품 데이터를 화면에 초기화 할수있다`() {
        // given
        every { view.updateBindingData(any(), any()) } just runs

        // when
        presenter.initProductData()

        // then
        verify(exactly = 1) { view.updateBindingData(any(), any()) }
    }

    @Test
    fun `장바구니 항목 선택 다이얼로그의 데이터 및 관련 데이터 수정 리스너들을 전달한다`() {
        // given
        every { view.showBasketDialog(any(), any(), any(), any()) } just runs
        every { view.updateProductCount(any()) } just runs
        // when
        presenter.setBasketDialog()

        // then
        verify(exactly = 1) { view.showBasketDialog(any(), any(), any(), any()) }
        verify(exactly = 1) { view.updateProductCount(any()) }
    }

    @Test
    fun `최근 본 상품을 클릭한다면 현재 상품이 최근본 상품으로 변경되고 현재 화면의 데이터가 변경된 현재상품(최근본상품)으로 업데이트된다`() {
        // given
        val currentProductSlot = slot<UiProduct>()
        every {
            view.updateBindingData(
                capture(currentProductSlot),
                any()
            )
        } just runs
        val previousProduct = UiProduct(
            2,
            "돼지2",
            UiPrice(1000),
            "https://pbs.twimg.com/media/FpFzjV-aAAAIE-v?format=jpg&name=large"
        )
        presenter = ProductDetailPresenter(view, basketRepository, product, previousProduct)
        // when
        presenter.selectPreviousProduct()

        // then
        assertEquals(currentProductSlot.captured, previousProduct)
    }
}
