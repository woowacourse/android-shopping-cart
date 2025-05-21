package woowacourse.shopping.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun getAll(): LiveData<List<CartEntity>>

    @Insert
    fun insertAll(vararg cartEntities: CartEntity)

    @Delete
    fun delete(cartEntity: CartEntity)

    @Query("SELECT * FROM cart LIMIT :limit OFFSET :offset")
    fun getPage(
        limit: Int,
        offset: Int,
    ): LiveData<List<CartEntity>>

    @Query("SELECT COUNT(*) FROM cart")
    fun getAllItemsSize(): LiveData<Int>

    @Query("SELECT * FROM cart WHERE id = :id LIMIT 1")
    fun findById(id: Long): CartEntity?

    @Update
    fun update(cartEntity: CartEntity)
}
