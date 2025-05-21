package woowacourse.shopping.view.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.data.DummyShoppingCartRepository
import woowacourse.shopping.product
import woowacourse.shopping.view.inventory.item.toUiModel

@Suppress("FunctionName")
class ProductDetailViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun 상품을_추가할_수_있다() {
        // given
        val repository = DummyShoppingCartRepository()
        val viewModel = ProductDetailViewModel(repository)

        // when
        viewModel.addProduct(product.toUiModel())

        // then
        val result = repository.getAll()
        assertThat(result).contains(product)
    }
}
