package woowacourse.shopping.model

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class CartTest {
    private val price = Money(10000)
    private val product =
        Product(
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
        assertThat(cart.getTotalSize()).isEqualTo(1)
        val newCart = cart.deleteItem(product.id)
        assertThat(newCart.getTotalSize()).isEqualTo(0)
    }

    @Test
    fun `등록된 상품의 총 가격을 계산할 수 있다`() {
        val cart = Cart().addItem(product)
        assertThat(cart.calculateTotalPrice()).isEqualTo(price)
    }

    @Test
    fun `이미 등록된 상품을 추가하면 예외가 발생한다`() {
        val cart = Cart().addItem(product)
        assertThatThrownBy {
            cart.addItem(product)
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `페이지 사이즈에 맞춰 아이템을 반환한다`() {
        val cart = createCart(size = 6)

        val result = cart.getPage(page = 0, pageSize = 5)

        assertThat(result.items.map { it.product.id })
            .containsExactly("1", "2", "3", "4", "5")
        assertThat(result.page).isEqualTo(0)
        assertThat(result.isCanMoveNext).isTrue()
    }

    @Test
    fun `남은 아이템이 한 페이지의 적정 아이템 개수보다 적을 경우, 남은 아이템만 반환한다`() {
        val cart = createCart(size = 14)

        val result = cart.getPage(page = 2, pageSize = 5)

        assertThat(result.items.map { it.product.id })
            .containsExactly("11", "12", "13", "14")
        assertThat(result.page).isEqualTo(2)
        assertThat(result.isCanMoveNext).isFalse()
    }

    @Test
    fun `마지막 페이지에서는 다음 페이지 이동이 불가능하다`() {
        val cart = createCart(size = 10)

        val result = cart.getPage(page = 2, pageSize = 5)

        assertThat(result.isCanMoveNext).isFalse()
    }

    private fun createCart(size: Int): Cart =
        (1..size).fold(Cart()) { cart, id ->
            cart.addItem(createProduct(id = id.toString()))
        }

    private fun createProduct(id: String): Product =
        Product(
            id = id,
            name = ProductName("product$id"),
            price = Money(10000),
            imageUrl = "ds",
        )
}
