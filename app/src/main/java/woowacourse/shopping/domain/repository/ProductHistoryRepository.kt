package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface ProductHistoryRepository {
    fun saveProductHistory(productId: Long)

    fun loadAllProductHistory(): List<Product>

    fun loadProductHistory(productId: Long): Product

    fun loadLatestProduct(): Product

    fun deleteAllProductHistory()
}
