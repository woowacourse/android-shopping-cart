package woowacourse.shopping.domain

import io.kotest.matchers.equals.shouldEqual
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import woowacourse.shopping.fixture.ShoppingFixture

class CartTest {
    @Test
    fun `장바구니 아이템 리스트를 갖는다`() {
        val items = listOf(ShoppingFixture.getCartItem())

        val cart = Cart(initialItems = items)

        cart.items shouldEqual items
    }

    @Test
    fun `장바구니에 아이템을 추가할 수 있다`() {
        val cart = Cart()
        cart.add(product = ShoppingFixture.getProduct(), amount = 1)

        cart.items.size shouldEqual 1
    }

    @Test
    fun `장바구니에 동일한 아이템을 추가할 경우 해당 아이템의 개수가 증가한다`() {
        val product = ShoppingFixture.getProduct(id = "1")

        val cart = Cart(listOf(ShoppingFixture.getCartItem(product = product, quantity = 1)))
        cart.add(product = product, amount = 1)

        cart.items.first { it.product.id == "1" }.quantity shouldEqual 2
    }

    @Test
    fun `장바구니에 존재하는 아이템을 삭제할 수 있다`() {
        val product = ShoppingFixture.getProduct(id = "1")
        val cart = Cart(listOf(ShoppingFixture.getCartItem(product = product)))
        cart.deleteItem(product = product)

        cart.items.firstOrNull { it.product == product } shouldBe null
    }
}
