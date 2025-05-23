package woowacourse.shopping.data.product

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.product.ProductEntity.Companion.PRODUCT_TABLE_NAME

@Dao
interface ProductDao {
    @Query("SELECT * FROM $PRODUCT_TABLE_NAME WHERE id == :id")
    fun getOrNull(id: Int): ProductEntity?

    @Query("SELECT * FROM $PRODUCT_TABLE_NAME")
    fun getAll(): List<ProductEntity>

    @Query("DELETE FROM $PRODUCT_TABLE_NAME")
    fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<ProductEntity>)
}
