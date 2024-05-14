package woowacourse.shopping.repository

import woowacourse.shopping.db.Product

interface ProductStore {
    fun loadData(currentIndex: Int): List<Product>
}
