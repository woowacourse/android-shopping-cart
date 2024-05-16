package woowacourse.shopping.data.db.product

import woowacourse.shopping.domain.model.Product

class ProductDao {
    private val products: List<Product> = ProductDatabase.products

    fun findAll(): List<Product> = products

    fun findProductById(productId: Long): Product? {
        return products.find { it.id == productId }
    }

    fun findPagingProducts(
        offset: Int,
        pagingSize: Int,
    ): List<Product> {
        return if (offset >= products.size) {
            emptyList()
        } else {
            products.drop(offset)
        }.take(pagingSize)
    }
}
