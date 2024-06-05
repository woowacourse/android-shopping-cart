package woowacourse.shopping.domain.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.data.source.ProductHistoryDataSource
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.productTestFixture
import woowacourse.shopping.productsTestFixture
import woowacourse.shopping.source.FakeProductDataSource
import woowacourse.shopping.source.FakeProductHistorySource
import woowacourse.shopping.testfixture.productDomainsTestFixture
import java.util.concurrent.CountDownLatch

class DefaultProductHistoryRepositoryTest {
    private lateinit var historySource: ProductHistoryDataSource
    private lateinit var productSource: ProductDataSource
    private lateinit var productHistoryRepository: ProductHistoryRepository

    @BeforeEach
    fun setUp() {
        historySource =
            FakeProductHistorySource(
                history = mutableListOf(0, 1, 2),
            )
        productSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(60).toMutableList(),
            )
        productHistoryRepository = DefaultProductHistoryRepository(historySource, productSource)
    }

    @Test
    fun `이미 내역에 있는 상품을 저장하려고 하면 저장 해당 상품이 가장 최근 순서로 이동 async result`() {
        // given
        val product = productTestFixture(3)
        val latch = CountDownLatch(2)
        var actualLatestId: Long = -1

        // when 상품 내역 저장
        productHistoryRepository.saveProductHistoryAsyncResult(product.id) {
            latch.countDown()
        }

        // 가장 최근 상품 불러오기
        productHistoryRepository.loadLatestProductIdAsyncResult { result ->
            result.onSuccess {
                actualLatestId = it
                latch.countDown()
            }
        }

        latch.await()
        // then
        assertThat(actualLatestId).isEqualTo(product.id)
    }

    @Test
    fun `내역에 없는 상품을 저장하면 실제로 저장된다 async result`() {
        // given
        val product = productTestFixture(3)
        val latch = CountDownLatch(2)
        var resultProducts: List<Product> = emptyList()

        // when
        productHistoryRepository.saveProductHistoryAsyncResult(product.id) { _ ->
            latch.countDown()
        }

        productHistoryRepository.loadAllProductHistoryAsyncResult { result ->
            result.onSuccess {
                resultProducts = it
                latch.countDown()
            }
        }

        latch.await()

        assertThat(resultProducts).isEqualTo(
            productDomainsTestFixture(4),
        )
    }

    @Test
    fun `상품을 모두 로드 async result`() {
        // given setup
        val latch = CountDownLatch(1)
        var resultProducts: List<Product> = emptyList()

        // when
        productHistoryRepository.loadAllProductHistoryAsyncResult { result ->
            result.onSuccess {
                resultProducts = it
                latch.countDown()
            }
        }
        latch.await()
        assertThat(resultProducts).isEqualTo(productDomainsTestFixture(3))
    }
}
