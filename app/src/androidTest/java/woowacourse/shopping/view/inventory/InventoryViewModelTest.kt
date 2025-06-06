package woowacourse.shopping.view.inventory

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.allInventoryProducts
import woowacourse.shopping.data.inventory.InventoryRepositoryImpl
import woowacourse.shopping.data.product.ProductDatabase
import woowacourse.shopping.data.recent.RecentProductDatabase
import woowacourse.shopping.data.recent.RecentProductRepositoryImpl
import woowacourse.shopping.data.shoppingcart.ShoppingCartDatabase
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepositoryImpl
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.view.inventory.item.InventoryItem.ProductUiModel

@Suppress("FunctionName")
class InventoryViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var productDatabase: ProductDatabase
    private lateinit var shoppingCartDatabase: ShoppingCartDatabase
    private lateinit var inventoryRepository: InventoryRepositoryImpl
    private lateinit var shoppingCartRepository: ShoppingCartRepositoryImpl
    private lateinit var recentProductDatabase: RecentProductDatabase
    private lateinit var recentProductRepository: RecentProductRepositoryImpl
    private lateinit var viewModel: InventoryViewModel

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
        viewModel = InventoryViewModel(inventoryRepository, shoppingCartRepository, recentProductRepository)

        allInventoryProducts.forEach { product ->
            inventoryRepository.insert(product)
        }
    }

    @Test
    fun 한_페이지에_상품이_20개씩_로드된다() {
        // when
        viewModel.requestPage()

        // then
        val actual = viewModel.items.getOrAwaitValue().filterIsInstance<ProductUiModel>().size
        val expected = 20
        assertThat(actual).isEqualTo(expected)
    }
}
