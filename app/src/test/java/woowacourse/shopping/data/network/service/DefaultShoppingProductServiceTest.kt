package woowacourse.shopping.data.network.service

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import woowacourse.shopping.fixtures.productResponses
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DefaultShoppingProductServiceTest {
    private lateinit var testExecutors: ExecutorService

    @BeforeEach
    fun setUp() {
        testExecutors = Executors.newSingleThreadExecutor()
    }

    @Test
    @DisplayName("상품이 20개 있을 때, page 1 에 해당하는 10개의 상품을 가져올 수 있다.")
    fun `paging`() {
        //given
        val totalProducts = productResponses(20)
        val expectSize = 10
        val expect = productResponses(10)
        //when
        val actual = DefaultShoppingProductService(testExecutors, totalProducts)
            .fetchProducts(1, 10)
            .get()
            .content
        //then
        assertSoftly {
            actual shouldBe expect
            actual shouldHaveSize expectSize
        }
    }
}