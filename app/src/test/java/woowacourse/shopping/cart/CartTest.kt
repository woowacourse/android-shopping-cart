package woowacourse.shopping.cart

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.shopping.domain.cart.model.Cart
import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.cart.model.CartItems
import woowacourse.shopping.domain.product.model.ImageUrl
import woowacourse.shopping.domain.product.model.Price
import woowacourse.shopping.domain.product.model.Product
import woowacourse.shopping.domain.product.model.ProductName

class CartTest {
    @Test
    fun `장바구니에 상품을 추가했을 때 장바구니에 추가된다`() {
        val cart = Cart(cartItems = cartItemsValue)
        val targetCartItem = cartItem4

        val addedCart = cart.addCartItem(targetCartItem)

        addedCart.getPage(0, 5) shouldBe listOf(cartItem1, cartItem2, cartItem3, cartItem4)
    }

    @Test
    fun `장바구니에 상품 존재하는 상품을 삭제했을 때 장바구니에서 삭제된다`() {
        val cart = Cart(cartItems = cartItemsValue)
        val targetCartItem = cartItem3

        val removedCartItems = cart.removeCartItem(targetCartItem)

        removedCartItems.getPage(0, 5) shouldBe listOf(cartItem1, cartItem2)
    }

    @Test
    fun `장바구니에서 첫 번째 페이지이고, 한 페이지에 3개의 아이템이 보인다면 첫번째부터 세번째까지 아이템을 리스트로 반환한다`() {
        val cart = Cart(cartItems = cartItemsValue)
        val page = 0
        val pageSize = 5

        val result = cart.getPage(page, pageSize)

        assertTrue(result.contains(cartItem1))
        assertTrue(result.contains(cartItem2))
        assertTrue(result.contains(cartItem3))
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
        CartItems(
            value =
                listOf(
                    cartItem1,
                    cartItem2,
                    cartItem3,
                ),
        )
}
