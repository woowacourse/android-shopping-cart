package woowacourse.shopping.data.db.producthistory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductHistoryDao {
    @Query("SELECT * FROM product_history ORDER BY id DESC LIMIT 10")
    fun getAll(): List<ProductHistory>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(productHistory: ProductHistory): Long

    @Query("SELECT * FROM product_history ORDER BY id DESC LIMIT 1")
    fun getMostRecentProductHistory(): ProductHistory?
}
