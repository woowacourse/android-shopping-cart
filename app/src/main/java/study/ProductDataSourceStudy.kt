package study

import woowacourse.shopping.data.model.ProductData

interface ProductDataSourceStudy {
    fun findByPaged(page: Int): List<ProductData>

    fun findById(id: Long): ProductData

    fun isFinalPage(page: Int): Boolean

    fun shutDown(): Boolean

    fun findByPagedAsync(
        page: Int,
        callback: (List<ProductData>) -> Unit,
    )

    fun findByIdAsync(
        id: Long,
        callback: (ProductData) -> Unit,
    )

    fun isFinalPageAsync(
        page: Int,
        callback: (Boolean) -> Unit,
    )

    // return Result

    fun findByPagedResult(page: Int): Result<List<ProductData>>

    fun findByIdResult(id: Long): Result<ProductData>

    fun isFinalPageResult(page: Int): Result<Boolean>

    // callback wrapped in Result

    fun findByPagedAsyncResult(
        page: Int,
        callback: (Result<List<ProductData>>) -> Unit
    )

    fun findByIdAsyncResult(
        id: Long,
        callback: (Result<ProductData>) -> Unit
    )

    fun isFinalPageAsyncResult(
        page: Int,
        callback: (Result<Boolean>) -> Unit
    )

}
