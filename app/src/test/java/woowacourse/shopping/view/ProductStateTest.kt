package woowacourse.shopping.view

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.fixture.productFixture2
import woowacourse.shopping.view.main.state.IncreaseState
import woowacourse.shopping.view.main.state.ProductState

class ProductStateTest {
    @Test
    fun `현재 수량이 상품 수량보다 작을 경우 increase는 CanIncrease를 반환한다`() {
        val product = productFixture2 // quantity : 10
        val state = ProductState(product, Quantity(2))

        val result = state.increaseCartQuantity()

        assertTrue(result is IncreaseState.CanIncrease)
        result as IncreaseState.CanIncrease
        assertEquals(result.value.cartQuantity.value, 3)
    }
}
