package woowacourse.shopping.cart

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.cart.model.CartItemQuantity
import woowacourse.shopping.domain.product.model.ImageUrl
import woowacourse.shopping.domain.product.model.Price
import woowacourse.shopping.domain.product.model.Product
import woowacourse.shopping.domain.product.model.ProductName

class CartItemTest {

    @Test
    fun `해당 장바구니 상품과 같은 상품이 존재한다면 true를 반환한다`() {
        val cartItem = CartItem(
            product = mockProduct,
            quantity = CartItemQuantity(1)
        )
        val targetCartItem = cartItem1

        cartItem.isSameCartItem(targetCartItem) shouldBe true
    }

    @Test
    fun `해당 장바구니 상품과 같은 상품이 존재한지 않는다면 false를 반환한다`() {
        val cartItem = CartItem(
            product = mockProduct,
            quantity = CartItemQuantity(1)
        )
        val targetCartItem = cartItem2

        cartItem.isSameCartItem(targetCartItem) shouldBe false
    }

    @Test
    fun `해당 장바구니 상품의 개수를 3만큼 증가지킬 수 있다`() {
        val cartItem = cartItem1

        val targetCartItem = CartItem(
            product = mockProduct,
            quantity = CartItemQuantity(3)
        )
        val increasedCartItem = cartItem.increaseQuantity(targetCartItem)

        increasedCartItem.quantity.value shouldBe 6
    }

    @Test
    fun `해당 장바구니 상품의 개수를 2만큼 줄일 수 있다`() {
        val cartItem = cartItem1
        val targetCartItem = CartItem(
            product = mockProduct,
            quantity = CartItemQuantity(2)
        )
        val decreasedCartItem = cartItem.decreaseQuantity(targetCartItem)


        decreasedCartItem.quantity.value shouldBe 1
    }

    @Test
    fun `해당 장바구니 상품의 총 금액을 계산할 수 있다`() {
        val cartItem = cartItem1
        val totalPrice = cartItem.getCartItemTotalPrice()

        totalPrice shouldBe 9000
    }

    private val mockProduct =
        Product(
            name = ProductName("우아한두유"),
            price = Price(3000),
            imageUrl = ImageUrl("https://velog.io"),
        )

    private val cartItem1 =
        CartItem(
            product = mockProduct,
            quantity = CartItemQuantity(3)
        )

    private val cartItem2 =
        CartItem(
            product =
                Product(
                    name = ProductName("우아한물"),
                    price = Price(1000),
                    imageUrl = ImageUrl("https://naver.com"),
                ),
            quantity = CartItemQuantity(2)
        )
}
