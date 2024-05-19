package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ShoppingCartTest {
    private lateinit var shoppingCart: ShoppingCart

    @BeforeEach
    fun setup() {
        shoppingCart = ShoppingCart(TEST_SHOPPING_CART_ITEMS)
    }

    @Test
    fun `장바구니에 상품을 담을 수 있다`() {
        // when
        val newShoppingCart = shoppingCart.addItem(NEW_ITEM)

        // then
        assertThat(newShoppingCart.items.size).isGreaterThan(shoppingCart.items.size)
        assertThat(newShoppingCart.items.size).isEqualTo(shoppingCart.items.size + 1)
    }

    @Test
    fun `장바구니에 담긴 상품들은 중복되어 담기지 않는다`() {
        // when
        val newShoppingCart = shoppingCart.addItem(EXIST_ITEM_ID_0)

        // then
        assertThat(newShoppingCart.items.size).isEqualTo(shoppingCart.items.size)
    }

    @Test
    fun `장바구니에 담긴 상품을 목록에서 제거할 수 있다`() {
        // when
        val newShoppingCart = shoppingCart.deleteItem(EXIST_ITEM_ID_0)

        // then
        assertThat(newShoppingCart.items.size).isLessThan(shoppingCart.items.size)
        assertThat(newShoppingCart.items.size).isEqualTo(shoppingCart.items.size - 1)
    }

    @Test
    fun `장바구니에 담긴 상품의 수량이 변경된 경우 목록을 업데이트한다`() {
        // given
        val updatedItem =
            (EXIST_ITEM_ID_0.increaseQuantity() as QuantityUpdate.Success).value

        // when
        val newShoppingCart = shoppingCart.updateItem(updatedItem)

        // then
        assertThat(newShoppingCart.items[0].totalQuantity).isGreaterThan(shoppingCart.items[0].totalQuantity)
    }
}
