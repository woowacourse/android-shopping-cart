package woowacourse.shopping.data.localdb.mapper

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.localdb.entity.CartItemEntity
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductName

class CartItemEntityMapperTest {
    @Test
    fun `장바구니 Entity를 도메인 장바구니 상품으로 변환한다`() {
        val entity =
            CartItemEntity(
                id = "1",
                quantity = 3,
                timestamp = 100L,
            )
        val product =
            Product(
                id = "1",
                name = ProductName("상품"),
                price = Money(2000),
                imageUrl = "1",
            )

        val cartItem = entity.toDomain(product)

        assertThat(cartItem.product).isEqualTo(product)
        assertThat(cartItem.quantity).isEqualTo(entity.quantity)
    }

    @Test
    fun `장바구니 내 상품과 Product 간 id가 불일치할 시 예외가 발생한다`() {
        val entity =
            CartItemEntity(
                id = "1",
                quantity = 3,
                timestamp = 100L,
            )
        val product =
            Product(
                id = "2",
                name = ProductName("상품"),
                price = Money(2000),
                imageUrl = "2",
            )

        assertThatThrownBy { entity.toDomain(product) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("id가 일치하지 않습니다.")
    }

    @Test
    fun `도메인 장바구니 상품을 장바구니 Entity로 변환한다`() {
        val cartItem =
            CartItem(
                product =
                    Product(
                        id = "1",
                        name = ProductName("상품"),
                        price = Money(2000),
                        imageUrl = "1",
                    ),
                quantity = 3,
            )

        val entity = cartItem.toEntity(timestamp = 100L)

        assertThat(entity.id).isEqualTo(cartItem.product.id)
        assertThat(entity.quantity).isEqualTo(cartItem.quantity)
        assertThat(entity.timestamp).isEqualTo(100L)
    }
}
