package woowacourse.shopping.view.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.fixture.FakeProductsRepository
import woowacourse.shopping.fixture.TestProducts
import woowacourse.shopping.getOrAwaitValue

@Suppress("FunctionName")
class ProductsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val viewModel =
        ProductsViewModel(FakeProductsRepository())

    @Test
    fun 한_페이지에_상품이_20개씩_로드된다() {
        val page =
            Page(
                TestProducts.productUiModels.subList(0, 20),
                TestProducts.productUiModels.size,
                0,
                20,
            )
        viewModel.requestProductsPage(0)
        assertThat(viewModel.productsLiveData.getOrAwaitValue()).isEqualTo(page)
    }

    @Test
    fun 상품의_총_개수를_반환한다() {
        assertThat(viewModel.totalSize).isEqualTo(DummyProducts.productUiModels.size)
    }
}
