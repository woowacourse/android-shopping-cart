package woowacourse.shopping.view.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.productUiModel

@Suppress("FunctionName")
class ProductDetailViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun 상품을_추가할_수_있다() {
        val viewModel = ProductDetailViewModel()
        addProduct(productUiModel)
        assertThat(DummyShoppingCart.productUiModels).contains(productUiModel)
    }
}
