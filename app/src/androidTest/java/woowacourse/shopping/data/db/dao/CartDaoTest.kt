package woowacourse.shopping.data.db.dao

import androidx.room.Room
import org.junit.Before
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.db.ShoppingDatabase
import woowacourse.shopping.util.testApplicationContext

//     @Query("SELECT * FROM Cart LIMIT :size OFFSET :offset")
//    fun loadCart(offset: Int, size: Int): List<CartEntity>
//
//    @Insert
//    fun saveCart(cart: CartEntity)
//
//    @Update
//    fun updateCart(cart: CartEntity)
//
//    @Query("DELETE FROM Cart WHERE id = :id")
//    fun deleteCart(id: Long)
//
//    @Query("DELETE FROM Cart")
//    fun deleteAllCarts()
//}
class CartDaoTest {
    private lateinit var movieDao: CartDao

    @Before
    fun setUp() {
        val db =
            Room.inMemoryDatabaseBuilder(
                testApplicationContext,
                ShoppingDatabase::class.java,
            ).build()
        movieDao = db.cartDao()
    }

    @Test
    fun `test`() {
        // given

        // when

        // then
    }
}