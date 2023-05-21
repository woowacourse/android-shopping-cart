package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Product

internal class ProductsTest {
    @ParameterizedTest
    @ValueSource(ints = [1, 10, 100])
    internal fun `마지막 아이디를_반환한다`(count: Int) {
        // given
        val items = List(count) { id -> Product(id + 1, "상품 $id", Price(1000), "") }
        val products = Products(items)

        // when
        val actual = products.lastId

        // then
        assertThat(actual).isEqualTo(count)
    }

    @ParameterizedTest
    @CsvSource("2, 1", "15, 10", "101, 100")
    internal fun `아이템_개수가_로딩_단위_보다_많으면_데이터를_더_불러올_수_있는_상태이다`(itemCount: Int, loadUnit: Int) {
        // given
        val items = List(itemCount) { id -> Product(id + 1, "상품 $id", Price(1000), "") }
        val products = Products(items, loadUnit)

        // when
        val actual = products.canLoadMore()

        // then
        assertThat(actual).isTrue
    }

    @ParameterizedTest
    @CsvSource("5, 2, 4", "15, 10, 10", "101, 20, 100")
    internal fun `아이템을_로딩_단위별로_가져온다`(itemCount: Int, loadUnit: Int, expected: Int) {
        // given
        val items = List(itemCount) { id -> Product(id + 1, "상품 $id", Price(1000), "") }
        val products = Products(items, loadUnit)

        // when
        val actual = products.getItemsByUnit().size

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
