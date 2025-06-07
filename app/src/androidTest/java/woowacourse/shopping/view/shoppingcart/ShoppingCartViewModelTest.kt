@file:Suppress("ktlint")

package woowacourse.shopping.view.shoppingcart

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
import woowacourse.shopping.data.shoppingcart.ShoppingCartDatabase
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepositoryImpl
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.util.getOrAwaitValue
import woowacourse.shopping.util.allCartProducts
import woowacourse.shopping.util.cartFirstPageProducts
import woowacourse.shopping.util.cartProduct

class ShoppingCartViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var productDatabase: ProductDatabase
    private lateinit var shoppingCartDatabase: ShoppingCartDatabase
    private lateinit var inventoryRepository: InventoryRepositoryImpl
    private lateinit var shoppingCartRepository: ShoppingCartRepositoryImpl
    private lateinit var viewModel: ShoppingCartViewModel

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
        inventoryRepository = InventoryRepositoryImpl(productDatabase.productDao())
        shoppingCartRepository = ShoppingCartRepositoryImpl(shoppingCartDatabase.cartItemDao())
        viewModel = ShoppingCartViewModel(shoppingCartRepository)

        allCartProducts.forEach { cartProduct ->
            shoppingCartRepository.insert(cartProduct) {}
        }
    }

    @Test
    fun 한_페이지에_장바구니_상품이_5개씩_로드된다() {
        // when
        viewModel.requestPage(0)

        // then
        val actual = viewModel.products.getOrAwaitValue().items
        val expected = cartFirstPageProducts
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun 장바구니에서_상품을_삭제할_수_있다() {
        // when
        viewModel.requestPage(0)
        viewModel.removeCartItem(
            CartProduct(
                11,
                "[금룡각] 마라탕",
                15900,
                1,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/0634a5e8-51e8-4dcd-8b82-6f6237e8c261.jpg",
            ),
        )

        // then
        val result = viewModel.products.getOrAwaitValue().items
        assertThat(result).doesNotContain(cartProduct)
    }
}
