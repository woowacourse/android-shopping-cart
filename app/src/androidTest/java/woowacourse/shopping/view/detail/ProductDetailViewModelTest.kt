package woowacourse.shopping.view.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.data.DummyShoppingCartRepositoryOld
import woowacourse.shopping.data.ShoppingCartRepositoryOld
import woowacourse.shopping.inventoryItem
import woowacourse.shopping.view.inventory.item.toUiModel

@Suppress("FunctionName")
class ProductDetailViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: ShoppingCartRepositoryOld
    private lateinit var viewModel: ProductDetailViewModel

    @Before
    fun setUp() {
        repository = DummyShoppingCartRepositoryOld()
        viewModel = ProductDetailViewModel(repository)
    }

    @Test
    fun 상품을_추가할_수_있다() {
        // when
        viewModel.addToCart(inventoryItem.toUiModel())

        // then
        val result = repository.getAll()
        assertThat(result).contains(inventoryItem)
    }
}
