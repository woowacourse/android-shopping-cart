package woowacourse.shopping.data.db.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartDao {
    @Query("SELECT * FROM cartProducts")
    fun loadAll(): List<CartEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cartEntity: CartEntity): Long

    @Query("SELECT * FROM cartProducts WHERE product_id = :productId")
    fun find(productId: Long): CartEntity?

    @Query("DELETE FROM cartProducts WHERE product_id = :productId")
    fun delete(productId: Long)

    @Query("UPDATE cartProducts SET quantity = MAX(0, quantity + :quantityDelta) WHERE product_id = :productId")
    fun modifyQuantity(
        productId: Long,
        quantityDelta: Int,
    ): Int

    @Query("UPDATE cartProducts SET quantity = MAX(0,:newQuantityValue) WHERE product_id = :productId")
    fun setQuantity(
        productId: Long,
        newQuantityValue: Int,
    ): Int

    @Query("SELECT * FROM cartProducts LIMIT :pageSize OFFSET :startPage * :pageSize")
    fun loadPage(
        startPage: Int,
        pageSize: Int,
    ): List<CartEntity>

    @Query("SELECT COUNT(*) FROM cartProducts")
    fun countItems(): Int
}
