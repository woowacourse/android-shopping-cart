package woowacourse.shopping.data.product

import woowacourse.shopping.model.Product

interface ProductRepository {
    fun start()

    fun find(id: Long): Product

    fun findAll(): List<Product>

    fun getProducts(): List<Product>

    fun shutdown()
}
