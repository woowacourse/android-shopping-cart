@file:Suppress("ktlint")

package woowacourse.shopping.view.shoppingcart

import android.content.Context
import android.util.Log
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
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.cartProduct
import woowacourse.shopping.allCartProducts
import woowacourse.shopping.cartFirstPageProducts
import woowacourse.shopping.cartLastPageProduct
import woowacourse.shopping.domain.CartProduct

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
        viewModel = ShoppingCartViewModel(inventoryRepository, shoppingCartRepository)

        allCartProducts.forEach { cartProduct ->
            shoppingCartRepository.insert(cartProduct)
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

    @Test
    fun 장바구니에서_상품을_삭제하면_해당_상품이_있었던_페이지가_로드된다() {
        // when
        viewModel.requestPage(3)
        viewModel.removeCartItem(
            CartProduct(
                28,
                "[더건강한] 닭가슴살 2종 (100g*4) (택1)",
                12980,
                1,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/3d8a3861-f778-44ef-bff1-d665be4d8f19.jpg",
            )
        )
        viewModel.products.getOrAwaitValue()

        // then
        val actual = viewModel.products.getOrAwaitValue().items
        val expected = listOf(
            CartProduct(
                26,
                "냉동 유기농 블루베리 700g (미국산)",
                22900,
                1,
                "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/1653037727503l0.jpeg",
            ),
            CartProduct(
                27,
                "[애슐리] 홈스토랑 볶음밥 6종 (4개입) (택1)",
                11900,
                1,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/26376544-1943-4773-8665-7f7a1fa1dfb5.jpg",
            ),
            CartProduct(
                29,
                "[태우한우] 1+ 한우 안심 스테이크 200g (냉장)",
                39700,
                1,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/c1ea8fff-29d9-4e12-b2f1-667d76e2bdc9.jpeg",
            ),
        )
        assertThat(actual).isEqualTo(expected)
    }
}
