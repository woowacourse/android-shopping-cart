package woowacourse.shopping.home

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.domain.util.WoowaResult
import woowacourse.shopping.presentation.ui.common.uimodel.Operator
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView.Products
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView.RecentlyViewedProducts
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView.ShowMoreProducts
import woowacourse.shopping.presentation.ui.home.presenter.HomeContract
import woowacourse.shopping.presentation.ui.home.presenter.HomePresenter
import woowacourse.shopping.presentation.ui.shoppingCart.uiModel.ProductInCartUiState

class HomePresenterTest {
    private lateinit var presenter: HomeContract.Presenter
    private lateinit var view: HomeContract.View
    private lateinit var productRepository: ProductRepository
    private lateinit var shoppingCartRepository: ShoppingCartRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        productRepository = mockk(relaxed = true)
        shoppingCartRepository = mockk(relaxed = true)
        presenter = HomePresenter(view, productRepository, shoppingCartRepository)
    }

    @Test
    fun testFetchAllProductsOnHome() {
        // given :
        every { productRepository.getRecentlyViewedProducts(10) } returns recentlyViewProducts
        every { productRepository.getProductsFromRemote(any(), any()) } returns WoowaResult.SUCCESS(
            products,
        )
        every { shoppingCartRepository.getShoppingCart() } returns shoppingCart

        val slot = slot<List<ProductsByView>>()
        every { view.setUpProductsOnHome(capture(slot), any()) } just runs

        // when :
        presenter.fetchAllProductsOnHome()

        // then :
        val actual = slot.captured
        verify { productRepository.getRecentlyViewedProducts(any()) }
        verify { productRepository.getProductsFromRemote(any(), any()) }
        verify { view.setUpProductsOnHome(actual, any()) }

        Assert.assertEquals(
            listOf(wrappedRecentProducts) + wrappedProducts + showMoreButton,
            actual,
        )
    }

    @Test
    fun testFetchMoreProducts() {
        // given
        every { productRepository.getProductsFromRemote(any(), any()) } returns WoowaResult.SUCCESS(
            products,
        )

        val slot = slot<List<ProductsByView>>()
        every { view.setUpMoreProducts(capture(slot)) } just runs

        // when
        presenter.fetchMoreProducts()

        // then
        val actual = slot.captured
        verify { productRepository.getProductsFromRemote(any(), any()) }
        verify { view.setUpMoreProducts(wrappedProducts) }

        Assert.assertEquals(wrappedProducts, actual)
    }

    @Test
    fun testAddCountOfProductInCart() {
        // given :
        val request = Operator.PLUS
        every { shoppingCartRepository.getShoppingCart() } returns shoppingCart

        val slotForAdd = slot<ProductInCart>()
        every { shoppingCartRepository.addProductInCart(capture(slotForAdd)) } returns -1

        val slotForView = slot<List<ProductInCartUiState>>()
        every { view.setUpCountOfProductInCart(capture(slotForView)) } just runs

        // when :
        presenter.addCountOfProductInCart(request, product)

        // then :
        val actualForAdd = slotForAdd.captured
        val actualForView = slotForView.captured
        Assert.assertEquals(productInCart, actualForAdd)
        Assert.assertEquals(wrappedShoppingCart, actualForView)
    }

    companion object {
        private val product = Product(0, "", "test", 999)
        private val recentlyViewProducts = listOf<Product>(product)
        private val products = listOf(Product(0, "", "test", 999))
        private val wrappedRecentProducts = RecentlyViewedProducts(recentlyViewProducts)
        private val shoppingCart = listOf(ProductInCart(product, 3, false))
        private val wrappedShoppingCart = listOf(ProductInCartUiState(product, 3, false))
        private val productInCart = ProductInCart(product, 1, false)
        private val wrappedProducts = products.map { Products(it) }
        private val showMoreButton = ShowMoreProducts
    }
}
