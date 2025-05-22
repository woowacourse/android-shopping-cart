package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.database.DummyProduct
import woowacourse.shopping.domain.Product

@Suppress("ktlint:standard:max-line-length")
class ProductDataSourceImpl : ProductDataSource {
    private val productDatabase = DummyProduct

    override fun get(id: Long): Product = productDatabase.products[id] ?: throw IllegalArgumentException()

    override fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val fromIndex = (page - 1) * pageSize
        val toIndex = minOf(fromIndex + pageSize, productDatabase.size)
        return productDatabase.productsValues.subList(fromIndex, toIndex)
    }

    override fun notHasMoreProduct(
        page: Int,
        pageSize: Int,
    ): Boolean {
        val fromIndex = page * pageSize
        return fromIndex >= productDatabase.size
    }
}
