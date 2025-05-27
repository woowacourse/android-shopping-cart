package woowacourse.shopping.view.detail

import android.view.View
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

    @Test
    fun 최근_본_상품을_추가할_수있다() {
        val product = TestProducts.productUiModels[0]
        val viewModel =
            ProductDetailViewModel(
                FakeShoppingCartRepository(),
                FakeRecentProductRepository(),
            )
        viewModel.addRecentProduct()
        assertThat(DummyShoppingCart.items).contains(
            ShoppingCartItem(
                product.id,
                product,
                1,
            ),
        )
    }

    @Test
    fun 현재_상품을_set_할수_있다() {
        val product = TestProducts.productUiModels[0]
        val viewModel =
            ProductDetailViewModel(
                FakeShoppingCartRepository(),
                FakeRecentProductRepository(),
            )
        viewModel.setProduct(product.toProductUiModel())
        assertThat(viewModel.productUiModelLiveData.value).isEqualTo(product.toProductUiModel())
    }

    @Test
    fun 현재_상품이_최근_본_상품인지_확인_할_수있다() {
        val product = TestProducts.productUiModels[0]
        val viewModel =
            ProductDetailViewModel(
                FakeShoppingCartRepository(),
                FakeRecentProductRepository(),
            )
        viewModel.setProduct(product.toProductUiModel())
        assertThat(viewModel.isInRecentProducts()).isEqualTo(View.GONE)
    }

    @Test
    fun 마지막으로_본_상품의_이름을_확인할_수_있다() {
        val product = TestProducts.productUiModels[0]
        val viewModel =
            ProductDetailViewModel(
                FakeShoppingCartRepository(),
                FakeRecentProductRepository(),
            )
        viewModel.setProduct(product.toProductUiModel())
        viewModel.addRecentProduct()
        assertThat(viewModel.lastViewedProductName()).isEqualTo(product.name)
    }

    @Test
    fun 현재_상품의_수량을_증가시킬_수_있다() {
        val product = TestProducts.productUiModels[0]
        val viewModel =
            ProductDetailViewModel(
                FakeShoppingCartRepository(),
                FakeRecentProductRepository(),
            )
        viewModel.setProduct(product.toProductUiModel())
        viewModel.increaseCount()
        assertThat(viewModel.quantityLiveData.value).isEqualTo(2)
    }

    @Test
    fun 현재_상품의_수량을_감소시킬_수_있다() {
        val product = TestProducts.productUiModels[0]
        val viewModel =
            ProductDetailViewModel(
                FakeShoppingCartRepository(),
                FakeRecentProductRepository(),
            )
        viewModel.setProduct(product.toProductUiModel())
        viewModel.increaseCount()
        viewModel.decreaseCount()
        assertThat(viewModel.quantityLiveData.value).isEqualTo(1)
    }
}
