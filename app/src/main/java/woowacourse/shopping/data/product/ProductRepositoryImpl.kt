package woowacourse.shopping.data.product

import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductRepository

class ProductRepositoryImpl : ProductRepository {
    private val products = ProductDummy.products

    override fun productsByPageNumberAndSize(
        pageNumber: Int,
        loadSize: Int,
    ): List<Product> {
        val fromIndex = pageNumber * loadSize
        val toIndex = (fromIndex + loadSize).coerceAtMost(products.size)
        return products.subList(fromIndex, toIndex)
    }

    override fun fetchById(id: Long): Product? {
        return products.find { product -> product.id == id }
    }

    override fun canMoreLoad(
        pageNumber: Int,
        loadSize: Int,
    ): Boolean {
        val fromIndex = pageNumber * loadSize
        return fromIndex < products.size
    }
}
