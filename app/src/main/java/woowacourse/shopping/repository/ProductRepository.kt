package woowacourse.shopping.repository

import woowacourse.shopping.model.Products

interface ProductRepository {
    val size: Int

    fun getProducts(fromIndex: Int, limit: Int): Products

    fun hasNext(current: Int): Boolean
}
