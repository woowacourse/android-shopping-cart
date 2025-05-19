package woowacourse.shopping.data.product

import woowacourse.shopping.data.dummy.ProductData
import woowacourse.shopping.domain.Product

class ProductDao {
    fun getAll(): List<Product> = ProductData.products

    fun count(): Int = ProductData.products.size

    fun getPaged(
        limit: Int,
        offset: Int,
    ): List<Product> {
        val items = ProductData.products.subList(offset, offset + limit)

        return items
    }
}
