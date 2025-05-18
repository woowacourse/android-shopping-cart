package woowacourse.shopping.view.inventory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.data.DummyInventoryRepository
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.view.page.Page

@Suppress("FunctionName")
class InventoryViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun 한_페이지에_상품이_20개씩_로드된다() {
        val repository = DummyInventoryRepository()
        val viewModel = InventoryViewModel(repository)
        val page =
            Page.from(
                repository.getAll(),
                0,
                20,
            )
        viewModel.requestProductsPage(0)
        assert(viewModel.productsLiveData.getOrAwaitValue() == page)
    }

    @Test
    fun 상품의_총_개수를_반환한다() {
        val repository = DummyInventoryRepository()
        val viewModel = InventoryViewModel(repository)
        assert(viewModel.totalSize == repository.getAll().size)
    }
}
