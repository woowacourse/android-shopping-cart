package woowacourse.shopping.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ShoppingCartTest {
    private lateinit var shoppingCart: ShoppingCart

    @BeforeEach
    fun setup() {
        val products = listOf(Product(1, "상품1", "imageUrl1", Price(1000)))
        shoppingCart = ShoppingCart(products)
    }

    @Test
    fun `장바구니에 상품을 제거할 수 있다`() {
        assertThat(shoppingCart.products.count()).isEqualTo(1)

        shoppingCart.deleteProduct(1)

        assertThat(shoppingCart.products.count()).isEqualTo(0)
    }
}
