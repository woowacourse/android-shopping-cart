package woowacourse.shopping.domain.model.cart

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Quantity
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.ProductTitle

class CartItemsTest {
    private val product =
        Product(
            id = "1",
            imageUrl = "",
            productTitle = ProductTitle("콜라"),
            price = Price(1000),
        )

    @Test
    fun `이미 담긴 상품을 장바구니에 추가하면 신규 항목이 추가되지 않는다`() {
        val cartItems =
            CartItems()
                .add(CartItem(product, Quantity(1)))
                .add(CartItem(product, Quantity(1)))

        assertEquals(1, cartItems.items.size)
    }
}
