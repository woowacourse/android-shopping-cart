package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface ProductHistoryRepository {
    fun saveProductHistory(productId: Long)

    fun loadAllProductHistory(): List<Product>

    fun loadProductHistory(productId: Long): Product

    fun loadLatestProduct(): Product

    fun saveProductHistoryAsync(productId: Long, callback: (Boolean) -> Unit)

    fun loadLatestProductAsync(callback: (Long) -> Unit)

    fun loadAllProductHistoryAsync(callback: (List<Product>) -> Unit)

    fun loadProductHistoryAsync(productId: Long, callback: (Product) -> Unit)

}
