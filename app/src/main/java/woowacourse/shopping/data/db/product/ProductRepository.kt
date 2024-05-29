package woowacourse.shopping.data.db.product

import woowacourse.shopping.domain.model.Product

interface ProductRepository {
    fun findProductsByPage(): List<Product>

    fun findProductById(id: Long): Product?

    fun canLoadMore(): Boolean
}
