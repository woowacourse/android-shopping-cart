package woowacourse.shopping.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.source.ShoppingCartProductIdDataSource
import woowacourse.shopping.domain.model.ProductIdsCount
import woowacourse.shopping.domain.repository.DefaultProductIdsCountRepository
import woowacourse.shopping.domain.repository.ProductIdsCountRepository
import woowacourse.shopping.source.FakeShoppingCartProductIdDataSource
import woowacourse.shopping.testfixture.productsIdCountDataTestFixture
import woowacourse.shopping.testfixture.productsIdCountTestFixture

class DefaultProductIdsCountRepositoryTest {
    private lateinit var repository: ProductIdsCountRepository
    private lateinit var source: ShoppingCartProductIdDataSource

    @Test
    fun `상품을 찾는다`() {
        // given
        val productId = 1L
        source = FakeShoppingCartProductIdDataSource(productsIdCountDataTestFixture(3).toMutableList())
        repository = DefaultProductIdsCountRepository(source)

        // when
        val productIdsCount = repository.findByProductId(productId)

        // then
        println(productsIdCountDataTestFixture(3).toMutableList())

        println(productsIdCountTestFixture(1))

        assertThat(productIdsCount).isEqualTo(ProductIdsCount(1, 1))
    }

    @Test
    fun `상품을 찾는다 async`() {
        // given
        val productId = 1L
        source = FakeShoppingCartProductIdDataSource(productsIdCountDataTestFixture(3).toMutableList())
        repository = DefaultProductIdsCountRepository(source)

        // when
        repository.findByProductIdAsync(productId) {
            // then
            assertThat(it).isEqualTo(ProductIdsCount(1, 1))
        }
    }
}
