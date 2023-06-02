package woowacourse.shopping

import com.shopping.domain.CartProduct
import com.shopping.domain.CartRepository
import com.shopping.domain.Product
import com.shopping.domain.RecentProduct
import com.shopping.domain.RecentRepository
import io.mockk.every
import io.mockk.just
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.productdetail.ProductDetailContract
import woowacourse.shopping.productdetail.ProductDetailPresenter
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel

class ProductDetailPresenterTest {

    private lateinit var view: ProductDetailContract.View
    private lateinit var product: Product
    private lateinit var recentRepository: RecentRepository
    private lateinit var cartRepository: CartRepository
    private lateinit var presenter: ProductDetailContract.Presenter

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        product = ProductUIModel.dummy.toDomain()
        recentRepository = mockk()
        cartRepository = mockk()
        presenter =
            ProductDetailPresenter(view, product.toUIModel(), recentRepository, cartRepository)
    }

    @Test
    fun `최근 목록 저장소에 데이터를 추가한다`() {
        val currentTime = 1000L

        // given
        every { recentRepository.insert(RecentProduct(currentTime, product)) } just runs

        // when
        presenter.insertRecentRepository(currentTime)

        // then
        verify { recentRepository.insert(RecentProduct(currentTime, product)) }
    }

    @Test
    fun `가장 최근 본 상품을 가져온다`() {
        // given
        val dummyRecentProduct = RecentProduct(10000L, product)
        every { recentRepository.getLatestProduct() } returns dummyRecentProduct

        // when
        presenter.fetchMostRecentProduct()

        // then
        verify(exactly = 1) { view.showRecentProduct(any()) }
    }

    @Test
    fun `장바구니 상품을 만들어 뷰에게 전달한다`() {
        // given
        every { cartRepository.getProductCount(product.id) } returns 2
        justRun {
            view.setCartProductData(
                CartProductUIModel(true, 2, product.toUIModel())
            )
        }

        // when
        presenter.attachCartProductData()

        // then
        verify { cartRepository.getProductCount(product.id) }
        verify {
            view.setCartProductData(
                CartProductUIModel(true, 2, product.toUIModel())
            )
        }
    }

    @Test
    fun `장바구니에 상품을 추가하려 할 때 원래 장바구니에 없는 상품이라면 장바구니 레포지토리에 추가시키고 장바구니 화면을 표시한다`() {
        // given
        every { cartRepository.getProductCount(product.id) } returns 0
        justRun { cartRepository.insert(CartProduct(true, 5, product)) }
        justRun { view.showCartPage() }

        // when
        presenter.addToCart(5)

        // then
        verify { cartRepository.insert(CartProduct(true, 5, product)) }
        verify { view.showCartPage() }
        verify(inverse = true) { cartRepository.updateProductCount(any(), any()) }
    }

    @Test
    fun `장바구니에 상품을 추가하려 할 때 원래 장바구니에 있는 상품이라면 수량을 변경시키고 장바구니 화면을 표시한다`() {
        // given
        every { cartRepository.getProductCount(product.id) } returns 1
        justRun { cartRepository.updateProductCount(product.id, 5) }
        justRun { view.showCartPage() }

        // when
        presenter.addToCart(4)

        // then
        verify(inverse = true) { cartRepository.insert(any()) }
        verify { cartRepository.updateProductCount(product.id, 5) }
        verify { view.showCartPage() }
    }
}
