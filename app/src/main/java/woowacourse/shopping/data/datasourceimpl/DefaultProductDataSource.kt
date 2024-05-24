package woowacourse.shopping.data.datasourceimpl

import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.local.PRODUCT_DATA
import woowacourse.shopping.data.model.Product
import kotlin.math.min

object DefaultProductDataSource : ProductDataSource {
    private val products: List<Product> = PRODUCT_DATA

    override fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, products.size)

        if (fromIndex > toIndex) return emptyList()

        return products.subList(fromIndex, toIndex)
    }

    override fun getProductById(id: Long): Product {
        return products.first { it.id == id }
    }

    override fun getProductByIds(ids: List<Long>): List<Product> {
        return ids.map { id -> products.first { it.id == id } }
    }
}
