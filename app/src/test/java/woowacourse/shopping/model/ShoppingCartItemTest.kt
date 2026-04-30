package woowacourse.shopping.model

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

class ShoppingCartItemTest {
    private val product = Product(1, "동원 스위트콘", Price(99_800), "")

    @Test
    fun `같은 상품이라도 쇼핑 카트 아이템 id가 다르면 별개로 취급한다`() {
        val shoppingCartItem = ShoppingCartItem(1, product)
        val differentIdShoppingCartItem = ShoppingCartItem(2, product)

        shoppingCartItem shouldNotBe differentIdShoppingCartItem
        shoppingCartItem.hashCode() shouldNotBe differentIdShoppingCartItem.hashCode()
    }

    @Test
    fun `다른 상품이라도 쇼핑 카트 아이템 id가 같으면 동일하다고 취급한다`() {
        val shoppingCartItem = ShoppingCartItem(1, product)
        val differentProductShoppingCartItem =
            ShoppingCartItem(1, Product(2, "동원 참치", Price(9_980), "..."))
        shoppingCartItem shouldBe differentProductShoppingCartItem
        shoppingCartItem.hashCode() shouldBe differentProductShoppingCartItem.hashCode()
    }
}
