package woowacourse.shopping.data.db.product

import woowacourse.shopping.domain.Product

class ProductDao {
    private val products: List<Product> = ProductDatabase.products

    fun findAll(): List<Product> = products

    fun findProductById(productId: Long): Product? {
        return products.find { it.id == productId }
    }

}
