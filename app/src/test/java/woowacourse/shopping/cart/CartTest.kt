package woowacourse.shopping.cart

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.shopping.domain.cart.model.Cart
import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.cart.model.CartItemQuantity
import woowacourse.shopping.domain.cart.model.CartItems
import woowacourse.shopping.domain.product.model.ImageUrl
import woowacourse.shopping.domain.product.model.Price
import woowacourse.shopping.domain.product.model.Product
import woowacourse.shopping.domain.product.model.ProductName

class CartTest {
    @Test
    fun `장바구니에 상품을 추가했을 때 장바구니에 추가된다`() {
        val cart = Cart(cartItems = cartItemsValue)
        val targetCartItem =
            CartItem(
                product = product4,
                quantity = CartItemQuantity(1),
            )
        val targetQuantity = 1

        val addedCart = cart.addCartItem(targetCartItem, targetQuantity)

        addedCart.searchCartItem(targetCartItem) shouldBe true
    }

    @Test
    fun `장바구니에 상품 존재하는 상품을 삭제했을 때 장바구니에서 삭제된다`() {
        val cart = Cart(cartItems = cartItemsValue)
        val targetCartItem =
            CartItem(
                product = product3,
                quantity = CartItemQuantity(2),
            )

        val removedCartItems = cart.removeCartItem(targetCartItem)

        removedCartItems.searchCartItem(targetCartItem) shouldBe false
    }

    @Test
    fun `장바구니에서 첫 번째 페이지이고, 한 페이지에 3개의 아이템이 보인다면 첫번째부터 세번째까지 아이템을 리스트로 반환한다`() {
        val cart = Cart(cartItems = cartItemsValue)
        val page = 0
        val pageSize = 5

        val result = cart.getPage(page, pageSize)

        result.contains(CartItem(product = product1, quantity = CartItemQuantity(1))) shouldBe true
        result.contains(CartItem(product = product2, quantity = CartItemQuantity(2))) shouldBe true
        result.contains(CartItem(product = product3, quantity = CartItemQuantity(3))) shouldBe true
    }

    @Test
    fun `0보다 작은 값이 페이지 값으로 들어갈 경우 예외가 발생한다`() {
        val cart = Cart(cartItems = cartItemsValue)
        val page = -1
        val pageSize = 5

        assertThrows<IllegalArgumentException> {
            cart.getPage(page, pageSize)
        }
    }

    @Test
    fun `해당 장바구니 상품이 장바구니 안에 존재하면 true를 반환한다`() {
        val cart = Cart(cartItems = cartItemsValue)
        val targetCartItem =
            CartItem(
                product = product3,
                quantity = CartItemQuantity(2),
            )

        cart.searchCartItem(targetCartItem) shouldBe true
    }

    @Test
    fun `해당 장바구니 상품이 장바구니 안에 존재하지 않으면 false를 반환한다`() {
        val cart = Cart(cartItems = cartItemsValue)
        val targetCartItem =
            CartItem(
                product = product4,
                quantity = CartItemQuantity(2),
            )

        cart.searchCartItem(targetCartItem) shouldBe false
    }

    @Test
    fun `해당 장바구니 상품의 개수를 2만큼 증가시키면 해당 장바구니의 총 상품 수가 2만큼 증가한다`() {
        val cart = Cart(cartItems = cartItemsValue)
        val targetCartItem =
            CartItem(
                product = product3,
                quantity = CartItemQuantity(2),
            )
        val targetQuantity = 2

        val increasedCart = cart.addCartItem(targetCartItem, targetQuantity)

        increasedCart.getTotalCartItemCount() shouldBe 8
    }

    @Test
    fun `해당 장바구니 상품의 개수를 1만큼 감소시키면 해당 장바구니의 총 상품 수가 1만큼 감소한다`() {
        val cart = Cart(cartItems = cartItemsValue)
        val targetCartItem =
            CartItem(
                product = product2,
                quantity = CartItemQuantity(1),
            )
        val targetQuantity = 1

        val decreaseCart = cart.minusCartItem(targetCartItem, targetQuantity)

        decreaseCart.getTotalCartItemCount() shouldBe 5
    }

    @Test
    fun `해당 장바구니의 총 상품 수를 반환할 수 있다`() {
        val cart = Cart(cartItems = cartItemsValue)

        cart.getTotalCartItemCount() shouldBe 6
    }

    private val product1 =
        Product(
            name = ProductName("우아한두유"),
            price = Price(3000),
            imageUrl = ImageUrl("https://velog.io"),
        )

    private val product2 =
        Product(
            name = ProductName("우아한물"),
            price = Price(1000),
            imageUrl = ImageUrl("https://naver.com"),
        )

    private val product3 =
        Product(
            name = ProductName("우아한우유"),
            price = Price(2000),
            imageUrl = ImageUrl("https://google.com"),
        )

    private val product4 =
        Product(
            name = ProductName("우아한스무디"),
            price = Price(1000),
            imageUrl = ImageUrl("https://daum.net"),
        )

    private val cartItemsValue =
        CartItems(
            listOf(
                CartItem(
                    product = product1,
                    quantity = CartItemQuantity(1),
                ),
                CartItem(
                    product = product2,
                    quantity = CartItemQuantity(2),
                ),
                CartItem(
                    product = product3,
                    quantity = CartItemQuantity(3),
                ),
            ),
        )
}
