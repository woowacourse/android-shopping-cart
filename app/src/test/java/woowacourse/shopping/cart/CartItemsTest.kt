package woowacourse.shopping.cart

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.cart.CartItem
import woowacourse.shopping.domain.cart.CartItems
import woowacourse.shopping.domain.product.ImageUrl
import woowacourse.shopping.domain.product.Price
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductName

class CartItemsTest {
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

    private val cartItem4 =
        CartItem(
            product =
                Product(
                    name = ProductName("우아한스무디"),
                    price = Price(1000),
                    imageUrl = ImageUrl("https://daum.net"),
                ),
        )

    private val cartItemsValue =
        listOf(
            cartItem1,
            cartItem2,
            cartItem3,
        )

    @Test
    fun `장바구니 목록에 상품을 추가했을 때 장바구니 목록에 추가된다`() {
        val cartItems = CartItems(value = cartItemsValue)
        val targetCartItem = cartItem4

        val addedCartItems = cartItems.addCartItem(targetCartItem)

        assertTrue(addedCartItems.searchCartItem(targetCartItem))
    }

    @Test
    fun `장바구니 목록에 상품 존재하는 상품을 삭제했을 때 장바구니 목록에서 삭제된다`() {
        val cartItems = CartItems(value = cartItemsValue)
        val targetCartItem = cartItem3

        val removedCartItems = cartItems.removeCartItem(targetCartItem)

        assertFalse(removedCartItems.searchCartItem(targetCartItem))
    }

    @Test
    fun `장바구니 목록 안에 target id와 동일한 상품이 있을 경우 true를 반환한다`() {
        val cartItems = CartItems(value = cartItemsValue)

        assertTrue(cartItems.searchCartItem(cartItem1))
    }

    @Test
    fun `장바구니 목록 안에 target id와 동일한 상품이 없을 경우 false를 반환한다`() {
        val cartItems = CartItems(value = cartItemsValue)

        assertFalse(cartItems.searchCartItem(cartItem4))
    }

    @Test
    fun `subList는 정상 범위에서 부분 리스트를 반환한다`() {
        val cartItems = CartItems(value = cartItemsValue)

        val sub = cartItems.subList(fromIndex = 0, toIndex = 2)

        assertEquals(listOf(cartItem1, cartItem2), sub)
    }

    @Test
    fun `subList는 fromIndex가 음수여도 0으로 클램핑된다`() {
        val cartItems = CartItems(value = cartItemsValue)

        val sub = cartItems.subList(fromIndex = -3, toIndex = 2)

        assertEquals(listOf(cartItem1, cartItem2), sub)
    }

    @Test
    fun `subList는 toIndex가 size보다 커도 size로 클램핑된다`() {
        val cartItems = CartItems(value = cartItemsValue)

        val sub = cartItems.subList(fromIndex = 1, toIndex = 100)

        assertEquals(listOf(cartItem2, cartItem3), sub)
    }

    @Test
    fun `subList는 fromIndex가 size보다 크면 빈 리스트를 반환한다`() {
        val cartItems = CartItems(value = cartItemsValue)

        val sub = cartItems.subList(fromIndex = 10, toIndex = 20)

        assertTrue(sub.isEmpty())
    }

    @Test
    fun `subList는 fromIndex가 toIndex보다 크면 빈 리스트를 반환한다`() {
        val cartItems = CartItems(value = cartItemsValue)

        val sub = cartItems.subList(fromIndex = 3, toIndex = 1)

        assertTrue(sub.isEmpty())
    }
}
