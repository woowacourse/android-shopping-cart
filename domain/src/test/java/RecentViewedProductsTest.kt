import model.RecentViewedProducts
import org.junit.Assert.assertEquals
import org.junit.Test

class RecentViewedProductsTest {

    @Test
    fun `최대 개수보다 많은 상품이 저장될 경우 상품이 삭제되고 삭제된 상품을 반환한다`() {
        // given
        val recentViewedProducts = RecentViewedProducts(
            products = listOf(Product(name = "아메리카노")),
            maxSize = 1
        )

        // when
        val removedProducts = recentViewedProducts.add(Product(name = "카페라떼"))

        // then
        val expected = Product(name = "아메리카노")
        assertEquals(expected, removedProducts)
    }

    @Test
    fun `최대 개수보다 상품이 적은 경우 상품을 추가하고 null을 반환한다`() {
        // given
        val recentViewedProducts = RecentViewedProducts(
            products = listOf(
                Product(name = "아메리카노")
            ),
            maxSize = 5
        )

        // when
        val removedProducts = recentViewedProducts.add(
            Product(name = "카페라떼")
        )

        // then
        val expectedValues = listOf(
            Product(name = "아메리카노"),
            Product(name = "카페라떼")
        )
        val expected = null
        assertEquals(expected, removedProducts)
        assertEquals(expectedValues, recentViewedProducts.values)
    }
}
