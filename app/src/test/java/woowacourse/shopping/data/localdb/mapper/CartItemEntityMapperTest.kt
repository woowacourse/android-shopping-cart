package woowacourse.shopping.data.localdb.mapper

import org.assertj.core.api.Assertions.assertThat
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
                name = "상품",
                price = 2000,
                imageUrl = "image-url",
                quantity = 3,
                timestamp = 100L,
            )

        val cartItem = entity.toDomain()

        assertThat(cartItem.product.id).isEqualTo(entity.id)
        assertThat(cartItem.product.getName()).isEqualTo(entity.name)
        assertThat(cartItem.product.getPrice()).isEqualTo(entity.price)
        assertThat(cartItem.product.imageUrl).isEqualTo(entity.imageUrl)
        assertThat(cartItem.quantity).isEqualTo(entity.quantity)
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
                        imageUrl = "image-url",
                    ),
                quantity = 3,
            )

        val entity = cartItem.toEntity(timestamp = 100L)

        assertThat(entity.id).isEqualTo(cartItem.product.id)
        assertThat(entity.name).isEqualTo(cartItem.product.getName())
        assertThat(entity.price).isEqualTo(cartItem.product.getPrice())
        assertThat(entity.imageUrl).isEqualTo(cartItem.product.imageUrl)
        assertThat(entity.quantity).isEqualTo(cartItem.quantity)
        assertThat(entity.timestamp).isEqualTo(100L)
    }
}
