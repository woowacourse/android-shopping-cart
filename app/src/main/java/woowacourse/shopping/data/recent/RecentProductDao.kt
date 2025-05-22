package woowacourse.shopping.data.recent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface RecentProductDao {
    @Insert
    fun insert(product: RecentProductEntity)

    @Query("INSERT INTO recent_product(product_id) VALUES(:productId)")
    fun insertByProductId(productId: Long)

    @Query("SELECT * from recent_product")
    fun getAll(): List<RecentProductEntity>

    @Query("SELECT * FROM recent_product ORDER BY id DESC LIMIT :limit OFFSET :offset")
    fun getPaged(
        limit: Int,
        offset: Int,
    ): List<RecentProductEntity>

    @Query("DELETE from recent_product WHERE product_id == :productId")
    fun deleteByProductId(productId: Long)

    @Transaction
    fun replaceRecentProduct(productId: Long) {
        deleteByProductId(productId)
        insertByProductId(productId)
    }
}
