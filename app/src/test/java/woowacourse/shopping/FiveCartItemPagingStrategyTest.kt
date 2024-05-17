package woowacourse.shopping

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.db.Product

class FiveCartItemPagingStrategyTest {
    private lateinit var itemPagingStrategy: PagingStrategy<Product>

    @BeforeEach
    fun setUp() {
        itemPagingStrategy = FiveCartItemPagingStrategy()
    }

    @Test
    fun `총 10 개의 아이템 중 처음 다섯 개의 아이템을 불러온다`() {
        // given
        val items = productsTestFixture(10)

        // when
        val loadPagedData = itemPagingStrategy.loadPagedData(0, items)

        // then
        val expected = productsTestFixture(5)
        assertThat(loadPagedData).isEqualTo(expected)
    }

    @Test
    fun `총 10 개의 아이템 중 두 번째 페이지의 다섯 개의 아이템을 불러온다`() {
        // given
        val items = productsTestFixture(10)

        // when
        val firstLoad = itemPagingStrategy.loadPagedData(0, items)
        val secondLoad = itemPagingStrategy.loadPagedData(1, items)

        // then
        val expected =
            productsTestFixture(5) {
                productTestFixture(it + 5)
            }
        assertThat(secondLoad).isEqualTo(expected)
    }

    @Test
    fun `총 8 개의  아이템 중 두번째 페이지의 모든 데이터를 불러온다`() {
        // given
        val items = productsTestFixture(8)

        // when
        val firstLoad = itemPagingStrategy.loadPagedData(0, items)
        val secondLoad = itemPagingStrategy.loadPagedData(1, items)

        val expected =
            productsTestFixture(3) {
                productTestFixture(it + 5)
            }

        assertThat(secondLoad).isEqualTo(expected)
    }
}
