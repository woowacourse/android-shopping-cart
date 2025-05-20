package woowacourse.shopping.view.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.data.repository.ProductsRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.fixture.TestProducts
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.view.page.Page

@Suppress("FunctionName")
class ProductsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val viewModel =
        ProductsViewModel(
            object : ProductsRepository {
                override fun findAll(
                    offset: Int,
                    limit: Int,
                ): List<Product> {
                    return TestProducts.products.subList(0, 20)
                }

                override fun totalSize(): Int {
                    return TestProducts.products.size
                }
            },
        )

    @Test
    fun 한_페이지에_상품이_20개씩_로드된다() {
        val page =
            Page.from(
                TestProducts.products.subList(0, 20),
                TestProducts.products.size,
                0,
                20,
            )
        viewModel.requestProductsPage(0)
        assert(viewModel.productsLiveData.getOrAwaitValue() == page)
    }

    @Test
    fun 상품의_총_개수를_반환한다() {
        assert(viewModel.totalSize == DummyProducts.products.size)
    }
}
