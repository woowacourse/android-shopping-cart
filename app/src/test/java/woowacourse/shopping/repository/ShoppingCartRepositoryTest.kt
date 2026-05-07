package woowacourse.shopping.repository

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import woowacourse.shopping.model.Price
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductTitle
import woowacourse.shopping.model.ShoppingCartItem

class ShoppingCartRepositoryTest {
    private val product = Product("1", ProductTitle("동원 스위트콘"), Price(99_800), "")

    @Test
    fun `장바구니에 상품을 추가할 수 있다`() {
        val shoppingCartRepository: ShoppingCartRepository = MemoryShoppingCartRepository(emptyList())
        shoppingCartRepository.add(product)
        val shoppingCartItems = shoppingCartRepository.getShoppingItems()

        shoppingCartItems.size shouldBe 1
        shoppingCartItems.single().product shouldBe product
    }

    @Test
    fun `장바구니에 추가된 상품은 삭제할 수 있다`() {
        val shoppingCartRepository: ShoppingCartRepository = MemoryShoppingCartRepository(listOf(product))
        val addedShoppingCartItem = shoppingCartRepository.getShoppingItems().single()

        shoppingCartRepository.remove(addedShoppingCartItem)
        shoppingCartRepository.getShoppingItems() shouldBe emptyList()
    }

    @Test
    fun `장바구니에 없는 상품을 삭제해도 에러가 발생하지 않으며 상태가 유지된다`() {
        val shoppingCartRepository: ShoppingCartRepository = MemoryShoppingCartRepository(emptyList())
        val initialItems = shoppingCartRepository.getShoppingItems()
        shoppingCartRepository.remove(ShoppingCartItem("1", product))

        shoppingCartRepository.getShoppingItems() shouldBe initialItems
    }
}
