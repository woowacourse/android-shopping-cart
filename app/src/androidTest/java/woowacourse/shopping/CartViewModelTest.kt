package woowacourse.shopping

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.feature.cart.CartViewModel
import woowacourse.shopping.util.getOrAwaitValue

@Suppress("ktlint:standard:function-naming")
class CartViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CartViewModel

    class FakeCartRepository : CartRepository {
        override fun getAll(): LiveData<List<Goods>> = MutableLiveData(emptyList())

        override fun getAllItemsSize(): LiveData<Int> = MutableLiveData(0)

        override fun getPage(
            limit: Int,
            offset: Int,
        ): LiveData<List<Goods>> = MutableLiveData(emptyList())

        override fun insert(
            goods: Goods,
            onComplete: () -> Unit,
        ) {
            onComplete()
        }

        override fun delete(goods: Goods) { }
    }

    @Before
    fun setup() {
        viewModel = CartViewModel(FakeCartRepository())
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
    fun 첫_페이지에서_updatePageButton_호출시_왼쪽버튼_비활성_오른쪽버튼_활성화된다() {
        viewModel.totalCartSize = 10
        viewModel.updatePageButton()

        assertThat(viewModel.isLeftPageEnable.getOrAwaitValue(), `is`(false))
        assertThat(viewModel.isRightPageEnable.getOrAwaitValue(), `is`(true))
    }

    @Test
    fun 마지막_페이지의_마지막_아이템_삭제시_페이지가_이전_페이지로_이동한다() {
        viewModel.totalCartSize = 6
        viewModel.plusPage()
        assertEquals(2, viewModel.page.getOrAwaitValue())

        viewModel.delete(Goods(id = 999L, name = "dummy", price = 0, thumbnailUrl = "dummy"))
        assertEquals(1, viewModel.page.getOrAwaitValue())
    }
}
