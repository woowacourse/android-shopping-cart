package woowacourse.shopping.data

import woowacourse.shopping.domain.Product

class DefaultShoppingRepository(
    private val productDataSource: ProductDataSource = DummyProductDataSource,
) : ShoppingRepository {
    override fun products(exceptProducts: List<Long>): List<Product> {
        return productDataSource.products(exceptProducts, PRODUCT_AMOUNT)
    }

    override fun productById(id: Long): Product? {
        return productDataSource.productById(id)
    }

    override fun canLoadMoreProducts(exceptProducts: List<Long>): Boolean {
        return productDataSource.canLoadMoreProducts(exceptProducts)
    }

    companion object {
        private const val PRODUCT_AMOUNT = 20
    }
}
