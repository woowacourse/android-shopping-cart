package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.page.Page

interface ProductRepository {
    fun getProductInRange(start: Page, end: Page): List<Product>
    fun getProductByPage(page: Page): List<Product>
}
