package woowacourse.shopping.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.data.local.AppDatabase
import woowacourse.shopping.data.local.entity.CartItemEntity

@RunWith(AndroidJUnit4::class)
class CartDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var cartDao: CartDao

    private val cartEntity = CartItemEntity(
        productId = "1",
        name = "상품1",
        price = 1000,
        imageUrl = "",
        quantity = 1,
    )

    private val cartEntity1 = CartItemEntity(
        productId = "1",
        name = "상품1",
        price = 1000,
        imageUrl = "",
        quantity = 5,
    )

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()
        cartDao = database.cartDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `장바구니_저장_테스트`() = runBlocking {

        cartDao.insertOrUpdate(cartEntity)

        val items = cartDao.getCartItems().first()
        assertEquals(1, items.size)
        assertEquals("상품1", items[0].name)
    }

    @Test
    fun `장바구니_수량_업데이트_테스트`() = runBlocking {
        cartDao.insertOrUpdate(cartEntity)

        cartDao.insertOrUpdate(cartEntity1)

        val items = cartDao.getCartItems().first()

        assertEquals(1, items.size)
        assertEquals(5, items[0].quantity)
    }

    @Test
    fun `장바구니_삭제_테스트`() = runBlocking {
        cartDao.insertOrUpdate(cartEntity)

        val items = cartDao.getCartItems().first()
        assertEquals(1, items.size)

        cartDao.deleteById("1")

        val deletedItems = cartDao.getCartItems().first()

        assertTrue(deletedItems.isEmpty())
    }
}
