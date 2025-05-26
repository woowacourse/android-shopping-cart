package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import woowacourse.shopping.data.entity.ShoppingEntity

@Dao
interface ShoppingDao {
    @Query("SELECT * FROM shoppingCart")
    fun getAll(): List<ShoppingEntity>

    @Query("DELETE FROM shoppingCart WHERE id = :id")
    fun delete(id: Int)

    @Query("UPDATE shoppingCart SET quantity = quantity + :quantity WHERE id = :id")
    fun increaseQuantity(
        id: Int,
        quantity: Int,
    )

    @Transaction
    fun decreaseOrDelete(
        id: Int,
        quantity: Int,
    ) {
        decreaseQuantity(id, quantity)
        deleteIfQuantityZero(id)
    }

    @Query("UPDATE shoppingCart SET quantity = quantity - :quantity WHERE id = :id")
    fun decreaseQuantity(
        id: Int,
        quantity: Int,
    )

    @Query("DELETE FROM shoppingCart WHERE id = :id AND quantity <= 0")
    fun deleteIfQuantityZero(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(goods: ShoppingEntity)

    @Query("SELECT * FROM shoppingCart ORDER BY id ASC LIMIT :count OFFSET (:page * :count)")
    fun getPagedGoods(
        page: Int,
        count: Int,
    ): List<ShoppingEntity>

    @Query("SELECT * FROM shoppingCart WHERE id = :id")
    fun findGoodsById(id: Int): ShoppingEntity?
}
