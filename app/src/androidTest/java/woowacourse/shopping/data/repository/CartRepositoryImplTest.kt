package woowacourse.shopping.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.ProductFixture
import woowacourse.shopping.data.local.dao.CartDao
import woowacourse.shopping.data.local.database.ShoppingDatabase
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.uuid.ExperimentalUuidApi

class CartRepositoryImplTest {
    private lateinit var database: ShoppingDatabase
    private lateinit var cartDao: CartDao
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database =
            Room
                .inMemoryDatabaseBuilder(
                    context,
                    ShoppingDatabase::class.java,
                ).allowMainThreadQueries()
                .build()
        cartDao = database.cartDao()
        cartRepository = CartRepositoryImpl(cartDao)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun addProductToCart() =
        runTest {
            val product = ProductFixture.productList.first()

            cartRepository.increaseQuantity(product, 1)

            val cart = cartRepository.getItems()

            assertThat(cart.cartItems).hasSize(1)
            assertThat(
                cart.cartItems
                    .first()
                    .product.productId,
            ).isEqualTo(product.productId)
            assertThat(cart.cartItems.first().quantity).isEqualTo(1)
        }

    @Test
    fun increaseQuantityWhenAddingSameProduct() =
        runTest {
            val product = ProductFixture.productList.first()

            cartRepository.increaseQuantity(product, 1)
            cartRepository.increaseQuantity(product, 3)

            val cart = cartRepository.getItems()

            assertThat(cart.cartItems).hasSize(1)
            assertThat(cart.cartItems.first().quantity).isEqualTo(4)
        }

    @Test
    fun removeProductWhenDecreasingQuantityFromOne() =
        runTest {
            val product = ProductFixture.productList.first()

            cartRepository.increaseQuantity(product, 1)
            cartRepository.decreaseQuantity(product.productId)

            val cart = cartRepository.getItems()

            assertThat(cart.cartItems).isEmpty()
        }

    @Test
    fun returnTotalQuantityOfProducts() =
        runTest {
            val product1 = ProductFixture.productList.first()
            val product2 = ProductFixture.productList[1]

            cartRepository.increaseQuantity(product1, 2)
            cartRepository.increaseQuantity(product2, 3)

            val totalQuantity = cartRepository.getTotalQuantity()

            assertThat(totalQuantity).isEqualTo(5)
        }

    @Test
    fun returnPagedCartItems() =
        runTest {
            ProductFixture.productList.take(6).forEach {
                cartRepository.increaseQuantity(it, 1)
            }

            val cart = cartRepository.getPagingItems(page = 1, pageSize = 5)

            assertThat(cart.cartItems).hasSize(1)
        }
}
