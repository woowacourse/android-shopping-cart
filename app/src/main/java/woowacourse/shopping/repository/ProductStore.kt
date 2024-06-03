package woowacourse.shopping.repository

import woowacourse.shopping.model.Product

interface ProductStore {
    fun loadDataAsNeeded(currentIndex: Int): List<Product>

    fun findById(findId: Int): Product?
}
