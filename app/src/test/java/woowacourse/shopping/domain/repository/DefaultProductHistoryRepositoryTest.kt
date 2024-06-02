package woowacourse.shopping.domain.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.model.toDomain
import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.data.source.ProductHistoryDataSource
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.productTestFixture
import woowacourse.shopping.productsTestFixture
import woowacourse.shopping.source.FakeProductDataSource
import woowacourse.shopping.source.FakeProductHistorySource
import woowacourse.shopping.testfixture.productDomainTestFixture
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
    fun `상품 검색`() {
        // given setup
        // when
        val product = productHistoryRepository.loadProductHistory(1)

        // then
        assertThat(product).isEqualTo(
            productDomainTestFixture(1)
        )
    }

    @Test
    fun `상품 검색 async`() {
        // given setup
        var result: Product? = null
        val latch = CountDownLatch(1)

        // when
        productHistoryRepository.loadProductHistoryAsync(1) { product ->
            result = product
            latch.countDown()
        }
        latch.await()

        // then
        assertThat(result).isEqualTo(
            productTestFixture(1).toDomain(quantity = 0),
        )
    }

    @Test
    fun `가장 최근 상품 검색 `() {
        // given setup
        // when
        val product = productHistoryRepository.loadLatestProduct()

        // then
        assertThat(product).isEqualTo(
            productDomainTestFixture(2),
        )
    }

    @Test
    fun `가장 최근 상품 검색 async`() {
        // given setup
        val latch = CountDownLatch(1)
        var result: Long = -99

        // when
        productHistoryRepository.loadLatestProductAsync { productId ->
            result = productId
            latch.countDown()
        }
        latch.await()

        // then
        assertThat(result).isEqualTo(2)
    }

    @Test
    fun `이미 내역에 있는 상품을 저장하려고 하면 저장 해당 상품이 가장 뒷 순서로 이동`() {
        // given setup
        val product = productTestFixture(2)

        // when
        productHistoryRepository.saveProductHistory(product.id)

        // then
        assertThat(productHistoryRepository.loadAllProductHistory()).hasSize(3)
    }

    @Test
    fun `이미 내역에 있는 상품을 저장하려고 하면 저장 해당 상품이 가장 최근 순서로 이동 async`() {
        // given setup
        val product = productTestFixture(2)
        var resultLatestProductId: Long = -1

        val latch = CountDownLatch(2)

        // when
        productHistoryRepository.saveProductHistoryAsync(product.id) {
            latch.countDown()
        }

        productHistoryRepository.loadLatestProductAsync { latestProductId ->
            resultLatestProductId = latestProductId
            latch.countDown()
        }
        latch.await()

        // then
        assertThat(resultLatestProductId).isEqualTo(product.id)
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
    fun `내역에 없는 상품을 저장`() {
        // given setup
        val product = productTestFixture(5)

        // when
        productHistoryRepository.saveProductHistory(product.id)

        // then
        assertThat(productHistoryRepository.loadAllProductHistory()).hasSize(4)
    }

    @Test
    fun `내역에 없는 상품을 저장하면 실제로 저장된다 async`() {
        // given setup
        val product = productTestFixture(3)
        val latch = CountDownLatch(2)
        var resultProducts: List<Product> = emptyList()

        // when
        productHistoryRepository.saveProductHistoryAsync(product.id) {
            latch.countDown()
        }

        // then
        productHistoryRepository.loadAllProductHistoryAsync { products ->
            resultProducts = products
            latch.countDown()
        }
        latch.await()

        assertThat(resultProducts).isEqualTo(
            productDomainsTestFixture(4)
        )
    }

    @Test
    fun `내역에 없는 상품을 저장하면 실제로 저장된다 async result`() {
        // given
        val product = productTestFixture(3)
        val latch = CountDownLatch(2)
        var resultProducts: List<Product> = emptyList()

        // when
        productHistoryRepository.saveProductHistoryAsyncResult(product.id) { result ->
            latch.countDown()
        }

        productHistoryRepository.loadAllProductHistoryAsyncResult { result ->
            result.onSuccess {
                resultProducts = it
                latch.countDown()
            }
        }

        println("before await")
        latch.await()

        assertThat(resultProducts).isEqualTo(
            productDomainsTestFixture(4)
        )
    }


    @Test
    fun `상품 모두 로드`() {
        // given setup

        // when
        val products = productHistoryRepository.loadAllProductHistory()

        // then
        assertThat(products).isEqualTo(
            productDomainsTestFixture(3)
        )
    }

    @Test
    fun `상품 모두 로드 async`() {
        // given setup
        val latch = CountDownLatch(1)
        var resultProducts: List<Product> = emptyList()

        // when
        productHistoryRepository.loadAllProductHistoryAsync { products ->
            resultProducts = products
            latch.countDown()
        }
        latch.await()

        assertThat(resultProducts).isEqualTo(
            productDomainsTestFixture(3)
        )

    }

    // 이거는 실행안 되도록 애노테이션 아래 붙여줘
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
