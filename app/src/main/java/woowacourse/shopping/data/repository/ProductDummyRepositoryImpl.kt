package woowacourse.shopping.data.repository

import woowacourse.shopping.data.dummyProducts
import woowacourse.shopping.domain.model.Product

// TODO: ProductRepositoryImpl 및 Domain layer 내부 ProductRepository 구현
object ProductDummyRepositoryImpl {
    private const val INITIAL_PRODUCT_ID: Int = 0

    fun fetchProducts(
        count: Int,
        lastId: Int = INITIAL_PRODUCT_ID,
    ): List<Product> = dummyProducts.filter { it.id > lastId }.take(count)

    fun fetchProductDetail(id: Int): Product? = dummyProducts.find { it.id == id }

    fun fetchIsProductsLoadable(lastId: Int): Boolean = dummyProducts.maxOfOrNull { it.id > lastId } == true
}
