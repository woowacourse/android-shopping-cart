package woowacourse.shopping.domain

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.entity.Cart

class ShoppingCartTest {
    @Test
    fun `쇼핑 카트에 상품 추가`() {
        // given
        val cart = Cart()
        val expectCart = Cart(product(1))
        // when
        val newShoppingCart = cart.add(product(id = 1))
        // then
        newShoppingCart shouldBe expectCart
    }

    @Test
    fun `해당 상품이 쇼핑 카트에 있을 때, 상품 삭제`() {
        // given
        val cart = Cart(product(1))
        val expectCart = Cart()
        // when
        val newShoppingCart = cart.remove(product(id = 1))
        // then
        newShoppingCart shouldBe expectCart
    }

    @Test
    fun `해당 상품이 쇼핑 카트에 없을 때, 상품을 삭제하면 아무일도 벌어지지 않는다`() {
        // given
        val cart = Cart()
        val expectCart = Cart()
        // when
        val newShoppingCart = cart.remove(product(id = 1))
        // then
        newShoppingCart shouldBe expectCart
    }

    @Test
    fun `쇼핑 카트에 담긴 상품들의 가격 합을 구한다`() {
        // given
        val products = listOf(product(1, 1000), product(2, 2000))
        val cart = Cart(products)
        val expectPrice = 3000
        // when
        val price = cart.totalPrice()
        // then
        price shouldBe expectPrice
    }
}
