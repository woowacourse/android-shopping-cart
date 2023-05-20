package woowacourse.shopping.productdetail

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.repository.CartRepository

class ProductDetailPresenterTest {
    private lateinit var presenter: ProductDetailPresenter
    private lateinit var view: ProductDetailContract.View
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUP() {
        view = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        presenter = ProductDetailPresenter(
            view,
            productModel = mockk(relaxed = true),
            recentProductModel = mockk(relaxed = true),
            cartRepository
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
    fun 카트에_상품을_담으면_카트에_상품을_추가하고_카트를_보여준다() {
        // given
        justRun {
            cartRepository.addCartProduct(any())
            view.showCart()
        }

        // when
        presenter.addToCart()

        // then
        verify {
            cartRepository.addCartProduct(any())
            view.showCart()
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
