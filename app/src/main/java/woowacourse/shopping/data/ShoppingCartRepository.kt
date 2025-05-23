package woowacourse.shopping.data

import woowacourse.shopping.domain.Page
import woowacourse.shopping.domain.Product

interface ShoppingCartRepository {
    fun getAll(): List<Product>

    fun getPage(
        pageSize: Int,
        pageIndex: Int,
    ): Page<Product>

    fun insert(product: Product)

    fun remove(product: Product)
}
