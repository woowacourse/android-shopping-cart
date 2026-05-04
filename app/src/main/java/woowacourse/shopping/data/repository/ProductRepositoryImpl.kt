package woowacourse.shopping.data.repository

import woowacourse.shopping.data.DUMMY_PRODUCTS
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.ProductItems
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.math.min

object ProductRepositoryImpl : ProductRepository {
    override fun getProducts(
        page: Int,
        pageSize: Int,
    ): ProductItems {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, DUMMY_PRODUCTS.size)

        if (fromIndex > toIndex) return ProductItems(emptyList())

        val result = DUMMY_PRODUCTS.subList(fromIndex, toIndex)
        return ProductItems(result)
    }

    override fun getProductCount(): Int = DUMMY_PRODUCTS.size

    override fun getProduct(id: String): Product? =
        DUMMY_PRODUCTS.find { it.id == id }
}
