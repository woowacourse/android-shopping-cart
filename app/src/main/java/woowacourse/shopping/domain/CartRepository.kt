package woowacourse.shopping.domain

import woowacourse.shopping.presentation.ui.Product

interface CartRepository {
    fun addData(product: Product): Result<Long>

    fun delete(product: Product): Result<Long>

    fun load(
        pageOffset: Int,
        pageSize: Int,
    ): Result<List<Cart>>

    fun getMaxOffset(): Result<Int>
}
