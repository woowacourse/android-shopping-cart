package woowacourse.shopping.data.localdb.mapper

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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
                timestamp = 100L,
            )
        val product =
            Product(
                id = "1",
                name = ProductName("상품"),
                price = Money(2000),
                imageUrl = "1",
            )

        val result = entity.toDomain(product)

        assertThat(result).isEqualTo(product)
    }

    @Test
    fun `최근 본 상품과 Product 간 id가 불일치할 시 예외가 발생한다`() {
        val entity =
            RecentItemEntity(
                id = "1",
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
    fun `도메인 상품을 최근 본 상품 Entity로 변환한다`() {
        val product =
            Product(
                id = "1",
                name = ProductName("상품"),
                price = Money(2000),
                imageUrl = "1",
            )

        val entity = product.toEntity(timestamp = 100L)

        assertThat(entity.id).isEqualTo(product.id)
        assertThat(entity.timestamp).isEqualTo(100L)
    }
}
