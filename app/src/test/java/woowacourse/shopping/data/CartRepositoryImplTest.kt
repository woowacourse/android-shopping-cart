package woowacourse.shopping.data

import io.kotest.matchers.equals.shouldEqual
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.source.CartDataSource
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.fixture.ShoppingFixture

class CartRepositoryImplTest {
    @Test
    fun `장바구니에 아이템을 추가할 수 있다`() =
        runTest {
            val cartRepository = CartRepositoryImpl(cartDataSource = FakeCartDataSource())
            cartRepository.addItem(product = ShoppingFixture.getProduct(), amount = 1)

            cartRepository.getCartItemByPage(1).size shouldEqual 1
        }

    @Test
    fun `장바구니에 동일한 아이템을 추가할 경우 해당 아이템의 개수가 증가한다`() =
        runTest {
            val cartRepository = CartRepositoryImpl(cartDataSource = FakeCartDataSource())

            val product = ShoppingFixture.getProduct(id = "1")

            cartRepository.addItem(product = product, amount = 1)
            cartRepository.addItem(product = product, amount = 1)

            cartRepository
                .getCartItemByPage(1)
                .first { it.product.id == "1" }
                .quantity shouldEqual 2
        }

    @Test
    fun `장바구니에 존재하는 아이템을 삭제할 수 있다`() =
        runTest {
            val cartRepository = CartRepositoryImpl(cartDataSource = FakeCartDataSource())

            val product = ShoppingFixture.getProduct(id = "1")

            cartRepository.addItem(product = product, amount = 1)
            cartRepository.deleteItem(productId = "1")

            cartRepository.getCartItemByPage(1).firstOrNull { it.product == product } shouldBe null
        }
}

class FakeCartDataSource : CartDataSource {
    override val items: MutableList<CartItem> = mutableListOf()

    override fun add(cartItem: CartItem) {
        val idx = items.indexOfFirst { it.product.id == cartItem.product.id }

        if (idx == -1) {
            items.add(cartItem)
            return
        }

        items[idx] = items[idx].addQuantity(amount = cartItem.quantity)
    }

    override fun deleteItem(productId: String) {
        items.removeIf { it.product.id == productId }
    }
}
