package woowacourse.shopping.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.shopping.data.db.model.ProductEntity

@Dao
interface ProductDao {
    @Insert
    fun putProduct(productEntity: ProductEntity)

    @Query("select * from ProductEntity where id == :id")
    fun findById(id: Int): ProductEntity?

    @Query("select * from ProductEntity where id > :offset * :size limit :size")
    fun findByOffsetAndSize(
        offset: Int,
        size: Int,
    ): List<ProductEntity>
}
