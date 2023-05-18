package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BasketTest {
    @Test
    fun `장바구니에 상품을 담는다`() {
        val basketProducts = listOf<BasketProduct>()
        val basket = Basket(basketProducts)
        val basketProduct = BasketProduct(1, Count(5), Product(1, "새상품", Price(1000), "url"))

        val actual = basket.add(basketProduct)
        val expected = Basket(basketProducts + basketProduct)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `장바구니에 상품을 삭제한다`() {
        val basketProducts = listOf<BasketProduct>(
            BasketProduct(1, Count(5), Product(1, "새상품", Price(1000), "url")),
            BasketProduct(1, Count(5), Product(1, "새상품", Price(1000), "url")),
            BasketProduct(1, Count(5), Product(1, "새상품", Price(1000), "url"))
        )
        val basket = Basket(basketProducts)
        val basketProduct = BasketProduct(1, Count(5), Product(1, "새상품", Price(1000), "url"))

        val actual = basket.delete(basketProduct)
        val expected = Basket(basketProducts - basketProduct)

        assertThat(actual).isEqualTo(expected)
    }
}
