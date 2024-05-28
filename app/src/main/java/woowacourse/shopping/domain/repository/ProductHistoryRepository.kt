package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface ProductHistoryRepository {
    fun insertProductHistory(
        productId: Long,
        name: String,
        price: Int,
        imageUrl: String,
    ): Result<Unit>

    fun findProductHistory(productId: Long): Result<Product>

    fun getProductHistory(size: Int): Result<List<Product>>

    fun deleteProductHistory(productId: Long): Result<Unit>

    fun deleteAllProductHistory(): Result<Unit>
}
