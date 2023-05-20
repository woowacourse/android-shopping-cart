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
    fun `장바구니에 이미 같은 상품이 담겨있는데 상품을 추가하면 상품 갯수가 늘어난다 `() {
        val basketProducts =
            listOf<BasketProduct>(BasketProduct(1, Count(5), Product(1, "새상품", Price(1000), "url")))
        val basket = Basket(basketProducts)
        val basketProduct = BasketProduct(1, Count(5), Product(1, "새상품", Price(1000), "url"))

        val actual = basket.add(basketProduct)
        val expected = Basket(
            listOf<BasketProduct>(
                BasketProduct(
                    1,
                    Count(10),
                    Product(1, "새상품", Price(1000), "url")
                )
            )
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `장바구니에 이미 같은 상품이 담겨있지 않은데 제품을 추가하면 새로운 상품으로 담긴다 `() {
        val basketProducts = listOf<BasketProduct>(
            BasketProduct(1, Count(5), Product(1, "새상품", Price(1000), "url")),
            BasketProduct(2, Count(5), Product(3, "새상품", Price(1000), "url"))
        )
        val basket = Basket(basketProducts)
        val basketProduct = BasketProduct(2, Count(5), Product(3, "새상품", Price(1000), "url"))

        val actual = basket.add(basketProduct)
        val expected = Basket(
            listOf<BasketProduct>(
                BasketProduct(1, Count(5), Product(1, "새상품", Price(1000), "url")),
                BasketProduct(2, Count(5), Product(3, "새상품", Price(1000), "url"))
            )
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `장바구니에 이미 같은 상품이 담겨있는데 상품을 뺴면 갯수가 줄어든다(갯수가 0개가 되면 항목자체가 사라진다) `() {
        val basketProducts = listOf<BasketProduct>(
            BasketProduct(1, Count(5), Product(1, "새상품", Price(1000), "url")),
            BasketProduct(2, Count(5), Product(3, "새상품", Price(1000), "url"))
        )
        val basket = Basket(basketProducts)
        val basketProduct = BasketProduct(2, Count(5), Product(3, "새상품", Price(1000), "url"))

        val actual = basket.delete(basketProduct)
        val expected = Basket(
            listOf<BasketProduct>(
                BasketProduct(1, Count(5), Product(1, "새상품", Price(1000), "url"))
            )
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `장바구니에 이미 같은 상품이 담겨있는데 상품을 뺴면 갯수가 줄어든다(갯수가 0이 아니라면 항목은 남아있다) `() {
        val basketProducts = listOf<BasketProduct>(
            BasketProduct(1, Count(5), Product(1, "새상품", Price(1000), "url")),
            BasketProduct(2, Count(5), Product(3, "새상품", Price(1000), "url"))
        )
        val basket = Basket(basketProducts)
        val basketProduct = BasketProduct(2, Count(3), Product(3, "새상품", Price(1000), "url"))

        val actual = basket.delete(basketProduct)
        val expected = Basket(
            listOf<BasketProduct>(
                BasketProduct(1, Count(5), Product(1, "새상품", Price(1000), "url")),
                BasketProduct(2, Count(2), Product(3, "새상품", Price(1000), "url"))
            )
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `장바구니에 있는 상품의 총액을 계산한다(500원 *3개,1000*5개 총액 6500)`() {
        // given
        val basketProducts = listOf<BasketProduct>(
            BasketProduct(1, Count(3), Product(1, "새상품", Price(500), "url")),
            BasketProduct(2, Count(5), Product(3, "새상품", Price(1000), "url"))
        )
        val basket = Basket(basketProducts)

        // when
        val expected = 6500
        val actual = basket.getTotalPrice()

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `장바구니에 있는 상품중 체크된 상품의 총액을 계산한다(체크항목(500원 *3개,1000*5개) 비체크항목 700*2개 총액 6500)`() {
        // given
        val basketProducts = listOf<BasketProduct>(
            BasketProduct(1, Count(3), Product(1, "새상품", Price(500), "url"), true),
            BasketProduct(2, Count(5), Product(3, "새상품", Price(1000), "url"), true),
            BasketProduct(2, Count(2), Product(4, "새상품", Price(700), "url"), false)
        )
        val basket = Basket(basketProducts)

        // when
        val expected = 6500
        val actual = basket.getCheckedProductsTotalPrice()

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `장바구니 index3 아이템부터 5개를 가져온다 총아이템수 30개`() {
        // given
        val basketProducts = List(30) {
            BasketProduct(it, Count(3), Product(it, "새상품", Price(500), "url"))
        }
        val basket = Basket(basketProducts)

        // when
        val expected = Basket(
            listOf(
                BasketProduct(3, Count(3), Product(3, "새상품", Price(500), "url")),
                BasketProduct(4, Count(3), Product(4, "새상품", Price(500), "url")),
                BasketProduct(5, Count(3), Product(5, "새상품", Price(500), "url")),
                BasketProduct(6, Count(3), Product(6, "새상품", Price(500), "url")),
                BasketProduct(7, Count(3), Product(7, "새상품", Price(500), "url"))
            )
        )
        val actual = basket.getSubBasketByStartId(3, 5)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `장바구니 index3 아이템부터 5개를 가져온다 총아이템수 6개`() {
        // given
        val basketProducts = List(6) {
            BasketProduct(it, Count(3), Product(it, "새상품", Price(500), "url"))
        }
        val basket = Basket(basketProducts)

        println(basket.products.lastIndex)

        // when
        val expected = Basket(
            listOf(
                BasketProduct(3, Count(3), Product(3, "새상품", Price(500), "url")),
                BasketProduct(4, Count(3), Product(4, "새상품", Price(500), "url")),
                BasketProduct(5, Count(3), Product(5, "새상품", Price(500), "url"))
            )
        )
        val actual = basket.getSubBasketByStartId(3, 5)

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
