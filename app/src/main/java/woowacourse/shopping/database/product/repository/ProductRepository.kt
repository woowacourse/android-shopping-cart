package woowacourse.shopping.database.product.repository

import model.Product

interface ProductRepository {

    fun getProductById(id: Int): Product

    fun getProductInRange(from: Int, count: Int): List<Product>
}
