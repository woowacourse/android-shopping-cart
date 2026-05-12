package woowacourse.shopping.model

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class CartTest {
    private val price = Money(10000)
    private val product =
        Product(
            id = "1",
            name = ProductName("product"),
            price = price,
            imageUrl = "ds",
        )

    @Test
    fun `상품을 등록할 수 있다`() {
        val cart = Cart().addItem(product)
        val product2 = createProduct(id = "2")

        assertThat(cart.getTotalSize()).isEqualTo(1)
        val newCart = cart.addItem(product2)
        assertThat(newCart.getTotalSize()).isEqualTo(2)
    }

    @Test
    fun `등록한 상품을 삭제할 수 있다`() {
        val cart = Cart().addItem(product)

        val newCart = cart.deleteItem(product.id)

        assertThat(newCart.getTotalSize()).isEqualTo(0)
    }

    @Test
    fun `등록한 상품의 총 가격을 계산할 수 있다`() {
        val cart = Cart().addItem(product)

        assertThat(cart.calculateTotalPrice()).isEqualTo(10000)
    }

    @Test
    fun `이미 등록된 상품을 추가하면 예외가 발생한다`() {
        val cart = Cart().addItem(product)

        assertThatThrownBy {
            cart.addItem(product)
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    private fun createProduct(id: String): Product =
        Product(
            id = id,
            name = ProductName("product$id"),
            price = Money(10000),
            imageUrl = "ds",
        )
}
