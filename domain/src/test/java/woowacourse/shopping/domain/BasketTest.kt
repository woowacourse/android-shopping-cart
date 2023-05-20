package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BasketTest {
    @Test
    fun `장바구니에 상품을 담는다`() {
        val products = listOf<Product>()
        val basket = Basket(products)
        val product = Product(0, "새상품", Price(1000), "")

        val actual = basket.increaseProductCount(product)
        val expected = Basket(products + product)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `장바구니에 상품을 삭제한다`() {
        val products =
            listOf(Product(0, "새상품", Price(1000), ""), Product(1, "새상품", Price(1000), ""))
        val basket = Basket(products)
        val product = Product(0, "새상품", Price(1000), "")

        val actual = basket.delete(product)
        val expected = Basket(products - product)

        assertThat(actual).isEqualTo(expected)
    }
}
