package woowacourse.shopping.cart

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.cart.model.CartItemQuantity
import woowacourse.shopping.domain.cart.model.CartItems
import woowacourse.shopping.domain.product.model.ImageUrl
import woowacourse.shopping.domain.product.model.Price
import woowacourse.shopping.domain.product.model.Product
import woowacourse.shopping.domain.product.model.ProductName

class CartItemsTest {
    @Test
    fun `장바구니 목록에 상품을 추가했을 때 장바구니 목록에 추가된다`() {
        val cartItems = CartItems(_value = cartItemsValue)
        val targetCartItem = CartItem(
            product = product1,
            quantity = CartItemQuantity(1)
        )

        val addedCartItems = cartItems.addCartItem(targetCartItem)

        assertTrue(addedCartItems.searchCartItem(targetCartItem))
    }

    @Test
    fun `장바구니 목록에 상품 존재하는 상품을 삭제했을 때 장바구니 목록에서 삭제된다`() {
        val cartItems = CartItems(_value = cartItemsValue)
        val targetCartItem = CartItem(
            product = product1,
            quantity = CartItemQuantity(1)
        )

        val removedCartItems = cartItems.removeCartItem(targetCartItem)

        assertFalse(removedCartItems.searchCartItem(targetCartItem))
    }

    @Test
    fun `장바구니 목록 안에 target id와 동일한 상품이 있을 경우 true를 반환한다`() {
        val cartItems = CartItems(_value = cartItemsValue)
        val cartItem1 = CartItem(
            product = product1,
            quantity = CartItemQuantity(1)
        )

        assertTrue(cartItems.searchCartItem(cartItem1))
    }

    @Test
    fun `장바구니 목록 안에 target id와 동일한 상품이 없을 경우 false를 반환한다`() {
        val cartItems = CartItems(_value = cartItemsValue)
        val cartItem4 = CartItem(
            product = product4,
            quantity = CartItemQuantity(1)
        )


        assertFalse(cartItems.searchCartItem(cartItem4))
    }

    @Test
    fun `장바구니 상품 중 우아한두유의 개수를 2 증가시켰을 때 우아한 두유의 개수는 2만큼 증가한다`() {
        val cartItems = CartItems(_value = cartItemsValue)
        val targetCartItem = CartItem(
            product = product1,
            quantity = CartItemQuantity(2)
        )

        val increaseCartItems = cartItems.increaseCartItem(targetCartItem)

        increaseCartItems.getTotalCartItemCount() shouldBe 8

    }

    @Test
    fun `장바구니 상품 중 우아한물의 개수를 1 감소시켰을 때 우아한 두유의 개수는 1만큼 감소한다`() {
        val cartItems = CartItems(_value = cartItemsValue)
        val targetCartItem = CartItem(
            product = product1,
            quantity = CartItemQuantity(1)
        )

        val decreaseCartItems = cartItems.decreaseCartItem(targetCartItem)

        decreaseCartItems.getTotalCartItemCount() shouldBe 5
    }

    @Test
    fun `장바구니 상품의 총 개수를 구할 수 있다`() {
        val cartItems = CartItems(_value = cartItemsValue)

        cartItems.getTotalCartItemCount() shouldBe 6
    }

    private val product1 = Product(
        name = ProductName("우아한두유"),
        price = Price(3000),
        imageUrl = ImageUrl("https://velog.io"),
    )

    private val product2 = Product(
            name = ProductName("우아한물"),
            price = Price(1000),
            imageUrl = ImageUrl("https://naver.com"),
        )

    private val product3 = Product(
        name = ProductName("우아한우유"),
        price = Price(2000),
        imageUrl = ImageUrl("https://google.com"),
    )

    private val product4 = Product(
        name = ProductName("우아한스무디"),
        price = Price(1000),
        imageUrl = ImageUrl("https://daum.net"),
    )

    private val cartItemsValue =
        listOf(
            CartItem(
                product = product1,
                quantity = CartItemQuantity(1)
            ),
            CartItem(
                product = product2,
                quantity = CartItemQuantity(2)
            ),
            CartItem(
                product = product3,
                quantity = CartItemQuantity(3)
            )
        )
}
