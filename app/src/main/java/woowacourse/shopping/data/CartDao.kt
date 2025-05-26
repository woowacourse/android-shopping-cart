// package woowacourse.shopping.data
//
// import androidx.lifecycle.LiveData
// import androidx.room.Dao
// import androidx.room.Insert
// import androidx.room.OnConflictStrategy
// import androidx.room.Query
//
// @Dao
// interface CartDao {
//    @Query("SELECT * FROM shopping_cart_items")
//    fun getAllItemsLiveData(): LiveData<List<ShoppingCartItemEntity>>
//
//    @Query("SELECT * FROM shopping_cart_items")
//    suspend fun getAllItemsList(): List<ShoppingCartItemEntity>
//
//    @Query("SELECT * FROM shopping_cart_items WHERE productId = :productId")
//    suspend fun getItemById(productId: Int): ShoppingCartItemEntity?
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertItem(item: ShoppingCartItemEntity)
//
//    @Query("DELETE FROM shopping_cart_items WHERE productId = :productId")
//    suspend fun deleteItemById(productId: Int)
// }
