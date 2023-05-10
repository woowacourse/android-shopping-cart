package woowacourse.shopping.presentation.productdetail

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.ProductRepository
import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.presentation.ProductFixture
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.view.productdetail.ProductDetailContract
import woowacourse.shopping.presentation.view.productdetail.ProductDetailPresenter

class ProductDetailPresenterTest {
    private lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var view: ProductDetailContract.View
    private lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        productRepository = mockk()

        presenter = ProductDetailPresenter(view, productRepository)
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

        TestCase.assertEquals(expected, actual)
        verify { productRepository.getDataById(actual.id) }
        verify { view.setProductInfoView(actual) }
    }
}
