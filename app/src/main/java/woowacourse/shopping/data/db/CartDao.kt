package woowacourse.shopping.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {
    @Query("SELECT * FROM ProductEntity")
    fun getAll(): List<ProductEntity>

    @Query("DELETE FROM ProductEntity WHERE id = :id")
    fun delete(id: Long)

    @Insert
    fun insert(product: ProductEntity)
}
