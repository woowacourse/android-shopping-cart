package woowacourse.shopping.data.localdb.mapper

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.localdb.entity.RecentItemEntity
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductName

class RecentItemEntityMapperTest {
    @Test
    fun `최근 본 상품 Entitiy를 도메인 상품으로 변환한다`() {
        val entity =
            RecentItemEntity(
                id = "1",
                name = "상품",
                price = 2000,
                imageUrl = "image-url",
                timestamp = 100L,
            )

        val product = entity.toDomain()

        assertThat(product.id).isEqualTo(entity.id)
        assertThat(product.getName()).isEqualTo(entity.name)
        assertThat(product.getPrice()).isEqualTo(entity.price)
        assertThat(product.imageUrl).isEqualTo(entity.imageUrl)
    }

    @Test
    fun `도메인 상품을 최근 본 상품 Entity로 변환한다`() {
        val product =
            Product(
                id = "1",
                name = ProductName("상품"),
                price = Money(2000),
                imageUrl = "image-url",
            )

        val entity = product.toEntity(timestamp = 100L)

        assertThat(entity.id).isEqualTo(product.id)
        assertThat(entity.name).isEqualTo(product.getName())
        assertThat(entity.price).isEqualTo(product.getPrice())
        assertThat(entity.imageUrl).isEqualTo(product.imageUrl)
        assertThat(entity.timestamp).isEqualTo(100L)
    }
}
