package woowacourse.shopping.data.remote

import androidx.room.Dao
import woowacourse.shopping.data.local.entity.ProductEntity

@Dao
interface ProductDao {
    fun findById(id: Long): ProductEntity?

    fun findPagedItems(
        limit: Int,
        offset: Int,
    ): List<ProductEntity>

    fun insert(productEntity: ProductEntity)

    fun insertAll(vararg productEntity: ProductEntity)

    fun delete(productEntity: ProductEntity)

    fun deleteById(id: Long)

    fun size(): Int
}
