package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import woowacourse.shopping.data.ProductFixture
import woowacourse.shopping.domain.model.product.RecentlyViewedProducts

class RecentlyViewedProductsTest {
    @Test
    fun `최근 본 상품을 추가하면 가장 앞에 추가된다`() {
        val product1 = ProductFixture.productList[0]
        val product2 = ProductFixture.productList[1]
        val recentlyViewedProducts = RecentlyViewedProducts(listOf(product1))

        val result = recentlyViewedProducts.add(product2)

        assertThat(result.productItems).containsExactly(product2, product1)
    }

    @Test
    fun `최근 본 상품은 최대 10개까지만 유지된다`() {
        val products = ProductFixture.productList.take(10)
        val newProduct = ProductFixture.productList[10]
        val recentlyViewedProducts = RecentlyViewedProducts(products)

        val result = recentlyViewedProducts.add(newProduct)

        assertThat(result.productItems).hasSize(10)
        assertThat(result.productItems.first()).isEqualTo(newProduct)
        assertThat(result.productItems).doesNotContain(products.last())
    }

    @Test
    fun `같은 상품을 여러 번 추가해도 중복되지 않는다`() {
        val product = ProductFixture.productList.first()
        val recentlyViewedProducts = RecentlyViewedProducts()

        val result =
            recentlyViewedProducts
                .add(product)
                .add(product)
                .add(product)

        assertThat(result.productItems).containsExactly(product)
    }
}
