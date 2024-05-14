package woowacourse.shopping.domain

import woowacourse.shopping.presentation.ui.Product

interface ProductRepository {
    fun load(
        pageOffset: Int,
        pageSize: Int,
    ): Result<List<Product>>

    fun loadById(id: Long): Result<Product>
}
