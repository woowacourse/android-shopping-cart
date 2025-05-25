@file:Suppress("ktlint")

package woowacourse.shopping.view.shoppingcart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.view.uimodel.ProductUiModel
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.fixture.FakeShoppingCartRepository
import woowacourse.shopping.fixture.TestProducts
import woowacourse.shopping.fixture.TestShoppingCart
import woowacourse.shopping.mapper.toProductUiModel
import woowacourse.shopping.mapper.toShoppingCartItemUiModel
import woowacourse.shopping.view.uimodel.ShoppingCartItemUiModel

class ShoppingCartViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun 한_페이지에_장바구니_상품이_5개씩_로드된다() {
        val viewModel = ShoppingCartViewModel(
            FakeShoppingCartRepository(
                TestShoppingCart.items.toMutableList()
            )
        )
        val page =
            Page(
                TestShoppingCart.items.subList(0,5).map { it.toShoppingCartItemUiModel()},
                TestShoppingCart.items.size,
                0,
                5,
            )
        viewModel.requestProductsPage(0)
        assertThat(viewModel.productsLiveData.getOrAwaitValue().page).isEqualTo(page)
    }

    @Test
    fun 장바구니에서_상품을_삭제할_수_있다() {
        val viewModel = ShoppingCartViewModel(
            FakeShoppingCartRepository(
                TestShoppingCart.items.toMutableList()
            )
        )
        val productUiModel =
            ShoppingCartItemUiModel(
                1,
                TestProducts.productUiModels[0].toProductUiModel(),
                42000,
            )
        viewModel.removeProduct(productUiModel, 0)
        assertThat(viewModel.productsLiveData.getOrAwaitValue().page.items).doesNotContain(productUiModel)
    }

    @Test
    fun 장바구니에서_상품을_삭제하면_해당_상품이_있었던_페이지가_로드된다() {
        val viewModel = ShoppingCartViewModel(
            FakeShoppingCartRepository(
                TestShoppingCart.items.toMutableList()
            )
        )
        val productUiModel =
            ShoppingCartItemUiModel(
                1,
                TestProducts.productUiModels[0].toProductUiModel(),
                42000,
            )

        viewModel.removeProduct(productUiModel, 4)

        assertThat(viewModel.productsLiveData.getOrAwaitValue().page.currentPage).isEqualTo(4)
    }

}
