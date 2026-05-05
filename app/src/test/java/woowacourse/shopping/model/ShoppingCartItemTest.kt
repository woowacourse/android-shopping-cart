package woowacourse.shopping.model

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

class ShoppingCartItemTest {
    private val product = Product(1, ProductTitle("동원 스위트콘"), Price(99_800), "")

    @Test
    fun `id와 상품이 다르면 별개로 취급한다`() {
        val shoppingCartItem = ShoppingCartItem(1, product)
        val differentIdShoppingCartItem = ShoppingCartItem(2, product)

        shoppingCartItem shouldNotBe differentIdShoppingCartItem
    }

    @Test
    fun `id와 상품이 같으면 같은 쇼핑 카트 아이템으로 취급한다`() {
        val shoppingCartItem = ShoppingCartItem(1, product)
        shoppingCartItem shouldBe shoppingCartItem
    }
}
