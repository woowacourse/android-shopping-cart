package woowacourse.shopping.view.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.data.DummyShoppingCartRepository
import woowacourse.shopping.data.ShoppingCartRepository
import woowacourse.shopping.inventoryItem
import woowacourse.shopping.view.inventory.item.toUiModel

@Suppress("FunctionName")
class ProductDetailViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: ShoppingCartRepository
    private lateinit var viewModel: ProductDetailViewModel

    @Before
    fun setUp() {
        repository = DummyShoppingCartRepository()
        viewModel = ProductDetailViewModel(repository)
    }

    @Test
    fun 상품을_추가할_수_있다() {
        // when
        viewModel.addProduct(inventoryItem.toUiModel())

        // then
        val result = repository.getAll()
        assertThat(result).contains(inventoryItem)
    }
}
