package woowacourse.shopping.data.repository

import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.domain.Product

interface ShoppingCartRepository {
    fun findAll(pageRequest: PageRequest): Page<Product>

    fun totalSize(): Int

    fun remove(product: Product)
}
