package woowacourse.shopping.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

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
        cart = (Cart().addItem(product) as AddItemResult.NewAdded).cart
    }

    @Test
    fun `새로운 상품을 추가하면 NewAdded를 반환한다`() {
        val newProduct =
            Product(
                name = ProductName("새로운 상품904"),
                price = Money(10000),
                imageUrl = "ds",
            )
        val result = cart.addItem(newProduct)
        assertThat(result).isInstanceOf(AddItemResult.NewAdded::class.java)
    }

    @Test
    fun `이미 등록된 상품을 장바구니에 추가하면 DuplicateItem을 반환한다`() {
        val result = cart.addItem(product)
        assertThat(result).isInstanceOf(AddItemResult.DuplicateItem::class.java)
    }

    @Test
    fun `등록한 상품을 삭제하면 Success를 반환한다`() {
        val result = cart.deleteItem(product.id)
        assertThat(result).isInstanceOf(RemoveItemResult.Success::class.java)
    }

    @Test
    fun `등록되지 않은 상품을 삭제하면 NotFoundItem을 반환한다`() {
        val result = cart.deleteItem("등록되지 않은 아이템")
        assertThat(result).isInstanceOf(RemoveItemResult.NotFoundItem::class.java)
    }

    @Test
    fun `등록된 상품의 총 가격을 계산할 수 있다`() {
        assertThat(cart.calculateTotalPrice()).isEqualTo(price)
    }

    @Test
    fun `이미 등록된 상품을 추가하면 DuplicateItem을 발생한다`() {
        val result = cart.addItem(product)
        assertThat(result).isEqualTo(AddItemResult.DuplicateItem)
    }
}
