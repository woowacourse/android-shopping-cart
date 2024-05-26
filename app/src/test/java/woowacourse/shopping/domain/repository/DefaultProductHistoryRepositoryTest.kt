package woowacourse.shopping.domain.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.model.toDomain
import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.data.source.ProductHistoryDataSource
import woowacourse.shopping.productTestFixture
import woowacourse.shopping.productsTestFixture
import woowacourse.shopping.source.FakeProductDataSource
import woowacourse.shopping.source.FakeProductHistorySource

class DefaultProductHistoryRepositoryTest {
    private lateinit var historySource: ProductHistoryDataSource
    private lateinit var productSource: ProductDataSource
    private lateinit var productHistoryRepository: ProductHistoryRepository

    @BeforeEach
    fun setUp() {
        historySource =
            FakeProductHistorySource(
                history = mutableListOf(1, 2, 3),
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
            productTestFixture(1).toDomain(quantity = 0),
        )
    }

    @Test
    fun `이미 내역에 있는 상품을 저장하려고 하면 저장 안됨`() {
        // given setup
        val product = productTestFixture(3)

        // when
        productHistoryRepository.saveProductHistory(product.id)

        // then
        assertThat(productHistoryRepository.loadAllProductHistory()).hasSize(3)
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
    fun `상품 모두 로드`() {
        // given setup

        // when
        val products = productHistoryRepository.loadAllProductHistory()

        // then
        assertThat(products).hasSize(3)
    }
}
