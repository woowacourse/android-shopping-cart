package woowacourse.shopping.ui.viewmodel

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.ui.products.ProductsViewModel
import woowacourse.shopping.util.InstantTaskExecutorExtension
import woowacourse.shopping.util.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class CatalogProductsViewModelTest {
    private lateinit var viewModel: ProductsViewModel

    @BeforeEach
    fun setup() {
        viewModel = ProductsViewModel()
    }

    @Test
    fun `새로운 상품 리스트가 products에 추가된다`() {
        // when
        viewModel.loadCartProducts()

        // then
        val actual = viewModel.catalogProducts.getOrAwaitValue()
        Assertions.assertTrue(actual.isNotEmpty())
    }

    @Test
    fun `hasMoreProducts 값이 설정된다`() {
        // given
        viewModel.loadCartProducts(Int.MAX_VALUE)

        // when
        viewModel.loadHasMoreProducts()

        // then
        val actual = viewModel.hasMoreProducts.getOrAwaitValue()
        Assertions.assertFalse(actual)
    }
}
