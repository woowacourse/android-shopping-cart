package woowacourse.shopping.data

import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product

@Suppress("ktlint:standard:max-line-length")
class ProductStorageImpl : ProductStorage {
    private val productDatabase = ProductDatabase

    override fun get(id: Long): Product =
        productDatabase.products[id] ?: throw IllegalArgumentException()

    override fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val fromIndex = page * pageSize
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
