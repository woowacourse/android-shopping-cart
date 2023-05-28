import model.RecentViewedProduct
import model.RecentViewedProducts
import org.junit.Assert.assertEquals
import org.junit.Test

class RecentViewedProductsTest {

    @Test
    fun `최대 개수보다 많은 상품이 저장될 경우 상품이 삭제된다`() {
        // given
        val recentViewedProducts = RecentViewedProducts(
            products = listOf(RecentViewedProduct(name = "아메리카노")),
            maxSize = 1
        )

        // when
        recentViewedProducts.add(RecentViewedProduct(name = "카페라떼")) {}

        // then
        val expected: List<RecentViewedProduct> = listOf(RecentViewedProduct(name = "카페라떼"))
        assertEquals(expected, recentViewedProducts.values)
    }

    @Test
    fun `최대 개수보다 상품이 적은 경우 상품을 추가한다`() {
        // given
        val recentViewedProducts = RecentViewedProducts(
            products = listOf(
                RecentViewedProduct(name = "아메리카노")
            ),
            maxSize = 5
        )

        // when
        recentViewedProducts.add(
            RecentViewedProduct(name = "카페라떼")
        ) {}

        // then
        val expected = listOf(
            RecentViewedProduct(name = "카페라떼"),
            RecentViewedProduct(name = "아메리카노")
        )
        assertEquals(expected, recentViewedProducts.values)
    }
}
