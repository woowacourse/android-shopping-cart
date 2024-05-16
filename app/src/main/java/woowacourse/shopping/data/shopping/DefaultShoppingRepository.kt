package woowacourse.shopping.data.shopping

import woowacourse.shopping.domain.Product

class DefaultShoppingRepository(
    private val shoppingDataSource: ShoppingDataSource = DummyShoppingDataSource,
) : ShoppingRepository {
    override fun products(exceptProducts: List<Long>): List<Product> {
        return shoppingDataSource.products(exceptProducts, PRODUCT_AMOUNT)
    }

    override fun productById(id: Long): Product? {
        return shoppingDataSource.productById(id)
    }

    override fun canLoadMoreProducts(exceptProducts: List<Long>): Boolean {
        return shoppingDataSource.canLoadMoreProducts(exceptProducts)
    }

    companion object {
        private const val PRODUCT_AMOUNT = 20
    }
}
