package woowacourse.shopping.data

import woowacourse.shopping.domain.Product

class DefaultShoppingRepository(
    private val productDataSource: ProductDataSource = DummyProductDataSource,
) : ShoppingRepository {
    override fun products(exceptProducts: List<Long>, amount: Int): List<Product> {
        return productDataSource.products(exceptProducts, amount)
    }

    override fun productById(id: Long): Product? {
        return productDataSource.productById(id)
    }
}
