package woowacourse.shopping

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.db.Product

class TwentyItemsPagingStrategyTest {
    private lateinit var pagingStrategy: PagingStrategy<Product>

    @BeforeEach
    fun setUp() {
        pagingStrategy = TwentyItemsPagingStrategy()
    }

    @Test
    fun `총 10 개의 아이템 중 첫 페이지의 아이템을 불러오면 10개를 불러온다`() {
        // given
        val items = productsTestFixture(10)

        // when
        val loadPagedData = pagingStrategy.loadPagedData(1, items)

        // then
        val expected = productsTestFixture(10)
        assertThat(loadPagedData).isEqualTo(expected)
    }

    @Test
    fun `총 30 개의 아이템 중 두 번째 페이지의 아이템을 불러오면 10개를 불러온다`() {
        // given
        val items = productsTestFixture(30)

        // when
        val firstLoad = pagingStrategy.loadPagedData(1, items)
        val secondLoad = pagingStrategy.loadPagedData(2, items)

        // then
        val expected =
            productsTestFixture(10) {
                productTestFixture(it + 20)
            }
        assertThat(secondLoad).isEqualTo(expected)
    }

    @Test
    fun `총 8 개의 아이템 중 첫 번째 페이지의 모든 데이터를 불러온다`() {
        // given
        val items = productsTestFixture(8)

        // when
        val firstLoad = pagingStrategy.loadPagedData(1, items)

        val expected = productsTestFixture(8)
        assertThat(firstLoad).isEqualTo(expected)
    }

    @Test
    fun `총 20 개의 아이템 중 두 번째 페이지의 모든 데이터를 불러오면 아이템은 0 개이다`() {
        // given
        val items = productsTestFixture(20)

        // when
        val firstLoad = pagingStrategy.loadPagedData(1, items)
        val secondLoad = pagingStrategy.loadPagedData(2, items)

        assertThat(secondLoad).isEqualTo(listOf<Product>())
    }
}
