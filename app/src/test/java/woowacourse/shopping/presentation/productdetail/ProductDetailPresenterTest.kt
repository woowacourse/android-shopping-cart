package woowacourse.shopping.presentation.productdetail

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.data.respository.cart.CartRepository
import woowacourse.shopping.data.respository.product.ProductRepository
import woowacourse.shopping.presentation.ProductFixture
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.view.productdetail.ProductDetailContract
import woowacourse.shopping.presentation.view.productdetail.ProductDetailPresenter

class ProductDetailPresenterTest {
    private lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var view: ProductDetailContract.View
    private lateinit var productRepository: ProductRepository
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)

        cartRepository = mockk()
        productRepository = mockk()

        presenter = ProductDetailPresenter(view, productRepository, cartRepository)
    }

    @Test
    fun `id를 통해 데이터를 받아와 상품 정보를 보여준다`() {
        // given
        every { productRepository.getDataById(0L) } returns ProductFixture.getData()
        val slot = slot<ProductModel>()
        justRun { view.setProductInfoView(capture(slot)) }

        // when
        presenter.loadProductInfoById(0L)

        // then
        val actual = slot.captured
        val expected = ProductFixture.getData().toUIModel()

        assertEquals(expected, actual)
        verify { productRepository.getDataById(actual.id) }
        verify { view.setProductInfoView(actual) }
    }

    @Test
    fun `상품을 장바구니에 저장한다`() {
        // given
        val slot = slot<Long>()
        justRun { cartRepository.addCart(capture(slot)) }
        justRun { view.addCartSuccessView() }

        // when
        presenter.addCart(1)

        // then
        val actual = slot.captured
        val expected = 1L
        assertEquals(expected, actual)
        verify { cartRepository.addCart(actual) }
        verify { view.addCartSuccessView() }
    }
}
