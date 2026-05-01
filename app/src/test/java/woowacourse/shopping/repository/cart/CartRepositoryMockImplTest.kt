package woowacourse.shopping.repository.cart

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.cart.CartItem
import woowacourse.shopping.domain.product.ImageUrl
import woowacourse.shopping.domain.product.Price
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductName

class CartRepositoryMockImplTest {
    @Test
    fun `장바구니에 상품을 추가할 수 있다`() {
        val cartRepository = CartRepositoryMockImpl

        runBlocking {
            cartRepository.addCartItem(cartItem1)
        }
        val updatedCart = runBlocking {
            cartRepository.getCart()
        }
        assertTrue(updatedCart.searchCartItem(cartItem1))
    }

    @Test
    fun `장바구니에 상품을 제거할 수 있다`() {
        val cartRepository = CartRepositoryMockImpl

        runBlocking {
            cartRepository.addCartItem(cartItem1)
            cartRepository.addCartItem(cartItem2)
            cartRepository.addCartItem(cartItem3)
        }
        runBlocking {
            cartRepository.removeCartItem(cartItem2)
        }

        val updatedCart = runBlocking {
            cartRepository.getCart()
        }

        assertTrue(updatedCart.searchCartItem(cartItem1))
        assertFalse(updatedCart.searchCartItem(cartItem2))
        assertTrue(updatedCart.searchCartItem(cartItem3))
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
