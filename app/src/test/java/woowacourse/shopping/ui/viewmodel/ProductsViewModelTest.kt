package woowacourse.shopping.ui.viewmodel

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.presentation.viewmodel.products.ProductsViewModel
import woowacourse.shopping.util.InstantTaskExecutorExtension
import woowacourse.shopping.util.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductsViewModelTest {
    private lateinit var viewModel: ProductsViewModel

    @BeforeEach
    fun setup() {
        viewModel = ProductsViewModel(FakeProductRepository())
    }

    @Test
    fun `새로운 상품 리스트가 products에 추가된다`() {
        // when
        viewModel.updateProducts()

        // then
        val actual = viewModel.products.getOrAwaitValue()
        Assertions.assertTrue(actual.isNotEmpty())
    }

    @Test
    fun `isLoadable 값이 설정된다`() {
        // given
        viewModel.updateProducts(Int.MAX_VALUE)

        // when
        viewModel.updateIsLoadable()

        // then
        val actual = viewModel.isLoadingProducts.getOrAwaitValue()
        Assertions.assertFalse(actual)
    }
}
