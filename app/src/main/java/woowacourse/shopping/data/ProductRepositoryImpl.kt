package woowacourse.shopping.data

import woowacourse.shopping.data.dummy.ProductData
import woowacourse.shopping.domain.Product

class ProductRepositoryImpl : ProductRepository {
    override fun getAll(): List<Product> = ProductData.products

    override fun getPaged(
        limit: Int,
        offset: Int,
    ): List<Product> = ProductData.products.subList(offset, limit + offset)
}
