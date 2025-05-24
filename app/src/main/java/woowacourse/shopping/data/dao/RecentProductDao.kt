package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import woowacourse.shopping.data.entity.RecentProductEntity
import woowacourse.shopping.data.entity.RecentProductWithProduct

@Dao
interface RecentProductDao {
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    fun insert(recentProductEntity: RecentProductEntity)

    @Query(
        "DELETE FROM recent_product " +
            "WHERE viewTime = (SELECT MIN(viewTime) FROM recent_product LIMIT 1)",
    )
    fun deleteLast()

    @Query(
        "SELECT * FROM recent_product " +
            "INNER JOIN product ON recent_product.product_id = product.id",
    )
    fun findAll(): List<RecentProductWithProduct>

    @Transaction
    fun save(recentProductEntity: RecentProductEntity) {
        if (!doesProductExist(recentProductEntity.productId)) {
            deleteLast()
        }
        insert(recentProductEntity)
    }

    @Query("SELECT EXISTS(SELECT 1 FROM recent_product WHERE product_id = :productId LIMIT 1)")
    fun doesProductExist(productId: Long): Boolean
}
