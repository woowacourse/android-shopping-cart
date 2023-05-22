package woowacourse.shopping

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.model.Operator
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.model.RecentlyViewedProduct
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentlyViewedRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.domain.util.WoowaResult
import woowacourse.shopping.presentation.ui.home.HomeContract
import woowacourse.shopping.presentation.ui.home.HomePresenter
import java.time.LocalDateTime

class HomePresenterTest {
    private lateinit var presenter: HomeContract.Presenter
    private lateinit var view: HomeContract.View
    private lateinit var productRepository: ProductRepository
    private lateinit var recentlyViewedRepository: RecentlyViewedRepository
    private lateinit var shoppingCartRepository: ShoppingCartRepository
    private lateinit var product: Product
    private lateinit var productInCart: ProductInCart
    private lateinit var products: List<ProductInCart>
    private lateinit var recentlyViewed: List<RecentlyViewedProduct>

    @Before
    fun setUp() {
        view = mockk<HomeContract.View>(relaxed = true)
        product = Product(
            id = 1,
            name = "BMW i8",
            price = 13000,
            itemImage = "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20190529_183%2Fauto_1559133035619Mrf6z_PNG%2F20190529212501_Y1nsyfUj.png",
        )
        productInCart = ProductInCart(
            product = product,
            quantity = 1,
        )
        recentlyViewed = listOf(
            RecentlyViewedProduct(
                product = product,
                viewedDateTime = LocalDateTime.now(),
            ),
        )
        products = listOf(productInCart)
        productRepository = mockk()
        recentlyViewedRepository = mockk()
        shoppingCartRepository = mockk()
        every { productRepository.isLastProduct(1) } returns true
        presenter =
            HomePresenter(view, productRepository, recentlyViewedRepository, shoppingCartRepository)
    }

    @Test
    fun `상품들을 가져와서 뷰에 세팅해준다`() {
        // given
        val slot = slot<Long>()
        every { productRepository.getProducts(10, capture(slot)) } returns products

        // when
        presenter.fetchProducts()
        val lastProductId = slot.captured

        // then
        assertEquals(0, lastProductId)
        verify(exactly = 1) { productRepository.getProducts(10, lastProductId) }
        verify(exactly = 1) { view.appendProductItems(0, products.size) }
    }

    @Test
    fun `최근 조회한 상품 목록을 가져와서 뷰에 세팅해준다`() {
        // given
        val slot = slot<List<RecentlyViewedProduct>>()
        every { recentlyViewedRepository.getRecentlyViewedProducts(10) } returns recentlyViewed
        every { view.updateRecentlyViewedProducts(capture(slot)) } returns Unit

        // when
        presenter.fetchRecentlyViewed()
        val actual = slot.captured

        // then
        verify(exactly = 1) { recentlyViewedRepository.getRecentlyViewedProducts(10) }
        verify(exactly = 1) { view.updateRecentlyViewedProducts(recentlyViewed) }
        assertEquals(recentlyViewed, actual)
    }

    @Test
    fun `수량이 1개인 0번째 상품의 수량을 감소시키면 장바구니에서 상품을 삭제하고 뷰를 업데이트한다`() {
        // given
        every { productRepository.getProducts(10, 0) } returns products
        presenter.fetchProducts()
        every { shoppingCartRepository.deleteProductInCart(1) } returns true

        // when
        presenter.updateProductQuantity(0, Operator.DECREASE)

        // then
        verify(exactly = 1) { shoppingCartRepository.deleteProductInCart(1) }
        verify(exactly = 1) { view.updateProductQuantity(0) }
    }

    @Test
    fun `수량이 1개인 0번째 상품의 수량을 증가시키면 장바구니 디비와 뷰를 업데이트한다`() {
        // given
        every { productRepository.getProducts(10, 0) } returns products
        presenter.fetchProducts()
        val slot = slot<Int>()
        every {
            shoppingCartRepository.updateProductQuantity(
                1,
                capture(slot),
            )
        } returns WoowaResult.SUCCESS(1)
        val expected = 2

        // when
        presenter.updateProductQuantity(0, Operator.INCREASE)
        val actual = slot.captured

        // then
        assertEquals(2, actual)
        verify(exactly = 1) { shoppingCartRepository.updateProductQuantity(1, expected) }
        verify(exactly = 1) { view.updateProductQuantity(0) }
    }

    @Test
    fun `장바구니의 전체 수량을 받아와서 뷰를 업데이트 해준다`() {
        // given
        every { shoppingCartRepository.getTotalQuantity() } returns 1
        // when
        presenter.fetchTotalQuantity()

        // then
        verify(exactly = 1) { shoppingCartRepository.getTotalQuantity() }
        verify(exactly = 1) { view.updateTotalQuantity(1) }
    }
}
