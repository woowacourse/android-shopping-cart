package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Product

class CartTest {
    @Test
    fun `장바구니에 상품을 담는다`() {
        val products = listOf<Product>()
        val cart = Cart(products)
        val product = Product(0, "새상품", Price(1000), "")

        val actual = cart.increaseProductCount(product)
        val expected = Cart(products + product)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `장바구니에 상품을 삭제한다`() {
        val products =
            listOf(Product(0, "새상품", Price(1000), ""), Product(1, "새상품", Price(1000), ""))
        val cart = Cart(products)
        val product = Product(0, "새상품", Price(1000), "")

        val actual = cart.delete(product)
        val expected = Cart(products - product)

        assertThat(actual).isEqualTo(expected)
    }
}
