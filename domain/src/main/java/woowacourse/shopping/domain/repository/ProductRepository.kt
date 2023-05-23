package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.page.Page

interface ProductRepository {
    fun getProductByPage(page: Page): List<Product>
    fun findProductById(id: Int): Product?
}
