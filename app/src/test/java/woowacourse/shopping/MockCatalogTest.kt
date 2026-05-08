package woowacourse.shopping;

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test;

class MockCatalogTest {
    @Test
    fun `페이지와 사이즈에 따라 적절한 상품 리스트를 반환한다`() = runBlocking {
        // given
        val pageSize = 5

        // when: 첫 번째 페이지 로드
        val firstPage = MockCatalog.loadMoreProducts(
            0,
            pageSize
        ).await()
        // then
        assertEquals(5, firstPage.size)
        assertEquals(
            MockCatalog.catalog[0],
            firstPage[0]
        )

        // when: 두 번째 페이지 로드
        val secondPage = MockCatalog.loadMoreProducts(1, pageSize).await()
        // then
        assertEquals(10, secondPage.size)
        assertEquals(MockCatalog.catalog[5], secondPage[5])
    }

    @Test
    fun `데이터 범위를 벗어난 페이지요청 시 마지막까지만 반환한다`() = runBlocking {
        val totalSize = MockCatalog.catalog.size
        val pageSize = 20
        val lastPage = (totalSize / pageSize) + 1

        val result =
            MockCatalog.loadMoreProducts(
                lastPage,
                pageSize
            ).await()

        assertTrue(
            result.size == totalSize
        )
    }
}
