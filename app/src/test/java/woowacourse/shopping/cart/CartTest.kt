package woowacourse.shopping.cart

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.cart.CartItem
import woowacourse.shopping.domain.cart.CartItems
import woowacourse.shopping.domain.product.ImageUrl
import woowacourse.shopping.domain.product.Price
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductName

class CartTest {

    @Test
    fun `장바구니에 상품을 추가했을 때 장바구니에 추가된다`() {
        val cart = Cart(cartItems = cartItemsValue)
        val targetCartItem =
            CartItem(
                product =
                    Product(
                        name = ProductName("우아한스무디"),
                        price = Price(1000),
                        imageUrl = ImageUrl("https://daum.net"),
                    ),
            )

        val addedCart = cart.addCartItem(targetCartItem)

        assertTrue(addedCart.searchCartItem(targetCartItem))
    }

    @Test
    fun `장바구니에 상품 존재하는 상품을 삭제했을 때 장바구니에서 삭제된다`() {
        val cart = Cart(cartItems = cartItemsValue)
        val targetCartItem = cartItem3

        val removedCartItems = cart.removeCartItem(targetCartItem)

        assertFalse(removedCartItems.searchCartItem(targetCartItem))
    }

    private val cartItem1 =
        CartItem(
            product =
                Product(
                    name = ProductName("우아한두유"),
                    price = Price(3000),
                    imageUrl = ImageUrl("https://velog.io"),
                ),
        )

    private val cartItem2 =
        CartItem(
            product =
                Product(
                    name = ProductName("우아한물"),
                    price = Price(1000),
                    imageUrl = ImageUrl("https://naver.com"),
                ),
        )

    private val cartItem3 =
        CartItem(
            product =
                Product(
                    name = ProductName("우아한우유"),
                    price = Price(2000),
                    imageUrl = ImageUrl("https://google.com"),
                ),
        )

    private val cartItemsValue =
        CartItems(
            value =
                listOf(
                    cartItem1,
                    cartItem2,
                    cartItem3,
                ),
        )
}
