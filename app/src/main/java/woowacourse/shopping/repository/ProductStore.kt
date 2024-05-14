package woowacourse.shopping.repository

import woowacourse.shopping.db.Product

interface ProductStore {
    fun load20Data(currentIndex: Int): List<Product>

    fun findById(id: Int): Product?
}
