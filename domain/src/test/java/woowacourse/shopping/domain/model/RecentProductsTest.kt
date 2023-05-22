package woowacourse.shopping.domain.model

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class RecentProductsTest {
    @ParameterizedTest
    @CsvSource("1, 1", "10, 5", "10, 10", "10, 11", "10, 100")
    internal fun `최근_본_목록을_최대_개수_만큼만_추가할_수_있다`(maxCount: Int, addCount: Int) {
        // given
        var recentProducts = RecentProducts(maxCount = maxCount)

        // when
        (1..addCount).forEach { id ->
            val item = RecentProduct(id, Product(id, "상품 $id", Price(1000), ""))
            recentProducts = recentProducts.add(item)
        }

        // then
        val actual = recentProducts.getItems().size
        Assertions.assertThat(actual).isEqualTo(addCount.coerceAtMost(maxCount))
    }

    @Test
    internal fun `마지막에 본 상품을 반환한다`() {
        // given
        var recentProducts = RecentProducts(maxCount = 10)

        (1..10).forEach { id ->
            val item = RecentProduct(0, Product(id, "상품 $id", Price(1000), ""))
            recentProducts = recentProducts.add(item)
        }
        val expected = RecentProduct(0, Product(10, "상품 10", Price(1000), ""))

        // when
        val actual = recentProducts.getLatest()

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
