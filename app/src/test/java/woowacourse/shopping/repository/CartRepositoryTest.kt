@file:Suppress("NonAsciiCharacters")

package woowacourse.shopping.repository

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.shopping.model.Product

class CartRepositoryTest {
    private lateinit var repo: CartRepository

    private val product1: Product = CartRepositoryFixture.shrimpCracker
    private val product2: Product = CartRepositoryFixture.sourCandy

    @BeforeEach
    fun setUp() {
        repo = FakeCartRepository()
    }

    @Test
    fun `정상적인 상품 객체를 장바구니에 추가하면, 장바구니 내부 목록에 해당 상품이 올바르게 반영된다`() =
        runBlocking {
            repo.add(product1.id)

            val actual = repo.getCartItems(0, repo.count())

            assertTrue(actual.containsKey(product1.id))
        }

    @Test
    fun `장바구니에 존재하는 상품을 삭제 요청하면, 목록에서 해당 상품이 정상적으로 제거된다`() =
        runBlocking {
            repo.add(product1.id)
            repo.delete(product1.id)

            val actual = repo.getCartItems(0, repo.count())

            assertFalse(actual.containsKey(product1.id))
        }

    @Test
    fun `존재하지 않는 상품의 삭제를 시도할 경우, 상태가 변경되지 않거나 올바르게 무시,예외 처리된다`(): Unit =
        runBlocking {
            assertThrows<IllegalArgumentException> { runBlocking { repo.delete(product1.id) } }
        }
}
