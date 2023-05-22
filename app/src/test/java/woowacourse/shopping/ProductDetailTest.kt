package woowacourse.shopping

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RecentlyViewedProduct
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentlyViewedRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.domain.util.WoowaResult
import woowacourse.shopping.presentation.ui.productDetail.ProductDetailContract
import woowacourse.shopping.presentation.ui.productDetail.ProductDetailPresenter
import java.time.LocalDateTime

class ProductDetailTest {
    private lateinit var view: ProductDetailContract.View
    private val productId: Long = 1
    private lateinit var productRepository: ProductRepository
    private lateinit var recentlyViewedRepository: RecentlyViewedRepository
    private lateinit var shoppingCartRepository: ShoppingCartRepository
    private lateinit var presenter: ProductDetailContract.Presenter

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        productRepository = mockk(relaxed = true)
        recentlyViewedRepository = mockk(relaxed = true)
        shoppingCartRepository = mockk(relaxed = true)
    }

    @Test
    fun `프레젠터를 생성하면 상품id에 해당하는 상품을 받아온다`() {
        // given
        every { productRepository.getProduct(productId) } returns WoowaResult.SUCCESS(
            Product(1, "", "", 0),
        )

        // when
        presenter = ProductDetailPresenter(
            view,
            productId,
            productRepository,
            recentlyViewedRepository,
            shoppingCartRepository,
        )

        // then
        verify(exactly = 1) { productRepository.getProduct(productId) }
    }

    @Test
    fun `프레젠터를 생성하면 상품이 최근 조회한 상품 목록에 추가된다`() {
        // given
        every { recentlyViewedRepository.addRecentlyViewedProduct(1) } returns 1

        // when
        presenter = ProductDetailPresenter(
            view,
            productId,
            productRepository,
            recentlyViewedRepository,
            shoppingCartRepository,
        )

        // then
        verify(exactly = 1) { recentlyViewedRepository.addRecentlyViewedProduct(1) }
    }

    @Test
    fun `프레젠터를 생성하면 뷰에 상품 데이터를 세팅해준다`() {
        // given
        val product = Product(1, "", "", 0)
        val recentlyViewedProduct =
            RecentlyViewedProduct(LocalDateTime.now(), Product(2, "", "", 0))
        every { productRepository.getProduct(1) } returns WoowaResult.SUCCESS(product)
        every { recentlyViewedRepository.getLastViewedProduct() } returns WoowaResult.SUCCESS(
            recentlyViewedProduct,
        )
        every { view.setBindingData(product, recentlyViewedProduct) } returns Unit
        val productSlot = slot<Product>()
        val recentlySlot = slot<RecentlyViewedProduct>()
        every { view.setBindingData(capture(productSlot), capture(recentlySlot)) } returns Unit

        // when
        presenter = ProductDetailPresenter(
            view,
            productId,
            productRepository,
            recentlyViewedRepository,
            shoppingCartRepository,
        )
        val productActual = productSlot.captured
        val recentlyActual = recentlySlot.captured

        // then
        assertEquals(product, productActual)
        assertEquals(recentlyViewedProduct, recentlyActual)
        verify(exactly = 1) { recentlyViewedRepository.getLastViewedProduct() }
        verify(exactly = 1) { view.setBindingData(product, recentlyViewedProduct) }
    }
}
