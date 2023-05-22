package woowacourse.shopping

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.domain.RecentViewedRepository
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.view.productdetail.ProductDetailContract
import woowacourse.shopping.view.productdetail.ProductDetailPresenter

class ProductDetailPresenterTest {
    private lateinit var view: ProductDetailContract.View
    private lateinit var presenter: ProductDetailContract.Presenter

    private lateinit var productRepository: ProductRepository
    private lateinit var cartRepository: CartRepository
    private lateinit var recentViewedRepository: RecentViewedRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        productRepository = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        recentViewedRepository = mockk(relaxed = true)

        presenter = ProductDetailPresenter(
            view,
            cartRepository,
            recentViewedRepository,
            productRepository,
        )
    }

    @Test
    fun `장바구니 담기 버튼을 클릭하면 장바구니에 상품이 담긴다`() {
        every { view.startCartActivity() } answers { nothing }

        val product = ProductModel(
            10,
            "락토핏",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/6769030628798948-183ad194-f24c-44e6-b92f-1ed198b347cd.jpg",
            10000,
        )
        presenter.putInCart(product)
        val expectedSize = 4
        val actualSize = cartRepository.findAll().size

        assertEquals(expectedSize, actualSize)
        verify { view.startCartActivity() }
    }

    @Test
    fun `상품 상세 페이지로 들어가면 최근 본 상품에 해당 상품이 등록된다`() {
        every { recentViewedRepository.add(any()) } just runs

        val product = ProductModel(
            10,
            "락토핏",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/6769030628798948-183ad194-f24c-44e6-b92f-1ed198b347cd.jpg",
            10000,
        )

        presenter.setProductData(product)
        presenter.updateRecentViewedProducts()

        verify(exactly = 1) { presenter.updateRecentViewedProducts() }
    }

    @Test
    fun `최근 본 상품을 누르면 해당 상품 상세 페이지로 이동한다`() {
        every { view.startRecentViewedDetail(any()) } just runs
        every { recentViewedRepository.findMostRecent() } returns 1

        presenter.getRecentViewedProductData()
        presenter.navigateRecentViewedDetail()

        verify(exactly = 1) { presenter.navigateRecentViewedDetail() }
    }
}
