package woowacourse.shopping.view.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.data.DummyShoppingCartRepository
import woowacourse.shopping.product
import woowacourse.shopping.view.model.toUiModel

@Suppress("FunctionName")
class ProductDetailViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun 상품을_추가할_수_있다() {
        val repository = DummyShoppingCartRepository()
        val viewModel = ProductDetailViewModel(repository)
        viewModel.addProduct(product.toUiModel())
        assert(repository.getAll().contains(product))
    }
}
