package woowacourse.shopping.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.domain.model.RecentProducts

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
}
