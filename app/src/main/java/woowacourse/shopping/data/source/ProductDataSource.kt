package woowacourse.shopping.data.source

import woowacourse.shopping.data.model.ProductData

interface ProductDataSource {
    fun findByPaged(page: Int): List<ProductData>

    fun findById(id: Long): ProductData

    fun isFinalPage(page: Int): Boolean

    fun shutDown(): Boolean

    fun findByPagedAsync(page: Int, callback: (List<ProductData>) -> Unit)

    fun findByIdAsync(id: Long, callback: (ProductData) -> Unit)

    fun isFinalPageAsync(page: Int, callback: (Boolean) -> Unit)
}
