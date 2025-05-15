package woowacourse.shopping.data

import woowacourse.shopping.data.dummy.ProductData
import woowacourse.shopping.domain.Product

class ProductRepositoryImpl : ProductRepository {
    override fun getAll(): List<Product> = ProductData.products

    override fun getPaged(
        limit: Int,
        offset: Int,
    ): List<Product> {
        val endIndex = (offset + limit).coerceAtMost(ProductData.products.size)
        if (offset >= ProductData.products.size) return emptyList()
        return ProductData.products.subList(offset, endIndex)
    }
}
