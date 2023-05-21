package woowacourse.shopping

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import model.Cart
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.database.ShoppingCache
import woowacourse.shopping.shoppingcart.CartContract
import woowacourse.shopping.shoppingcart.CartPage
import woowacourse.shopping.shoppingcart.CartPresenter
import woowacourse.shopping.util.toUiModel

class CartPresenterTest {

    private lateinit var presenter: CartPresenter
    private lateinit var view: CartContract.View
    private lateinit var shoppingCache: ShoppingCache
    private lateinit var cartPage: CartPage

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        shoppingCache = mockk(relaxed = true)
        cartPage = CartPage(Cart())
        presenter = CartPresenter(
            view = view,
            shoppingCache = shoppingCache,
            cartPage = cartPage
        )
    }

    @Test
    fun `저장소에서 장바구니에 담긴 상품들을 받아와서 페이지에 보이는 상품들을 업데이트한다`() {
        // given
        val products = listOf(
            CartProduct(name = "아메리카노", id = 1),
            CartProduct(name = "밀크티", id = 2),
        )
        every { shoppingCache.selectShoppingCartProducts() } returns products

        // when
        presenter.loadShoppingCartProducts()

        // then
        assertEquals(
            products.map { it.toUiModel() },
            presenter.showingProducts.value
        )
    }

    @Test
    fun `상품을 삭제하면 저장소에게 삭제할 상품의 아이디를 전해주고 페이지에 보이는 상품을 업데이트한다`() {
        // given
        val removingProduct = CartProduct(id = 1)
        val products = listOf(
            CartProduct(name = "아메리카노", id = 1),
            CartProduct(name = "밀크티", id = 2),
        )
        every { shoppingCache.selectShoppingCartProducts() } returns products
        presenter.loadShoppingCartProducts()

        // when
        presenter.removeShoppingCartProduct(removingProduct.product.id)

        // then
        verify { shoppingCache.deleteFromShoppingCart(id = 1) }

        val expected = listOf(CartProduct(id = 2).toUiModel())
        assertEquals(expected, presenter.showingProducts.value)
    }

    @Test
    fun `상품의 개수를 추가시키면 저장소에 개수가 증가한 데이터를 삽입하고 페이지에 보이는 상품을 업데이트한다`() {
        // given
        val products = listOf(
            CartProduct(name = "아메리카노", id = 1, count = 5),
            CartProduct(name = "밀크티", id = 2, count = 1),
        )
        every { shoppingCache.selectShoppingCartProducts() } returns products
        presenter.loadShoppingCartProducts()

        // when
        presenter.plusShoppingCartProductCount(id = 1)

        // then
        verify { shoppingCache.insertToShoppingCart(id = 1, count = 6) }

        val expected = listOf(
            CartProduct(id = 1, name = "아메리카노", count = 6),
            CartProduct(id = 2, name = "밀크티", count = 1)
        ).map { it.toUiModel() }

        assertEquals(expected, presenter.showingProducts.value)
    }

    @Test
    fun `상품의 개수를 감소시키면 저장소에 개수가 감소한 데이터를 삽입하고 페이지에 보이는 상품을 업데이트한다`() {
        // given
        val products = listOf(
            CartProduct(id = 1, name = "아메리카노", count = 5),
            CartProduct(id = 2, name = "밀크티", count = 6),
        )
        every { shoppingCache.selectShoppingCartProducts() } returns products
        presenter.loadShoppingCartProducts()

        // when
        presenter.minusShoppingCartProductCount(id = 2)

        // then
        verify { shoppingCache.insertToShoppingCart(id = 2, count = 5) }

        val expected = listOf(
            CartProduct(id = 1, name = "아메리카노", count = 5),
            CartProduct(id = 2, name = "밀크티", count = 5)
        ).map { it.toUiModel() }

        assertEquals(expected, presenter.showingProducts.value)
    }

    @Test
    fun `상품의 총 가격을 계산해서 화면에 보이는 가격을 초기화한다`() {
        // given
        val products = listOf(
            CartProduct(price = 5000),
            CartProduct(price = 1000),
            CartProduct(price = 2000),
        )
        every { shoppingCache.selectShoppingCartProducts() } returns products

        // when
        presenter.loadShoppingCartProducts()

        // then
        val expected = 8000

        assertEquals(expected, presenter.totalPrice.value)
    }

    @Test
    fun `장바구니에 있는 상품을 선택하지 않은 상태로 바꾸면 페이지에 보이는 상품이 갱신된다`() {
        // given
        val products = listOf(
            CartProduct(id = 1, name = "아메리카노"),
            CartProduct(id = 2, name = "밀크티"),
        )
        every { shoppingCache.selectShoppingCartProducts() } returns products
        presenter.loadShoppingCartProducts()

        // when
        presenter.changeProductSelectedState(id = 1, isSelected = false)

        // then
        val expected = listOf(
            CartProduct(id = 1, name = "아메리카노", isSelected = false),
            CartProduct(id = 2, name = "밀크티")
        ).map { it.toUiModel() }

        assertEquals(expected, presenter.showingProducts.value)
    }
}
