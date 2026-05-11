package woowacourse.shopping.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Quantity
import woowacourse.shopping.model.ShoppingCartItem
import woowacourse.shopping.repository.dao.ProductDao
import woowacourse.shopping.repository.entity.toModel

@RunWith(AndroidJUnit4::class)
class ShoppingCartRepositoryTest {
    private lateinit var database: AndroidShoppingDatabase
    private lateinit var productDao: ProductDao
    private lateinit var shoppingCartRepository: ShoppingCartRepository
    private lateinit var product: Product

    @Before
    fun setUp() {
        runBlocking {
            val context = ApplicationProvider.getApplicationContext<Context>()
            database =
                Room
                    .inMemoryDatabaseBuilder(context, AndroidShoppingDatabase::class.java)
                    .allowMainThreadQueries()
                    .build()
            productDao = database.productDao()
            productDao.addProduct("동원 스위트콘", 99_800, "")
            product = productDao.getProducts(0, 5).single().toModel()
            shoppingCartRepository =
                DefaultShoppingCartRepository(
                    DefaultProductRepository(productDao),
                    database.shoppingCartItemDao(),
                )
        }
    }

    @After
    fun tearDown() {
        database.close()
    }

    // 장바구니에 상품을 추가할 수 있다
    @Test
    fun add_item_to_shopping_cart_correctly() =
        runTest {
            shoppingCartRepository.addItemToCart(product.id, Quantity(1))
            val shoppingCartItems = shoppingCartRepository.getCartItems(0, 5)

            assertEquals(shoppingCartItems.size, 1)
            assertEquals(shoppingCartItems.single().product, product)
        }

    // 장바구니에 추가된 상품은 삭제할 수 있다
    @Test
    fun remove_item_from_shopping_cart_about_already_added() =
        runTest {
            shoppingCartRepository.addItemToCart(product.id, Quantity(1))
            val addedShoppingCartItem = shoppingCartRepository.getCartItems(0, 5).single()

            shoppingCartRepository.removeItemFromCart(addedShoppingCartItem.product.id)
            assertEquals(shoppingCartRepository.getCartItems(0, 5), emptyList<ShoppingCartItem>())
        }
}
