package woowacourse.shopping.feature.cart.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.core.model.Money
import woowacourse.shopping.core.model.Product
import woowacourse.shopping.core.model.ProductName

class CartTest {
    private val price = Money(10000)
    private val product =
        Product(
            name = ProductName("상품"),
            price = price,
            imageUrl = "ds",
        )
    private lateinit var cart: Cart

    @BeforeEach
    fun setUp() {
        cart = Cart().addItem(product)
    }

    @Test
    fun `상품을 등록할 수 있다`() {
        val product2 =
            Product(
                name = ProductName("상품2"),
                price = Money(10000),
                imageUrl = "ds",
            )
        assertThat(cart.items.size).isEqualTo(1)
        val newCart = cart.addItem(product2)
        assertThat(newCart.items.size).isEqualTo(2)
    }

    @Test
    fun `등록한 상품을 삭제할 수 있다`() {
        assertThat(cart.items.size).isEqualTo(1)
        val newCart = cart.deleteItem(product)
        assertThat(newCart.items.size).isEqualTo(0)
    }

    @Test
    fun `등록된 상품의 총 가격을 계산할 수 있다`() {
        assertThat(cart.calculateTotalPrice()).isEqualTo(price)
    }
}
