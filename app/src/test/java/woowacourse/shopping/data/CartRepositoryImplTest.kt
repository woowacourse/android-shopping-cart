package woowacourse.shopping.data

import io.kotest.matchers.equals.shouldEqual
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.shopping.data.repository.CartRepositoryImpl

class CartRepositoryImplTest {
    private lateinit var cartRepository: CartRepositoryImpl

    @BeforeEach
    fun setUp() {
        cartRepository = CartRepositoryImpl(FakeCartDao())
    }

    @Test
    fun `장바구니가 비어있을 때 1페이지를 조회하면 빈 리스트를 반환한다`() {
        runTest {
            cartRepository.getCartItemByPage(1, 20).items.size shouldEqual 0
        }
    }

    @Test
    fun `존재하지 않는 페이지 조회 시 예외가 발생한다`() {
        runTest {
            assertThrows<IllegalArgumentException> {
                cartRepository.getCartItemByPage(0, 20)
            }
        }
    }

    @Test
    fun `마지막 페이지 여부를 올바르게 계산한다`() {
        runTest {
            repeat(5) {
                cartRepository.addItem(productId = "$it", amount = 1)
            }
            cartRepository.getCartItemByPage(1, 20).isLast shouldEqual true
        }
    }

    @Test
    fun `장바구니에 아이템을 추가할 수 있다`() =
        runTest {
            cartRepository.addItem(productId = "1", amount = 1)

            cartRepository.getCartItemByPage(1, 20).items.size shouldEqual 1
        }

    @Test
    fun `장바구니에 동일한 아이템을 추가할 경우 해당 아이템의 개수가 증가한다`() =
        runTest {
            cartRepository.addItem(productId = "1", amount = 1)
            cartRepository.addItem(productId = "1", amount = 1)

            cartRepository
                .getCartItemByPage(1, 20)
                .items
                .first { it.productId == "1" }
                .amount shouldEqual 2
        }

    @Test
    fun `장바구니에 존재하는 아이템을 삭제할 수 있다`() =
        runTest {
            cartRepository.addItem(productId = "1", amount = 1)
            cartRepository.deleteItem(productId = "1")

            cartRepository.getCartItemByPage(1, 20).items.firstOrNull { it.productId == "1" } shouldBe null
        }
}
