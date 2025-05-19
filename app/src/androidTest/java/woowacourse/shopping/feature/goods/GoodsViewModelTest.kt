package woowacourse.shopping.feature.goods

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import woowacourse.shopping.util.getOrAwaitValue

@Suppress("ktlint:standard:function-naming")
class GoodsViewModelTest {
    private lateinit var viewModel: GoodsViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = GoodsViewModel()
    }

    @Test
    fun 앱_진입시_첫_20개의_상품을_로딩한다() {
        val viewModel = GoodsViewModel()

        val loadedGoods = viewModel.goods.getOrAwaitValue()

        assertEquals(20, loadedGoods.size)
    }

    @Test
    fun addPage_호출시_더_많은_상품을_보여준다() {
        val beforeCount = viewModel.goods.getOrAwaitValue().size
        viewModel.addPage()
        val afterCount = viewModel.goods.getOrAwaitValue().size
        Assertions.assertTrue(afterCount > beforeCount)
    }
}
