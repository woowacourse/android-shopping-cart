package woowacourse.shopping.domain.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.data.source.ShoppingCartProductIdDataSource
import woowacourse.shopping.productsTestFixture
import woowacourse.shopping.source.FakeProductDataSource
import woowacourse.shopping.source.FakeShoppingCartProductIdDataSource
import woowacourse.shopping.testfixture.productDomainTestFixture
import woowacourse.shopping.testfixture.productDomainsTestFixture
import woowacourse.shopping.testfixture.productsIdCountDataTestFixture

class DefaultShoppingProductRepositoryTest {
    private lateinit var productDataSource: ProductDataSource
    private lateinit var shoppingCartProductIdDataSource: ShoppingCartProductIdDataSource
    private lateinit var repository: ShoppingProductsRepository

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
        // expected: id 가 0~9 이면 상품 수량이 2 개, 나머지는 0개
        val expected = productDomainsTestFixture(dataCount = 20) { id ->
            when (id) {
                in 0..9 -> productDomainTestFixture(id.toLong(), quantity = 2)
                else -> productDomainTestFixture(id.toLong(), quantity = 0)
            }
        }

        assertThat(loadedProducts).isEqualTo(expected)
    }
}
