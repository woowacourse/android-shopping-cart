package woowacourse.shopping.view.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.view.page.Page

@Suppress("FunctionName")
class ProductsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun 한_페이지에_상품이_20개씩_로드된다() {
        val viewModel = ProductsViewModel()
        val page =
            Page.from(
                DummyProducts.products,
                0,
                20,
            )
        viewModel.requestProductsPage(0)
        assert(viewModel.productsLiveData.getOrAwaitValue() == page)
    }

    @Test
    fun 상품의_총_개수를_반환한다() {
        val viewModel = ProductsViewModel()
        assert(viewModel.totalSize == DummyProducts.products.size)
    }
}
