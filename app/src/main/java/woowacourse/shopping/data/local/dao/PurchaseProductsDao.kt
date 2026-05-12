package woowacourse.shopping.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.data.local.entity.PurchaseProductEntity

@Dao
interface PurchaseProductsDao {
    @Query("SELECT * FROM purchase_products")
    fun getAll(): Flow<List<PurchaseProductEntity>?>

    @Query("SELECT * FROM purchase_products WHERE id = :id")
    fun findWithId(id: String): Flow<PurchaseProductEntity?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIgnore(entity: PurchaseProductEntity): Long

    @Query("UPDATE purchase_products SET count = count + :delta WHERE id = :id")
    suspend fun updateCount(
        id: String,
        delta: Int,
    )

    @Transaction
    suspend fun upsert(entity: PurchaseProductEntity) {
        val id = insertIgnore(entity)
        if (id == -1L) updateCount(entity.id, entity.count)
    }

    @Query("DELETE FROM purchase_products WHERE id = :id")
    suspend fun deleteWithId(id: String)

    @Query("SELECT SUM(count) FROM purchase_products")
    fun getTotalAmount(): Flow<Int>

    @Query("SELECT count FROM purchase_products WHERE id = :id")
    fun getCountOfSpecificPurchaseProduct(id: String): Flow<Int>

    @Query("SELECT * FROM purchase_products LIMIT :limit OFFSET :offset")
    fun getPartedPurchaseProducts(
        limit: Int,
        offset: Int,
    ): Flow<List<PurchaseProductEntity>>

    @Query("SELECT COUNT(*) FROM purchase_products")
    fun getProductCount(): Flow<Int>

    @Query("SELECT EXISTS (SELECT * FROM purchase_products WHERE id = :id)")
    fun isContained(id: String): Boolean
}
