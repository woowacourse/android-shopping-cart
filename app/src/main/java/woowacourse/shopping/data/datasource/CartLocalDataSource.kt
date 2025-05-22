package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.entity.CartEntity

interface CartLocalDataSource {
    fun insert(item: CartEntity)

    fun getAll(): List<CartEntity>

    fun getByIds(ids: List<Long>): List<CartEntity?>

    fun totalSize(): Int

    fun deleteById(id: Long)

    fun getPaged(
        offset: Int,
        limit: Int,
    ): List<CartEntity>

    fun hasOnlyPage(limit: Int): Boolean
}
