package woowacourse.shopping.data.db.product

import woowacourse.shopping.domain.model.Product

interface ProductService {
    fun getSize(): Int

    fun findPageProducts(
        start: Int,
        offset: Int,
    ): List<Product>

    fun findProductById(id: Long): Product
}
