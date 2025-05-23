package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import woowacourse.shopping.data.entity.RecentProductEntity
import woowacourse.shopping.data.entity.RecentProductWithProduct

@Dao
interface RecentProductDao {
    @Insert
    fun insert(recentProductEntity: RecentProductEntity)

    @Query(
        "DELETE FROM recent_product " +
            "WHERE id = (SELECT MAX(id) FROM recent_product);",
    )
    fun deleteLast()

    @Query("SELECT * FROM recent_product")
    fun findAll(): List<RecentProductWithProduct>

    @Transaction
    fun save(recentProductEntity: RecentProductEntity) {
        deleteLast()
        insert(recentProductEntity)
    }
}
