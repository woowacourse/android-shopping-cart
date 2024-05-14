package woowacourse.shopping.domain

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ShoppingCartTest {
    @Test
    fun `쇼핑 카트에 상품 추가`() {
        // given
        val shoppingCart = ShoppingCart()
        val expectShoppingCart = ShoppingCart(product(1))
        // when
        val newShoppingCart = shoppingCart.add(product(id = 1))
        // then
        newShoppingCart shouldBe expectShoppingCart
    }

    @Test
    fun `해당 상품이 쇼핑 카트에 있을 때, 상품 삭제`() {
        // given
        val shoppingCart = ShoppingCart(product(1))
        val expectShoppingCart = ShoppingCart()
        // when
        val newShoppingCart = shoppingCart.remove(product(id = 1))
        // then
        newShoppingCart shouldBe expectShoppingCart
    }

    @Test
    fun `해당 상품이 쇼핑 카트에 없을 때, 상품을 삭제하면 아무일도 벌어지지 않는다`() {
        // given
        val shoppingCart = ShoppingCart()
        val expectShoppingCart = ShoppingCart()
        // when
        val newShoppingCart = shoppingCart.remove(product(id = 1))
        // then
        newShoppingCart shouldBe expectShoppingCart
    }

    @Test
    fun `쇼핑 카트에 담긴 상품들의 가격 합을 구한다`() {
        // given
        val products = listOf(product(1, 1000), product(2, 2000))
        val shoppingCart = ShoppingCart(products)
        val expectPrice = 3000
        // when
        val price = shoppingCart.totalPrice()
        // then
        price shouldBe expectPrice
    }
}
