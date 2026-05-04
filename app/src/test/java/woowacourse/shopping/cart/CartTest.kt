package woowacourse.shopping.cart

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
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

        assertTrue(addedCart.cartItems.searchCartItem(targetCartItem))
    }

    @Test
    fun `장바구니에 상품 존재하는 상품을 삭제했을 때 장바구니에서 삭제된다`() {
        val cart = Cart(cartItems = cartItemsValue)
        val targetCartItem = cartItem3

        val removedCartItems = cart.removeCartItem(targetCartItem)

        assertFalse(removedCartItems.cartItems.searchCartItem(targetCartItem))
    }

    @Test
    fun `getPage는 page와 pageSize에 해당하는 부분 리스트를 반환한다`() {
        val cart = Cart(cartItems = cartItemsValue)

        val firstPage = cart.getPage(page = 0, pageSize = 2)

        assertEquals(listOf(cartItem1, cartItem2), firstPage)
    }

    @Test
    fun `getPage는 마지막 페이지가 pageSize보다 적게 차있어도 정상 반환한다`() {
        val cart = Cart(cartItems = cartItemsValue)

        val lastPage = cart.getPage(page = 1, pageSize = 2)

        assertEquals(listOf(cartItem3), lastPage)
    }

    @Test
    fun `getPage는 범위를 넘는 page를 요청하면 빈 리스트를 반환한다`() {
        val cart = Cart(cartItems = cartItemsValue)

        val outOfRangePage = cart.getPage(page = 99, pageSize = 2)

        assertTrue(outOfRangePage.isEmpty())
    }

    @Test
    fun `getPage의 page가 음수면 예외가 발생한다`() {
        val cart = Cart(cartItems = cartItemsValue)

        assertThrows(IllegalArgumentException::class.java) {
            cart.getPage(page = -1, pageSize = 2)
        }
    }

    @Test
    fun `getPage의 pageSize가 0 이하면 예외가 발생한다`() {
        val cart = Cart(cartItems = cartItemsValue)

        assertThrows(IllegalArgumentException::class.java) {
            cart.getPage(page = 0, pageSize = 0)
        }
    }
}
