package woowacourse.shopping

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ShoppingCartItemTest {
    private val product = Product(1, "동원 스위트콘", 99_800, "")

    @BeforeEach
    fun setUp() {
        clearShoppingCart()
    }

    @Test
    fun `같은 상품이라도 쇼핑 카트 아이템 id가 다르면 별개로 취급한다`() {
        val shoppingCartItem = ShoppingCartItem(1, product)
        val differentIdShoppingCartItem = ShoppingCartItem(2, product)

        shoppingCartItem shouldNotBe differentIdShoppingCartItem
    }

    @Test
    fun `다른 상품이라도 쇼핑 카트 아이템 id가 같으면 동일하다고 취급한다`() {
        val shoppingCartItem = ShoppingCartItem(1, product)
        val differentProductShoppingCartItem = ShoppingCartItem(1, Product(2, "동원 참치", 9_980, "..."))
        shoppingCartItem shouldBe differentProductShoppingCartItem
    }

    @Test
    fun `장바구니에 상품을 추가할 수 있다`() {
        val shoppingCartRepository = MemoryShoppingCartRepository
        shoppingCartRepository.add(product)
        val shoppingCartItems = shoppingCartRepository.getShoppingItems().value

        shoppingCartItems.size shouldBe 1
        shoppingCartItems.single().product shouldBe product
    }

    @Test
    fun `장바구니에 추가된 상품은 삭제할 수 있다`() {
        val shoppingCartRepository = MemoryShoppingCartRepository
        shoppingCartRepository.add(product)
        val addedShoppingCartItem = shoppingCartRepository.getShoppingItems().value.single()

        shoppingCartRepository.remove(addedShoppingCartItem)
        shoppingCartRepository.getShoppingItems().value shouldBe emptyList()
    }

    @Test
    fun `장바구니에 없는 상품을 삭제하면 예외가 발생한다`() {
        shouldThrow<IllegalArgumentException> {
            val shoppingCartRepository = MemoryShoppingCartRepository
            shoppingCartRepository.remove(ShoppingCartItem(1, product))
        }
    }

    private fun clearShoppingCart() {
        val items = MemoryShoppingCartRepository.getShoppingItems().value.toList()
        items.forEach(MemoryShoppingCartRepository::remove)
    }
}
