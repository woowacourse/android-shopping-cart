package woowacourse.shopping.data.source

import woowacourse.shopping.data.model.ProductData

interface ProductDataSource {
    fun loadPagedItems(page: Int): List<ProductData>

    fun findById(id: Int): ProductData

    fun isFinalPage(page: Int): Boolean
}
