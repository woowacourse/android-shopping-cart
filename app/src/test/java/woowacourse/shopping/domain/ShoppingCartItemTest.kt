package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ShoppingCartItemTest {
    private lateinit var shoppingCartItem: ShoppingCartItem

    @BeforeEach
    fun setup() {
        shoppingCartItem = ShoppingCartItem(TEST_PRODUCT)
    }

    @Test
    fun `장바구니에 담을 상품의 기본 개수는 1개이다`() {
        assertThat(shoppingCartItem.totalQuantity).isEqualTo(1)
    }

    @Test
    fun `장바구니 아이템의 개수를 1 증가시킬 수 있다`() {
        // given
        val previousQuantity = shoppingCartItem.totalQuantity

        // when
        val presentQuantity =
            (shoppingCartItem.increaseQuantity() as QuantityUpdate.Success).value.totalQuantity

        // then
        assertThat(presentQuantity).isGreaterThan(previousQuantity)
        assertThat(presentQuantity).isEqualTo(previousQuantity + 1)
    }

    @Test
    fun `장바구니 아이템의 개수를 1 감소시킬 수 있다`() {
        // given
        val initialItem =
            (shoppingCartItem.increaseQuantity() as QuantityUpdate.Success).value

        // when
        val presentQuantity =
            (initialItem.decreaseQuantity() as QuantityUpdate.Success).value.totalQuantity

        // then
        assertThat(presentQuantity).isLessThan(initialItem.totalQuantity)
        assertThat(presentQuantity).isEqualTo(initialItem.totalQuantity - 1)
    }

    @Test
    fun `장바구니 아이템 개수는 1보다 작게 감소시킬 수 없으며, 실패 상태를 반환한다`() {
        // when
        val result = shoppingCartItem.decreaseQuantity()

        // then
        assertThat(result).isInstanceOf(QuantityUpdate.Failure::class.java)
    }

    @Test
    fun `장바구니 아이템의 개수에 따른 합계 가격을 알 수 있다`() {
        val increasedCountItem =
            (shoppingCartItem.increaseQuantity() as QuantityUpdate.Success).value

        assertThat(increasedCountItem.totalPrice.value).isEqualTo(20000)
    }
}
