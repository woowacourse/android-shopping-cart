package woowacourse.shopping

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import model.Product
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.database.product.ProductRepository
import woowacourse.shopping.shopping.ShoppingContract
import woowacourse.shopping.shopping.ShoppingPresenter
import woowacourse.shopping.util.toUiModel

class ShoppingPresenterTest {

    lateinit var presenter: ShoppingContract.Presenter
    lateinit var view: ShoppingContract.View
    lateinit var repository: ShoppingRepository
    lateinit var productRepository: ProductRepository
    lateinit var products: List<Product>

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        view = mockk(relaxed = true)
        productRepository = mockk(relaxed = true)
        presenter = ShoppingPresenter(view, repository, productRepository)
        products = listOf(
            Product(name = "아메리카노"),
            Product(name = "카페라떼"),
        )
    }

    @Test
    fun `저장소로부터 상품 목록을 받아와서 뷰를 초기화한다`() {
        // given
        val products = products.map { it.toUiModel() }
        every {
            view.setUpShoppingView(
                products = products,
                any(),
                any(),
            )
        } just Runs
        every { repository.selectRecentViewedProducts() } returns this.products
        every { repository.selectProducts(any(), any()) } returns this.products

        // when
        presenter.loadProducts()

        // then
        val expected = listOf(
            ProductUiModel(name = "아메리카노"),
            ProductUiModel(name = "카페라떼"),
        )
        assertEquals(expected, products)
    }

    @Test
    fun `저장소로부터 최근 본 상품을 받아와서 뷰를 초기화한다`() {
        // given
        val recentViewedProducts = products.map { it.toUiModel() }
        every {
            view.setUpShoppingView(
                any(),
                recentViewedProducts = recentViewedProducts,
                any(),
            )
        } just Runs
        every { repository.selectRecentViewedProducts() } returns this.products

        // when
        presenter.loadProducts()

        // then
        val expected = listOf(
            ProductUiModel(name = "아메리카노"),
            ProductUiModel(name = "카페라떼"),
        )
        assertEquals(expected, recentViewedProducts)
        verify {
            view.setUpShoppingView(
                any(),
                recentViewedProducts = recentViewedProducts,
                any(),
            )
        }
    }

    @Test
    fun `저장소에서 추가적인 상품 목록을 불러와서 뷰를 초기화한다`() {
        // given
        var additionalProducts = listOf<Product>()
        every {
            productRepository.loadProducts(
                any(),
                { additionalProducts = products },
                {},
            )
        } just Runs

        // when
        presenter.readMoreShoppingProducts()

        // then
        verify { view.refreshMoreShoppingProductsView(additionalProducts.map { it.toUiModel() }) }
    }

    @Test
    fun `선택한 상품을 최근 본 상품 저장소에 저장한다`() {
        // when
        presenter.addToRecentViewedProduct(1)

        // then
        verify { repository.insertToRecentViewedProducts(1) }
    }

    @Test
    fun `저장소로부터 최근 본 상품을 받아와서 뷰를 갱신한다`() {
        // given
        every { repository.selectRecentViewedProducts() } returns products

        // when
        presenter.updateRecentViewedProducts()

        // then
        val expected = products.map { it.toUiModel() }
        verify { view.refreshRecentViewedProductsView(expected) }
    }

    @Test
    fun `장바구니에 있는 상품의 수량을 더하면 저장소에 갱신한다`() {
        // given
        val cartProducts = listOf(
            CartProduct(Product(0), count = 1),
        )
        val slot = slot<Int>()
        every { repository.selectAllShoppingCartProducts() } returns cartProducts
        every { repository.updateShoppingCartCount(0, capture(slot)) } just Runs

        // when
        presenter.changeShoppingCartProductCount(0, true)

        // then
        assertEquals(2, slot.captured)
    }

    @Test
    fun `장바구니에 없는 상품의 수량을 더하면 저장소에 저장한다`() {
        // given
        val cartProducts = listOf(
            CartProduct(Product(0), count = 1),
        )
        val slot = slot<Int>()
        every { repository.selectAllShoppingCartProducts() } returns cartProducts
        every { repository.insertToShoppingCart(1, capture(slot), true) } just Runs

        // when
        presenter.changeShoppingCartProductCount(1, true)

        // then
        assertEquals(1, slot.captured)
    }

    @Test
    fun `장바구니에 1개만 담겨있던 상품의 수량을 빼면 저장소에 삭제한다`() {
        // given
        val cartProducts = listOf(
            CartProduct(Product(0), count = 1),
        )
        every { repository.selectAllShoppingCartProducts() } returns cartProducts
        every { repository.deleteFromShoppingCart(0) } just Runs

        // when
        presenter.changeShoppingCartProductCount(0, false)

        // then
        verify { repository.deleteFromShoppingCart(0) }
    }

    @Test
    fun `상단 툴바의 장바구니에 담긴 개수를 갱신한다`() {
        // given
        val cartProducts = listOf(
            CartProduct(Product(0), count = 1),
            CartProduct(Product(1), count = 3),
        )
        val totalCount = slot<Int>()
        every { repository.selectAllShoppingCartProducts() } returns cartProducts
        every { view.updateToolbar(capture(totalCount)) } just Runs

        // when
        presenter.updateToolbar()

        // then
        assertEquals(4, totalCount.captured)
    }
}
