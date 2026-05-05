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
        val result = repository.getPagingCartItems(0, 5)
        assertEquals(5, result.items.size)
    }

    @Test
    fun `마지막 페이지에서는 남은 항목만 반환된다`() {
        val result = repository.getPagingCartItems(2, 5)
        assertEquals(3, result.items.size)
    }

    @Test
    fun `페이지 범위를 벗어난 요청 시 예외가 발생한다`() {
        org.junit.jupiter.api.assertThrows<IllegalArgumentException> {
            repository.getPagingCartItems(10, 5)
        }
    }

    @Test
    fun `잘못된 페이지 번호 요청 시 예외가 발생한다`() {
        org.junit.jupiter.api.assertThrows<IllegalArgumentException> {
            repository.getPagingCartItems(-1, 5)
        }
    }

    @Test
    fun `잘못된 페이지 사이즈 요청 시 예외가 발생한다`() {
        org.junit.jupiter.api.assertThrows<IllegalArgumentException> {
            repository.getPagingCartItems(0, 0)
        }
    }

    @Test
    fun `상품 ID로 장바구니 항목을 조회할 수 있다`() {
        val firstProduct = DUMMY_PRODUCTS[0]
        val result = repository.getCartItem(firstProduct.id)
        assertEquals(firstProduct.id, result?.product?.id)
    }

    @Test
    fun `장바구니에 새로운 항목을 추가할 수 있다`() {
        val initialCount = repository.getCartItemCount()
        val newProduct = DUMMY_PRODUCTS[15]
        repository.addCartItem(CartItem(newProduct, Quantity(1)))
        
        assertEquals(initialCount + 1, repository.getCartItemCount())
        assertEquals(newProduct.id, repository.getCartItem(newProduct.id)?.product?.id)
    }

    @Test
    fun `장바구니에서 항목을 삭제할 수 있다`() {
        val firstProduct = DUMMY_PRODUCTS[0]
        val initialCount = repository.getCartItemCount()
        
        repository.deleteCartItem(firstProduct.id)
        
        assertEquals(initialCount - 1, repository.getCartItemCount())
        assertEquals(null, repository.getCartItem(firstProduct.id))
    }
}
