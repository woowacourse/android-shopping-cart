package woowacourse.shopping

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import model.Product
import model.RecentViewedProduct
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.database.ShoppingCache
import woowacourse.shopping.shopping.ShoppingContract
import woowacourse.shopping.shopping.ShoppingPresenter
import woowacourse.shopping.util.toProductUiModel
import woowacourse.shopping.util.toRecentViewedProductUiModel

class ShoppingPresenterTest {

    private lateinit var products: List<Product>
    private lateinit var recentViewedProducts: List<RecentViewedProduct>
    private lateinit var recentViewedProduct: RecentViewedProduct
    private lateinit var view: ShoppingContract.View
    private lateinit var shoppingCache: ShoppingCache
    private lateinit var presenter: ShoppingPresenter

    @Before
    fun setUp() {
        shoppingCache = mockk(relaxed = true)
        view = mockk(relaxed = true)
        presenter = ShoppingPresenter(
            view = view,
            shoppingCache = shoppingCache
        )

        products = listOf(
            Product(name = "아메리카노"),
            Product(name = "카페라떼")
        )
        recentViewedProducts = listOf(
            RecentViewedProduct(name = "아메리카노"),
            RecentViewedProduct(name = "카페라떼")
        )
        recentViewedProduct = RecentViewedProduct(name = "밀크티")
    }

    @Test
    fun `저장소로부터 상품 목록을 받아와서 뷰를 초기화한다`() {
        // given
        every { shoppingCache.selectProducts(any(), any()) } returns products

        // when
        presenter.loadProducts()

        // then
        val expectedProducts = products.map { it.toProductUiModel() }
        verify {
            view.setUpShoppingView(
                products = expectedProducts,
                any()
            )
        }
    }

    @Test
    fun `저장소로부터 최근 본 상품 목록을 받아와서 뷰를 초기화한다`() {
        // given
        every { shoppingCache.selectRecentViewedProducts() } returns recentViewedProducts

        // when
        val presenter = ShoppingPresenter(
            view = view,
            shoppingCache = shoppingCache
        )

        presenter.loadProducts()

        // then
        val expected = recentViewedProducts.map { it.toRecentViewedProductUiModel() }

        verify {
            view.setUpShoppingView(
                any(),
                recentViewedProducts = expected,
            )
        }
    }
    @Test
    fun `장바구니에 있는 상품들에 대한 개수를 저장소로부터 받아서 뷰를 갱신한다`() {
        // given
        every { shoppingCache.getCountOfShoppingCartProducts() } returns 7

        // when
        presenter.loadCartProductsCount()

        // then
        val expected = 7
        verify { view.refreshProductCount(expected) }
    }

    @Test
    fun `최근 본 상품을 추가하면 저장소에 저장한 후 기존 최근 본 상품들과 합쳐서 뷰를 갱신한다`() {
        // given
        every { shoppingCache.selectRecentViewedProducts() } returns recentViewedProducts
        every { shoppingCache.selectRecentViewedProductById(id = recentViewedProduct.id) } returns recentViewedProduct

        // when
        val presenter = ShoppingPresenter(
            view = view,
            shoppingCache = shoppingCache
        )

        presenter.addToRecentViewedProduct(recentViewedProduct.id)

        // then
        val expected = listOf(recentViewedProduct.toRecentViewedProductUiModel()) +
            recentViewedProducts.map { it.toRecentViewedProductUiModel() }

        verify { view.refreshRecentViewedProductsView(products = expected) }
    }

    @Test
    fun `상품의 상세 정보를 로드할때 저장소로부터 마지막으로 본 상품을 받아와서 뷰에 넘겨준다`() {
        // given
        val product = Product(name = "밀크티").toProductUiModel()
        every { shoppingCache.selectLatestViewedProduct() } returns Product(name = "밀크티")

        // when
        presenter.loadProductDetail(Product(name = "아메리카노").toProductUiModel())

        // then
        verify {
            view.shoppingNavigator.navigateToProductDetailView(
                product = Product(name = "아메리카노").toProductUiModel(),
                latestViewedProduct = product
            )
        }
    }

    @Test
    fun `상품을 추가적으로 읽어오면 추가적으로 읽어온 상품으로 뷰를 갱신한다`() {
        // given
        every { shoppingCache.selectProducts(any(), any()) } returns products

        // when
        presenter.readMoreShoppingProducts()

        // then
        verify {
            view.refreshShoppingProductsView(products.map { it.toProductUiModel() })
        }
    }

    @Test
    fun `상품을 장바구니에 추가하면 저장소에 저장하고 장바구니 상품의 개수를 나타내는 뷰를 갱신한다`() {
        // given
        val product = Product(name = "우유")
        every { shoppingCache.getCountOfShoppingCartProducts() } returns 3

        // when
        presenter.addProductToShoppingCart(product.toProductUiModel())

        // then
        verify { shoppingCache.insertToShoppingCart(product.id) }
        verify { view.refreshProductCount(3) }
    }

    @Test
    fun `상품의 개수를 증가시키면 저장소에 저장한다`() {
        // given
        val product = Product(name = "우유")
        val cartProduct = CartProduct(name = "우유", count = 1)
        every { shoppingCache.selectShoppingCartProductById(product.id) } returns cartProduct

        // when
        presenter.plusShoppingCartProductCount(product.toProductUiModel())

        // then
        verify {
            shoppingCache.insertToShoppingCart(
                id = product.id,
                count = cartProduct.count.value + 1
            )
        }
    }

    @Test
    fun `상품의 개수를 감소시키면 저장소에 저장한다`() {
        // given
        val product = Product(name = "우유")
        val cartProduct = CartProduct(name = "우유", count = 2)
        every { shoppingCache.selectShoppingCartProductById(product.id) } returns cartProduct

        // when
        presenter.minusShoppingCartProductCount(product.toProductUiModel())

        // then
        verify {
            shoppingCache.insertToShoppingCart(
                id = product.id,
                count = cartProduct.count.value - 1
            )
        }
    }
}
