package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import woowacourse.shopping.data.entity.LastProductEntity

@Dao
interface LastProductDao {

    @Query("SELECT * FROM lastProduct ORDER BY viewedAt DESC LIMIT :count")
    fun getRecent(count : Int): List<LastProductEntity>

    @Query("SELECT * FROM lastProduct WHERE productId = :productId LIMIT 1")
    fun findByProductId(productId: Int): LastProductEntity?

    @Insert
    fun insert(cart: LastProductEntity)

    @Delete
    fun delete(cart: LastProductEntity)

    @Query("""
        DELETE FROM lastProduct 
        WHERE id NOT IN (
            SELECT id FROM lastProduct ORDER BY viewedAt DESC LIMIT 10
        )
    """)
    fun deleteOldExceptRecent10()
}