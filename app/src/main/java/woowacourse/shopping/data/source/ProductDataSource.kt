package woowacourse.shopping.data.source

import woowacourse.shopping.data.model.ProductData

interface ProductDataSource {
    fun findByPagedAsyncResult(
        page: Int,
        callback: (Result<List<ProductData>>) -> Unit,
    )

    fun findAllUntilPageAsyncResult(
        page: Int,
        callback: (Result<List<ProductData>>) -> Unit,
    )

    fun findByIdAsyncResult(
        id: Long,
        callback: (Result<ProductData>) -> Unit,
    )

    fun isFinalPageAsyncResult(
        page: Int,
        callback: (Result<Boolean>) -> Unit,
    )
}
