@file:Suppress("NonAsciiCharacters")

package woowacourse.shopping.repository

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FakeRecentProductRepositoryTest {
    private val repo = FakeRecentProductRepository()

    private val product1 = CartRepositoryFixture.shrimpCracker
    private val product2 = CartRepositoryFixture.sourCandy

    @Test
    fun `최근 본 상품을 저장하면 최신순으로 조회할 수 있다`() =
        runBlocking {
            repo.recordView(product1.id)
            repo.recordView(product2.id)

            val actual = repo.getRecentProducts(limit = 10)

            assertEquals(listOf(product2.id, product1.id), actual.map { it.productId })
        }

    @Test
    fun `같은 상품을 다시 조회하면 중복 없이 가장 최근 순서로 갱신된다`() =
        runBlocking {
            repo.recordView(product1.id)
            repo.recordView(product2.id)
            repo.recordView(product1.id)

            val actual = repo.getRecentProducts(limit = 10)

            assertEquals(listOf(product1.id, product2.id), actual.map { it.productId })
        }

    @Test
    fun `현재 상품을 제외한 마지막 조회 상품을 반환한다`() =
        runBlocking {
            repo.recordView(product1.id)
            repo.recordView(product2.id)

            val actual = repo.getLatestViewedProductExcluding(product2.id)

            assertEquals(product1.id, actual?.productId)
        }
}
