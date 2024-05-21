package woowacourse.shopping.domain.repository.local

import woowacourse.shopping.domain.model.ProductHistory

interface ProductHistoryRepository {
    fun insertProductHistory(
        productId: Long,
        name: String,
        price: Int,
        imageUrl: String,
    ): Result<Unit>

    fun findProductHistory(productId: Long): Result<ProductHistory>

    fun getProductHistory(size: Int): Result<List<ProductHistory>>

    fun deleteProductHistory(productId: Long): Result<Unit>

    fun deleteAllProductHistory(): Result<Unit>
}
