package woowacourse.shopping.view.inventory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.data.DummyInventoryRepository
import woowacourse.shopping.data.InventoryRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.view.inventory.item.InventoryItem
import woowacourse.shopping.view.inventory.item.toUiModel

@Suppress("FunctionName")
class InventoryViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: InventoryRepository
    private lateinit var viewModel: InventoryViewModel

    @Before
    fun setUp() {
        repository = DummyInventoryRepository()
        viewModel = InventoryViewModel(repository)
    }

    @Test
    fun 한_페이지에_상품이_20개씩_로드된다() {
        // when
        viewModel.requestPage()

        // then
        val actual = viewModel.items.getOrAwaitValue().filterIsInstance<InventoryItem.ProductUiModel>()
        val expected = repository.getPage(20, 0).items.map(Product::toUiModel)
        assertThat(actual).isEqualTo(expected)
    }
}
