package woowacourse.shopping.productdetail

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.ui.productdetail.ProductDetailContract
import woowacourse.shopping.ui.productdetail.ProductDetailPresenter

class ProductDetailPresenterTest {
    private lateinit var presenter: ProductDetailPresenter
    private lateinit var view: ProductDetailContract.View
    private lateinit var recentProductRepository: RecentProductRepository

    @Before
    fun setUP() {
        view = mockk(relaxed = true)
        recentProductRepository = mockk(relaxed = true)
        presenter = ProductDetailPresenter(
            view,
            productModel = mockk(relaxed = true),
            recentProductModel = mockk(relaxed = true),
            recentProductRepository = recentProductRepository
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

    @Test
    fun 마지막으로_본_상품을_열면_최근_본_상품이_없는_상품_상세정보를_보여준다() {
        // given

        // when
        presenter.openProduct(mockk(relaxed = true))

        // then
        verify { view.showProductDetail(any(), isNull()) }
    }
}
