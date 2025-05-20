package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.model.Goods

interface CartRepository {
    fun getAll(): List<Goods>

    fun insert(
        goods: Goods,
        onComplete: () -> Unit,
    )

    fun delete(
        goods: Goods,
        onComplete: () -> Unit,
    )

    fun getPage(
        limit: Int,
        offset: Int,
    ): List<Goods>

    fun getAllItemsSize(): Int
}
