package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.domain.Product

class ProductDataSourceImpl : ProductDataSource {
    override fun getProducts(): List<Product> = DummyProducts.values

    override fun getProductById(id: Long): Product? = DummyProducts.values.find { it.productId == id }

    override fun getPagedProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val fromIndex = page * pageSize
        val toIndex = minOf(fromIndex + pageSize, DummyProducts.values.size)
        if (fromIndex >= DummyProducts.values.size) return emptyList()
        return DummyProducts.values.subList(fromIndex, toIndex)
    }
}
