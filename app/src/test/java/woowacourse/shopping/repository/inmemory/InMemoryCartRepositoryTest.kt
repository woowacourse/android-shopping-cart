@file:Suppress("NonAsciiCharacters")

package woowacourse.shopping.repository.inmemory

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product

class InMemoryCartRepositoryTest {
    val product1 =
        Product(
            name = "새우깡",
            price = Money(3100),
            imageUrl = "",
        )

    @Test
    fun `정상적인 상품 객체를 장바구니에 추가하면, 장바구니 내부 목록에 해당 상품이 올바르게 반영된다`() =
        runTest {
            val repo = InMemoryCartRepository()
            repo.increase(product1)

            assertTrue(repo.getSize() == 1)
            assertTrue(
                repo
                    .getPagedItems(fromIndex = 0, count = 1)
                    .first()
                    .product.name == "새우깡",
            )
        }

    @Test
    fun `장바구니에 존재하는 상품을 삭제 요청하면, 목록에서 해당 상품이 정상적으로 제거된다`() =
        runTest {
            val repo = InMemoryCartRepository()

            repo.increase(product1)
            repo.delete(product1)

            assertTrue(repo.getSize() == 0)
        }

    @Test
    fun `존재하지 않는 상품의 삭제를 시도할 경우, 상태가 변경되지 않거나 올바르게 무시,예외 처리된다`() =
        runTest {
            val repo = InMemoryCartRepository()

            assertThrows<IllegalArgumentException> { repo.delete(product1) }
        }
}
