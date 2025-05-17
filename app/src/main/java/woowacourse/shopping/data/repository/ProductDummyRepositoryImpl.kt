package woowacourse.shopping.data.repository

import woowacourse.shopping.data.dummyProducts
import woowacourse.shopping.domain.model.Product

object ProductDummyRepositoryImpl : ProductRepository {
    const val INITIAL_PRODUCT_ID: Int = 0

    override fun fetchProducts(
        count: Int,
        lastId: Int,
    ): List<Product> = dummyProducts.filter { it.id > lastId }.take(count)

    override fun fetchProductDetail(id: Int): Product? = dummyProducts.find { it.id == id }

    override fun fetchIsProductsLoadable(lastId: Int): Boolean = dummyProducts.maxOfOrNull { it.id > lastId } == true
}
