package woowacourse.shopping.data.datasource.product

import woowacourse.shopping.data.model.Page
import woowacourse.shopping.data.model.Product

interface ProductDataSource {
    interface Local

    interface Remote {
        fun getProductByPage(page: Page): List<Product>
        fun findProductById(id: Int): Product?
    }
}
