package woowacourse.shopping.view.detail

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.data.inventory.InventoryRepositoryImpl
import woowacourse.shopping.data.product.ProductDatabase
import woowacourse.shopping.data.recent.RecentProductDatabase
import woowacourse.shopping.data.recent.RecentProductRepositoryImpl
import woowacourse.shopping.data.shoppingcart.ShoppingCartDatabase
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepositoryImpl
import woowacourse.shopping.inventoryItem
import woowacourse.shopping.view.inventory.item.toUiModel

@Suppress("FunctionName")
class ProductDetailViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var productDatabase: ProductDatabase
    private lateinit var shoppingCartDatabase: ShoppingCartDatabase
    private lateinit var recentProductDatabase: RecentProductDatabase
    private lateinit var inventoryRepository: InventoryRepositoryImpl
    private lateinit var shoppingCartRepository: ShoppingCartRepositoryImpl
    private lateinit var recentProductRepository: RecentProductRepositoryImpl
    private lateinit var viewModel: ProductDetailViewModel

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        productDatabase =
            Room.inMemoryDatabaseBuilder(context, ProductDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        shoppingCartDatabase =
            Room.inMemoryDatabaseBuilder(context, ShoppingCartDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        recentProductDatabase =
            Room.inMemoryDatabaseBuilder(context, RecentProductDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        inventoryRepository = InventoryRepositoryImpl(productDatabase.productDao())
        shoppingCartRepository = ShoppingCartRepositoryImpl(shoppingCartDatabase.cartItemDao())
        recentProductRepository = RecentProductRepositoryImpl(recentProductDatabase.recentProductDao())
        viewModel = ProductDetailViewModel(inventoryRepository, shoppingCartRepository, recentProductRepository)
    }

    @Test
    fun 상품을_추가할_수_있다() {
        // when
        viewModel.addToCart(inventoryItem.toUiModel())

        // then
        val result =
            inventoryRepository.getAll { products ->
                assertThat(products).contains(inventoryItem)
            }
    }
}
