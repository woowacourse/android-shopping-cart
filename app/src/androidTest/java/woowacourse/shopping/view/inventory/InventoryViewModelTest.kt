package woowacourse.shopping.view.inventory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.data.DummyInventoryRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.view.model.InventoryItem
import woowacourse.shopping.view.model.toUiModel

@Suppress("FunctionName")
class InventoryViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun 한_페이지에_상품이_20개씩_로드된다() {
        val repository = DummyInventoryRepository()
        val viewModel = InventoryViewModel(repository)
        val page = repository.getPage(20, 0)
        viewModel.requestPage()
        val products = viewModel.items.getOrAwaitValue().filterIsInstance<InventoryItem.ProductUiModel>()
        assert(products == page.items.map(Product::toUiModel))
    }

    @Test
    fun 상품의_총_개수를_반환한다() {
        val repository = DummyInventoryRepository()
        val viewModel = InventoryViewModel(repository)
        assert(viewModel.totalSize == repository.getAll().size)
    }
}
