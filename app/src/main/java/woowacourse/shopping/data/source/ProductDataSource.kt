package woowacourse.shopping.data.source

import woowacourse.shopping.data.model.ProductData

interface ProductDataSource {
    fun findByPaged(page: Int): List<ProductData>

    fun findById(id: Long): ProductData

    fun isFinalPage(page: Int): Boolean

    fun shutDown(): Boolean
}
