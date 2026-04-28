package woowacourse.shopping.repository

import woowacourse.shopping.model.Products

interface ProductRepository {
    fun getAllProduct(): Products
}
