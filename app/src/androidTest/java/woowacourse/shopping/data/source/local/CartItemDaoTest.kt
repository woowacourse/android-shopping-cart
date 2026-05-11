package woowacourse.shopping.data.source.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.data.source.local.cart.CartItemDao
import woowacourse.shopping.data.source.local.cart.CartItemEntity

@RunWith(AndroidJUnit4::class)
class CartItemDaoTest {
    private lateinit var database: ShoppingDataBase
    private lateinit var cartDao: CartItemDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room
            .inMemoryDatabaseBuilder(context, ShoppingDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        cartDao = database.cartItemDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `카트_아이템을_추가_할_수_있다`() =
        runBlocking {
            cartDao.insert(
                CartItemEntity(
                    productId = "1",
                    quantity = 1,
                ),
            )

            assert(cartDao.getCartItems().size == 1)
        }

    @Test
    fun `카트_아이템을_업데이트_할_수_있다`() =
        runBlocking {
            val cartItem = CartItemEntity(
                productId = "1",
                quantity = 1,
            )

            cartDao.insert(cartItem)

            cartDao.update(cartItem.copy(quantity = 3))

            assert(cartDao.getCartItems().first().quantity == 3)
        }

    @Test
    fun `카트_아이템을_제거_할_수_있다`() =
        runBlocking {
            cartDao.insert(
                CartItemEntity(
                    productId = "1",
                    quantity = 1,
                ),
            )

            cartDao.delete(productId = "1")

            assert(cartDao.getCartItems().size == 0)
        }

    @Test
    fun `상품_아이디를_통해_특정_카트_아이템을_가져올_수_있다`() =
        runBlocking {
            val cartItem = CartItemEntity(
                productId = "1",
                quantity = 1,
            )

            cartDao.insert(cartItem)

            assert(cartDao.getCartItemById("1") == cartItem)
        }

    @Test
    fun `총_카트_아이템의_개수를_반환한다`() =
        runBlocking {
            val cartItem = CartItemEntity(
                productId = "1",
                quantity = 1,
            )

            cartDao.insert(cartItem)

            assert(cartDao.getTotalCount() == 1)
        }
}
