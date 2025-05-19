package woowacourse.shopping.feature.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import fixtureGoods
import fixtureRepository
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.util.getOrAwaitValue

@Suppress("ktlint:standard:function-naming")
class CartViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CartViewModel

    @Before
    fun setup() {
        viewModel = CartViewModel(fixtureRepository)
        Thread.sleep(500)
    }

    @Test
    fun plusPage_호출시_페이지_번호가_증가한다() {
        assertEquals(1, viewModel.page.getOrAwaitValue())
        viewModel.plusPage()
        assertEquals(2, viewModel.page.getOrAwaitValue())
    }

    @Test
    fun minusPage_호출시_페이지_번호가_감소한다() {
        viewModel.plusPage()
        assertEquals(2, viewModel.page.getOrAwaitValue())
        viewModel.minusPage()
        assertEquals(1, viewModel.page.getOrAwaitValue())
    }

    @Test
    fun 첫_페이지에서_왼쪽버튼은_비활성_오른쪽버튼은_활성화된다() {
        assertThat(viewModel.isLeftPageEnable.getOrAwaitValue(), `is`(false))
        assertThat(viewModel.isRightPageEnable.getOrAwaitValue(), `is`(true))
    }

    @Test
    fun 마지막_페이지에서_오른쪽버튼은_비활성_오른쪽버튼은_활성화된다() {
        viewModel.plusPage()
        assertThat(viewModel.isLeftPageEnable.getOrAwaitValue(), `is`(true))
        assertThat(viewModel.isRightPageEnable.getOrAwaitValue(), `is`(false))
    }

    @Test
    fun 아이템으로_장바구니_내의_인덱스를_찾아_반환한다() {
        val indexThreeGoods = fixtureGoods[3]
        assertEquals(viewModel.getPosition(indexThreeGoods), 3)
    }
}
