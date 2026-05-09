package woowacourse.shopping.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_table")
    suspend fun getAll(): List<CartEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartEntity: CartEntity)

    @Delete
    suspend fun delete(cartEntity: CartEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM cart_table WHERE productId = :id)")
    suspend fun isExist(id: String): Boolean

    @Query("UPDATE cart_table SET quantity = :quantity WHERE productId = :productId")
    suspend fun updateQuantity(
        productId: String,
        quantity: Int,
    )

    // 반환 타입을 Int?로 수정하여 데이터가 없을 때 null을 안전하게 받도록 합니다.
    @Query("SELECT quantity FROM cart_table WHERE productId = :productId")
    suspend fun getQuantity(productId: String): Int?

    // DB에서 직접 개수를 세어 성능을 높입니다.
    @Query("SELECT COUNT(*) FROM cart_table")
    suspend fun getCartCount(): Int

    @Query("SELECT SUM(quantity) FROM cart_table")
    suspend fun getTotalQuantity(): Int?
}
