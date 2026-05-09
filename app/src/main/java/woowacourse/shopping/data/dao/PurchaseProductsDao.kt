package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.data.entity.PurchaseProductEntity
import java.util.UUID
@Dao
interface PurchaseProductsDao {
    @Query("SELECT * FROM purchase_products")
    fun getAll(): Flow<List<PurchaseProductEntity>>

    @Query("SELECT * FROM purchase_products WHERE uuid = :id")
    fun findWithId(id: UUID): Flow<PurchaseProductEntity>

    @Insert
    suspend fun insertAll(vararg purchaseProductEntity: PurchaseProductEntity)

    @Query("UPDATE purchase_products SET count = count + :delta WHERE uuid = :id")
    suspend fun updateCount(id: UUID, delta: Int)

    @Query("DELETE FROM purchase_products WHERE uuid = :id")
    suspend fun deleteWithId(id: UUID)

    @Query("SELECT SUM(count) FROM purchase_products")
    fun getTotalAmount(): Flow<Int>

    @Query("SELECT count FROM purchase_products WHERE uuid = :id")
    fun getCountOfSpecificPurchaseProduct(id: UUID): Flow<Int>

    @Query("SELECT price * count FROM purchase_products WHERE uuid = :productId")
    fun getTotalPriceOfSpecificPurchaseProduct(productId: UUID): Flow<Int>

    @Query("SELECT * FROM purchase_products LIMIT :limit OFFSET :offset")
    fun getPartedPurchaseProducts(limit: Int, offset: Int): Flow<List<PurchaseProductEntity>>

    @Query("SELECT COUNT(*) FROM purchase_products")
    fun getProductCount(): Flow<Int>

    @Query("SELECT EXISTS (SELECT * FROM purchase_products WHERE uuid = :id)")
    fun isContained(id: UUID): Boolean
}
