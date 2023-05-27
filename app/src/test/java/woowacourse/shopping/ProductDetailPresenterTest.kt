package woowacourse.shopping

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.presentation.mapper.toDomain
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.productdetail.ProductDetailContract
import woowacourse.shopping.presentation.productdetail.ProductDetailPresenter
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.RecentProductRepository

class ProductDetailPresenterTest {
    private lateinit var view: ProductDetailContract.View
    private lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var cartRepository: CartRepository
    private lateinit var recentProductRepository: RecentProductRepository
    private val initProductModel = ProductModel(1, "", "wooseok", 1000)

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        recentProductRepository = mockk(relaxed = true)
        every { cartRepository.getCartProductInfoById(1) } returns CartProductInfo(
            initProductModel.toDomain(),
            5,
        )
        every { recentProductRepository.getMostRecentProduct() } returns initProductModel.toDomain()
        presenter =
            ProductDetailPresenter(
                view = view,
                recentProductRepository = recentProductRepository,
                cartRepository = cartRepository,
                productModel = initProductModel,
            )
    }

    @Test
    fun 현재상품이_최근본_상품과_동일하다면_최근본_상품을_보여주지_않는다() {
        // when
        presenter.checkCurrentProductIsMostRecent()
        // then
        verify { view.setMostRecentProductVisible(false, initProductModel) }
    }

    @Test
    fun 현재상품이_최근본_상품과_동일하지_않다면_최근본_상품을_보여준다() {
        // given
        every { recentProductRepository.getMostRecentProduct() } returns Product.defaultProduct
        presenter =
            ProductDetailPresenter(
                view = view,
                recentProductRepository = recentProductRepository,
                cartRepository = cartRepository,
                productModel = initProductModel,
            )
        // when
        presenter.checkCurrentProductIsMostRecent()
        // then
        verify { view.setMostRecentProductVisible(true, Product.defaultProduct.toPresentation()) }
    }

    @Test
    fun 최근_본_상품을_누르면_최근_본_상세_화면으로_넘어간다() {
        // when
        presenter.showMostRecentProductDetail()
        // then
        verify { view.navigateToMostRecent(initProductModel) }
    }

    @Test
    fun 현재_상품을_최근본_상품목록에_저장한다() {
        // when
        presenter.saveRecentProduct()
        // then
        verify { recentProductRepository.deleteRecentProductId(1) }
        verify { recentProductRepository.addRecentProductId(1) }
    }

    @Test
    fun 담기를_누르면_카트_DB에_저장한다() {
        // when
        presenter.saveProductInRepository(3)
        // then
        verify { cartRepository.putProductInCart(1) }
        verify { cartRepository.updateCartProductCount(1, 3) }
        verify { view.showCompleteMessage("wooseok") }
    }

    @Test
    fun 상품_총액을_계산하여_보여준다() {
        // when
        presenter.updateTotalPrice(3)
        // then
        verify { view.setTotalPrice(3000) }
    }
}
