package woowacourse.shopping.productdetail

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class ProductDetailPresenterTest {
    private lateinit var presenter: ProductDetailPresenter
    private lateinit var view: ProductDetailContract.View

    @Before
    fun setUP() {
        view = mockk(relaxed = true)
        presenter = ProductDetailPresenter(
            view,
            productModel = mockk(relaxed = true),
            recentProductModel = mockk(relaxed = true)
        )
    }

    @Test
    fun 프레젠터가_생성되면_뷰의_상품_상세정보를_갱신한다() {
        // given
        justRun { view.setupProductDetail(any()) }

        // when

        // then
        verify {
            view.setupProductDetail(any())
        }
    }

    @Test
    fun 프레젠터가_생성되면_뷰의_최근_상품_상세정보를_갱신한다() {
        // given
        justRun { view.setupRecentProductDetail(any()) }

        // when

        // then
        verify {
            view.setupRecentProductDetail(any())
        }
    }

    @Test
    fun 카트_상품_다이얼로그를_셋업하면_카트_상품_다이얼로그를_보여준다() {
        // given

        // when
        presenter.setupCartProductDialog()

        // then
        verify { view.showCartProductDialog(any()) }
    }
}
