package woowacourse.shopping

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.presentation.mapper.toDomain
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

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        recentProductRepository = mockk(relaxed = true)
        every { cartRepository.getCartProductInfoById(1) } returns CartProductInfo(
            initProductModel.toDomain(),
            5,
        )
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
        // given
        every { recentProductRepository.getMostRecentProduct() } returns initProductModel.toDomain()
        // when
        presenter.checkCurrentProductIsMostRecent()
        // then
        verify { view.hideMostRecentProduct() }
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
    fun 상품의_수량을_갱신한다() {
        // when
        presenter.updateProductCount(3)
        // then
        assertEquals(presenter.productInfo.value.count, 3)
    }
}
