package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.ProductData
import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.data.source.ShoppingCartProductIdDataSource

class DefaultShoppingProductRepository(
    private val productsSource: ProductDataSource,
    private val cartSource: ShoppingCartProductIdDataSource,
) : ShoppingProductsRepository {
    override fun loadAllProducts(page: Int): List<ProductData> {
        TODO("Not yet implemented")
    }

    override fun loadProduct(findId: Int): ProductData {
        TODO("Not yet implemented")
    }

    override fun isFinalPage(page: Int): Boolean {
        TODO("Not yet implemented")
    }

}