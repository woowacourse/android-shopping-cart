package woowacourse.shopping;

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MockCatalogTest {
    @Test
    fun `페이지와 사이즈에 따라 적절한 상품 리스트를 반환한다`() = runBlocking {
        // given
        val pageSize = 5

        // when
        val firstPage = MockCatalog.loadProducts(
            0,
            pageSize
        ).await()
        // then
        assertEquals(5, firstPage.size)
        assertEquals(
            MockCatalog.catalog[0],
            firstPage[0]
        )
    }

    @Test
    fun `데이터 범위를 벗어난 페이지요청 시 마지막까지만 반환한다`() = runBlocking {
        val totalSize = MockCatalog.catalog.size
        val pageSize = 20
        val lastPage = (totalSize / pageSize)

        val result =
            MockCatalog.loadProducts(
                lastPage,
                pageSize
            ).await()

        assertEquals(
            totalSize % pageSize, result.size
        )
    }
}
