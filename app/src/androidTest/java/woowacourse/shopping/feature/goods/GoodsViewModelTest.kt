package woowacourse.shopping.feature.goods

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import fixtureCartRepository
import fixtureGoodsRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.util.getOrAwaitValue

@Suppress("ktlint:standard:function-naming")
class GoodsViewModelTest {
    private lateinit var viewModel: GoodsViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = GoodsViewModel(fixtureCartRepository, fixtureGoodsRepository)
    }

    @Test
    fun 앱_진입시_첫_20개의_상품을_로딩한다() {
        val loadedCartItems = viewModel.cartItemsWithZeroQuantity.getOrAwaitValue()

        assertEquals(20, loadedCartItems.size)
        assertTrue(loadedCartItems.all { it.quantity == 0 })
    }

    @Test
    fun addPage_호출시_더_많은_상품을_보여준다() {
        val beforeCartItems = viewModel.cartItemsWithZeroQuantity.getOrAwaitValue()
        val beforeCount = beforeCartItems.size

        viewModel.addPage()

        val afterCartItems = viewModel.cartItemsWithZeroQuantity.getOrAwaitValue()
        val afterCount = afterCartItems.size

        assertTrue(afterCount > beforeCount)
        assertEquals(30, afterCount)
    }

    @Test
    fun 모든_상품을_로딩하면_isFullLoaded가_true가_된다() {
        // 첫 페이지 로딩 후
        viewModel.addPage() // 두 번째 페이지 로딩

        val isFullLoaded = viewModel.isFullLoaded.getOrAwaitValue()
        assertTrue(isFullLoaded)
    }

    @Test
    fun 초기_장바구니_아이템_크기는_0이다() {
        val totalCartItemSize = viewModel.totalCartItemSize.getOrAwaitValue()
        assertEquals("0", totalCartItemSize)
    }

    @Test
    fun 최근_본_상품이_초기에_로딩된다() {
        val recentlyViewedGoods = viewModel.recentlyViewedGoods.getOrAwaitValue()

        assertEquals(0, recentlyViewedGoods.size)
    }

    @Test
    fun 장바구니_아이템_추가시_수량이_증가한다() {
        val cartItems = viewModel.cartItemsWithZeroQuantity.getOrAwaitValue()
        val firstItem = cartItems.first()

        viewModel.addCartItemOrIncreaseQuantity(firstItem.copy(quantity = 1))

        val totalCartItemSize = viewModel.totalCartItemSize.getOrAwaitValue()
        assertEquals("1", totalCartItemSize)
    }

    @Test
    fun 장바구니_아이템_제거시_수량이_감소한다() {
        val cartItems = viewModel.cartItemsWithZeroQuantity.getOrAwaitValue()
        val firstItem = cartItems.first()

        viewModel.addCartItemOrIncreaseQuantity(firstItem.copy(quantity = 2))
        viewModel.removeCartItemOrDecreaseQuantity(firstItem.copy(quantity = 1))

        val totalCartItemSize = viewModel.totalCartItemSize.getOrAwaitValue()
        assertEquals("1", totalCartItemSize)
    }

    @Test
    fun 장바구니_수량이_99개_초과시_99plus_표시된다() {
        val cartItems = viewModel.cartItemsWithZeroQuantity.getOrAwaitValue()
        val firstItem = cartItems.first()

        viewModel.addCartItemOrIncreaseQuantity(firstItem.copy(quantity = 100))

        val totalCartItemSize = viewModel.totalCartItemSize.getOrAwaitValue()
        assertEquals("99+", totalCartItemSize)
    }
}
