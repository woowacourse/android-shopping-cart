package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.entity.RecentProductEntity

@Dao
interface RecentProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(productEntity: RecentProductEntity): Long

    @Query("SELECT * FROM recent_product WHERE productId =:id")
    fun getById(id: Long): RecentProductEntity?

    @Query(
        """
    SELECT * FROM recent_product 
    ORDER BY createdDateTime DESC 
    LIMIT 1
""",
    )
    fun getLatest(): RecentProductEntity?

    @Query("SELECT * FROM recent_product ORDER BY createdDateTime DESC")
    fun getAll(): List<RecentProductEntity>

    @Query(
        """
    DELETE FROM recent_product 
    WHERE productId IN (
        SELECT productId FROM recent_product 
        WHERE (SELECT COUNT(*) FROM recent_product) > 10
        ORDER BY createdDateTime ASC 
        LIMIT 1
    )
    """,
    )
    fun deleteLastByCreatedDateTime()
}
