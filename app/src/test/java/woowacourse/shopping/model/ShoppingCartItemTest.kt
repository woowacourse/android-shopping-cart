package woowacourse.shopping.model

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ShoppingCartItemTest {
    private val product = Product("1", ProductTitle("동원 스위트콘"), Price(99_800), "")

    @Test
    fun `1개가 있는 쇼핑 카트 상품에 수량을 2개 증가시키면 3이된다`() {
        val shoppingCartItem = ShoppingCartItem(Quantity(1), product)

        val increasedShoppingCartItem = shoppingCartItem.increaseQuantity(2)

        increasedShoppingCartItem.quantity.value shouldBe 3
    }

    @Test
    fun `증감량이 음수라면 쇼핑 카트 상품의 수량은 변화가 없다`() {
        val shoppingCartItem = ShoppingCartItem(Quantity(1), product)

        shoppingCartItem.decreaseQuantity(-1) shouldBe shoppingCartItem
        shoppingCartItem.increaseQuantity(-1) shouldBe shoppingCartItem
    }

    @Test
    fun `쇼핑 카트 상품의 수량이 0보다 작아지면 null을 반환한다`() {
        val shoppingCartItem = ShoppingCartItem(Quantity(1), product)

        shoppingCartItem.decreaseQuantity(2) shouldBe null
    }
}
