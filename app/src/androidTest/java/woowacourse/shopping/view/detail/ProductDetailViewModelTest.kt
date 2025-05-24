package woowacourse.shopping.view.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.fixture.FakeRecentProductRepository
import woowacourse.shopping.fixture.FakeShoppingCartRepository
import woowacourse.shopping.fixture.TestProducts
import woowacourse.shopping.mapper.toProductUiModel

@Suppress("FunctionName")
class ProductDetailViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun 상품을_추가할_수_있다() {
        val product = TestProducts.productUiModels[0]
        val viewModel =
            ProductDetailViewModel(
                FakeShoppingCartRepository(),
                FakeRecentProductRepository(),
            )
        viewModel.addProduct(product.toProductUiModel())
        assertThat(DummyShoppingCart.items).contains(
            ShoppingCartItem(
                product.id,
                product,
                1,
            ),
        )
    }
}
