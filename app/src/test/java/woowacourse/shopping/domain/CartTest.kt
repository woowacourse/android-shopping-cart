package woowacourse.shopping.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CartTest {
    private val productA = Product(
        name = "상품A",
        price = Money(2000),
        imageUrl = "",
        id = "1",
    )

    private val productB = Product(
        name = "상품B",
        price = Money(1000),
        imageUrl = "",
        id = "2",
    )

    private val quantity1 = Quantity(1)
    private val quantity2 = Quantity(2)

    private val testCart = Cart(
        cartItems = listOf(
            CartItem(
                product = productA,
                quantity = quantity1,
            ),
        ),
    )

    @Test
    fun `Cart에 상품을 존재한다면 true를 반환한다`() {
        val result = testCart.contains(productA)

        assertEquals(true, result)
    }

    @Test
    fun `Cart에 상품을 존재하지 않는다면 false를 반환한다`() {
        val result = testCart.contains(productB)

        assertEquals(false, result)
    }

    @Test
    fun `Cart에 입력받은 상품의 Quantity를 반환한다`() {
        val result = testCart.getQuantity(productA)

        assertEquals(Quantity(1), result)
    }

    @Test
    fun `Cart에 입력받은 상품이 없다면 null을 반환한다`() {
        val result = testCart.getQuantity(productB)

        assertEquals(null, result)
    }

    @Test
    fun `새로운 상품을 추가하면 Cart에 새로운 상품이 추가된 Cart를 반환한다`() {
        val cart = Cart()
        val newCart = cart.plusProduct(productA, quantity1)

        val isExistResult = newCart.contains(productA)
        val quantityResult = newCart.getQuantity(productA)

        assertEquals(true, isExistResult)
        assertEquals(Quantity(1), quantityResult)
    }

    @Test
    fun `이미 존재하는 상품을 추가하면 수량이 합쳐진 Cart 를 반환한다`() {
        val cart = Cart().plusProduct(productA, quantity1)
        val newCart = cart.plusProduct(productA, quantity2)

        val isExistResult = newCart.contains(productA)
        val quantityResult = newCart.getQuantity(productA)

        assertEquals(1, cart.cartItems.size)
        assertEquals(1, newCart.cartItems.size)
        assertEquals(true, isExistResult)
        assertEquals(Quantity(3), quantityResult)
    }

    @Test
    fun `삭제 수량이 보유 수량과 같으면 해당 상품이 제거된 Cart 를 반환한다`() {
        val cart = Cart().plusProduct(productA, quantity2)
        val newCart = cart.minusProduct(productA, quantity2)

        val isExistResult = newCart.contains(productA)

        assertEquals(0, newCart.cartItems.size)
        assertEquals(false, isExistResult)
    }

    @Test
    fun `삭제 수량이 보유 수량보다 적으면 수량이 줄어든 Cart 를 반환한다`() {
        val cart = Cart().plusProduct(productA, quantity2)
        val newCart = cart.minusProduct(productA, quantity1)

        val isExistResult = newCart.contains(productA)
        val quantityResult = newCart.getQuantity(productA)

        assertEquals(1, newCart.cartItems.size)
        assertEquals(true, isExistResult)
        assertEquals(Quantity(1), quantityResult)
    }

    @Test
    fun `삭제 수량이 보유 수량보다 많으면 예외를 발생시킨다`() {
        val cart = Cart().plusProduct(productA, quantity1)

        assertThrows<IllegalArgumentException> { cart.minusProduct(productA, quantity2) }
    }

    @Test
    fun `Cart에 없는 상품을 삭제하려고하면 예외를 발생시킨다`() {
        val cart = Cart().plusProduct(productA, quantity1)

        assertThrows<IllegalArgumentException> { cart.minusProduct(productB, quantity1) }
    }
}
