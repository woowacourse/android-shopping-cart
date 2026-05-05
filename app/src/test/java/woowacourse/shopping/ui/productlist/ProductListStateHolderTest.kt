package woowacourse.shopping.ui.productlist

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import woowacourse.shopping.constants.MockData
import woowacourse.shopping.ui.productlist.stateholder.ProductListStateHolder

class ProductListStateHolderTest {
    @Test
    fun `fetchProducts를 반복 호출해도 전체 개수를 초과하지 않는다`() {
        val holder = ProductListStateHolder()
        val totalSize = MockData.MOCK_PRODUCTS.size // 35개
        val pageSize = 20

        repeat(10) { holder.fetchProducts(pageSize = pageSize) }
        assertEquals(totalSize, holder.productUiModels.size)
    }

    @Test
    fun `fetchProducts를 한번 호출하면 page Size 만큼 불러온다`() {
        val holder = ProductListStateHolder()
        val pageSize = 20

        repeat(1) { holder.fetchProducts(pageSize = pageSize) }
        assertEquals(20, holder.productUiModels.size)
    }
}
