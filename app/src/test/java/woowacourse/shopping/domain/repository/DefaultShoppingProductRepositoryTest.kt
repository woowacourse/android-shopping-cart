package woowacourse.shopping.domain.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.model.toDomain
import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.data.source.ShoppingCartProductIdDataSource
import woowacourse.shopping.productTestFixture
import woowacourse.shopping.productsTestFixture
import woowacourse.shopping.source.FakeProductDataSource
import woowacourse.shopping.source.FakeShoppingCartProductIdDataSource
import woowacourse.shopping.testfixture.productsIdCountDataTestFixture

class DefaultShoppingProductRepositoryTest {
    lateinit var productDataSource: ProductDataSource
    lateinit var shoppingCartProductIdDataSource: ShoppingCartProductIdDataSource
    lateinit var repository: ShoppingProductsRepository

    @Test
    fun `첫번재 페이지 데이터 로드`() {
        // given
        productDataSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(count = 100).toMutableList(),
            )
        shoppingCartProductIdDataSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(dataCount = 10, 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepository(
                productsSource = productDataSource,
                cartSource = shoppingCartProductIdDataSource,
            )

        // when
        val loadedProducts = repository.loadAllProducts(page = 1)

        // then
        // TODO: 가독성이 너무 별로인데 개선해야 할듯?
        val expected =
            (
                productsTestFixture(count = 10).map { it.toDomain(2) } +
                    productsTestFixture(count = 10) {
                        productTestFixture(
                            (it + 10).toLong(),
                        )
                    }.map {
                        it.toDomain(
                            0,
                        )
                    }
            )
        assertThat(loadedProducts).isEqualTo(expected)
    }
}
