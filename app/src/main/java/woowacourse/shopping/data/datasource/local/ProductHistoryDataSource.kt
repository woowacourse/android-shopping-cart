package woowacourse.shopping.data.datasource.local

import woowacourse.shopping.data.model.local.ProductHistoryDto

interface ProductHistoryDataSource {
    fun insertProductHistory(
        productId: Long,
        name: String,
        price: Int,
        imageUrl: String,
    ): Result<Unit>

    fun findProductHistory(productId: Long): Result<ProductHistoryDto>

    fun getProductHistory(size: Int): Result<List<ProductHistoryDto>>

    fun deleteProductHistory(productId: Long): Result<Unit>

    fun deleteAllProductHistory(): Result<Unit>
}
