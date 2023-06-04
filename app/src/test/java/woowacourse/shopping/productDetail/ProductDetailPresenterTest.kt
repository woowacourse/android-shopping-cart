package woowacourse.shopping.productDetail

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.util.WoowaResult
import woowacourse.shopping.domain.util.WoowaResult.SUCCESS
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter
import woowacourse.shopping.presentation.ui.productDetail.presenter.ProductDetailContract
import woowacourse.shopping.presentation.ui.productDetail.presenter.ProductDetailPresenter
import woowacourse.shopping.presentation.ui.shoppingCart.uiModel.ProductInCartUiState

class ProductDetailPresenterTest {
    private lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var view: ProductDetailContract.View
    private lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        productRepository = mockk(relaxed = true)
        presenter = ProductDetailPresenter(view, productRepository)
    }

    @Test
    fun testFetchProduct() {
        // given :
        val id = 1L
        every { productRepository.getProductFromRemote(id) } returns SUCCESS(product)

        val slot = slot<ProductInCartUiState>()
        every { view.setProduct(capture(slot)) } just runs

        // when :
        presenter.fetchProduct(id)

        // then :
        val actual = slot.captured
        assertEquals(wrappedShoppingCart, actual)
    }

    @Test
    fun testFetchLastViewedProduct() {
        // given :
        every { productRepository.getLastViewedProduct() } returns SUCCESS(product)

        val slot = slot<WoowaResult<Product>>()
        every { view.setLastViewedProduct(capture(slot)) } just runs

        // when :
        presenter.fetchLastViewedProduct()

        // then :
        val actual = slot.captured
        assertEquals(SUCCESS(product), actual)
    }

    @Test
    fun testAddRecentlyViewedProduct() {
        // given :
        val slot = slot<Long>()
        every { productRepository.addRecentlyViewedProduct(capture(slot), any()) } returns -1

        // when :
        presenter.addRecentlyViewedProduct(2, 10)

        // then :
        val actual = slot.captured
        assertEquals(2, actual)
    }

    companion object {
        private val product = Product(0, "", "test", 999)
        private val recentlyViewProducts = listOf<Product>(product)
        private val products = listOf(Product(0, "", "test", 999))
        private val wrappedRecentProducts =
            HomeAdapter.ProductsByView.RecentlyViewedProducts(recentlyViewProducts)
        private val shoppingCart = listOf(ProductInCart(product, 3, false))
        private val wrappedShoppingCart = ProductInCartUiState(product, 1, true)
        private val productInCart = ProductInCart(product, 1, false)
        private val wrappedProducts = products.map { HomeAdapter.ProductsByView.Products(it) }
        private val showMoreButton = HomeAdapter.ProductsByView.ShowMoreProducts
    }
}
