package woowacourse.shopping

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.feature.goods.GoodsViewModel
import woowacourse.shopping.util.InstantTaskExecutorExtension
import woowacourse.shopping.util.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
@Suppress("ktlint:standard:function-naming")
class GoodsViewModelTest {
    private lateinit var viewModel: GoodsViewModel

    @BeforeEach
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
        assertTrue(afterCount > beforeCount)
    }
}
