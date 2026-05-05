package woowacourse.shopping.data.cart

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.product.model.ImageUrl
import woowacourse.shopping.domain.product.model.Price
import woowacourse.shopping.domain.product.model.Product
import woowacourse.shopping.domain.product.model.ProductName

class CartRepositoryMockImplTest {
    @Test
    fun `장바구니에 상품을 추가할 수 있다`() {
        val cartRepository = CartRepositoryMockImpl()

        cartRepository.addCartItem(cartItem1)
        val updatedCart = cartRepository.getCart()

        updatedCart.getPage(0, 5) shouldBe listOf(cartItem1)
    }

    @Test
    fun `장바구니에 상품을 제거할 수 있다`() {
        val cartRepository = CartRepositoryMockImpl()

        cartRepository.addCartItem(cartItem1)
        cartRepository.addCartItem(cartItem2)
        cartRepository.addCartItem(cartItem3)

        cartRepository.removeCartItem(cartItem2)

        val updatedCart = cartRepository.getCart()

        updatedCart.getPage(0, 5) shouldBe listOf(cartItem1, cartItem3)
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
}
