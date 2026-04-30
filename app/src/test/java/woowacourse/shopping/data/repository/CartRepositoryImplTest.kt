package woowacourse.shopping.data.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.DUMMY_PRODUCTS
import woowacourse.shopping.domain.model.Quantity
import woowacourse.shopping.domain.model.cart.CartItem
import woowacourse.shopping.domain.model.cart.CartItems

class CartRepositoryImplTest {
    private val repository = CartRepositoryImpl

    @BeforeEach
    fun setUp() {
        val cartItems = CartItems(DUMMY_PRODUCTS.take(13).map { CartItem(it, Quantity(1)) })
        repository.saveCartItems(cartItems)
    }

    @Test
    fun `첫 번째 페이지 요청 시 첫 5개 항목이 반환된다`() {
        val result = repository.getCartItems(0, 5)
        assertEquals(5, result.items.size)
    }

    @Test
    fun `마지막 페이지에서는 남은 항목만 반환된다`() {
        val result = repository.getCartItems(2, 5)
        assertEquals(3, result.items.size)
    }

    @Test
    fun `페이지 범위를 벗어난 요청 시 빈 목록이 반환된다`() {
        val result = repository.getCartItems(10, 5)
        assertEquals(0, result.items.size)
    }
}
