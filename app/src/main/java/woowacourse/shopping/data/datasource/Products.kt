package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.local.PRODUCT_DATA
import woowacourse.shopping.data.model.Product
import kotlin.math.min

object Products : ProductDataSource {
    private val products: List<Product> = PRODUCT_DATA

    override fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, products.size)
        return products.subList(fromIndex, toIndex)
    }

    override fun getProductById(id: Long): Product {
        return products.first { it.id == id }
    }
}
