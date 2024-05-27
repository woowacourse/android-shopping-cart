package woowacourse.shopping.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.local.entity.RecentEntity
import woowacourse.shopping.data.local.entity.RecentProductEntity

@Dao
interface RecentProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRecent(recentEntity: RecentEntity)

    @Query(
        "SELECT productentity.id AS productId, productentity.name, productentity.imgUrl, productentity.price, recententity.createdAt " +
            "FROM recententity LEFT JOIN productentity ON productentity.id = recententity.productId " +
            "ORDER BY recententity.createdAt DESC LIMIT :limit",
    )
    fun findByLimit(limit: Int): List<RecentProductEntity>

    @Query(
        "SELECT productentity.id AS productId, productentity.name, productentity.imgUrl, productentity.price, recententity.createdAt " +
            "FROM recententity LEFT JOIN productentity ON productentity.id = recententity.productId " +
            "ORDER BY recententity.createdAt DESC",
    )
    fun findOne(): RecentProductEntity?
}
