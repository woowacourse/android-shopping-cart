package woowacourse.shopping.view

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.fixture.productFixture1
import woowacourse.shopping.fixture.productFixture2
import woowacourse.shopping.view.main.vm.state.CartSavingState
import woowacourse.shopping.view.main.vm.state.IncreaseState
import woowacourse.shopping.view.main.vm.state.ProductState

class ProductStateTest {
    @Test
    fun `장바구니에 수량이 있을 경우 SAVED 상태여야 한다`() {
        val product = productFixture1
        val state = ProductState(product, Quantity(1))

        assertEquals(state.isSaveInCart(), CartSavingState.SAVED)
    }

    @Test
    fun `장바구니 수량이 0일 경우 NOT_SAVED 상태여야 한다`() {
        val product = productFixture1
        val state = ProductState(product, Quantity(0))

        assertEquals(state.isSaveInCart(), CartSavingState.NOT_SAVED)
    }

    @Test
    fun `현재 수량이 상품 수량보다 작을 경우 increase는 CanIncrease를 반환한다`() {
        val product = productFixture2 // quantity : 10
        val state = ProductState(product, Quantity(2))

        val result = state.increase()

        assertTrue(result is IncreaseState.CanIncrease)
        result as IncreaseState.CanIncrease
        assertEquals(result.value.cartQuantity.value, 3)
    }

    @Test
    fun `특정 상품이 장바구니에 저장된 수량이 0보다 클 경우 quantityVisible은 true다`() {
        val product = productFixture2
        val state = ProductState(product, Quantity(1))

        assertTrue(state.quantityVisible)
    }

    @Test
    fun `특정 상품이 장바구니에 저장된 수량이 0일 경우 quantityVisible은 false다`() {
        val product = productFixture2
        val state = ProductState(product, Quantity(0))

        assertFalse(state.quantityVisible)
    }
}
