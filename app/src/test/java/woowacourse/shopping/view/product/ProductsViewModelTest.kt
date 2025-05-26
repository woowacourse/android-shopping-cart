package woowacourse.shopping.view.product

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.product.repository.ProductsRepository
import woowacourse.shopping.view.InstantTaskExecutorExtension
import woowacourse.shopping.view.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductsViewModelTest {
    private lateinit var repository: ProductsRepository
    private lateinit var viewModel: ProductsViewModel

    @BeforeEach
    fun setUp() {
        repository = FakeProductsRepository()
        viewModel = ProductsViewModel(repository)
    }

    @Test
    fun `등록된 상품들을 20개씩 가져올 수 있다`() {
        // when
        viewModel.updateProducts()

        // then
        assertThat(
            viewModel.productItems.getOrAwaitValue().filterIsInstance<ProductsItem.ProductItem>(),
        ).hasSize(20)
    }

    @Test
    fun `가져온 상품들은 계속해서 쌓인다`() {
        // when
        viewModel.updateProducts()
        viewModel.updateProducts()

        // then
        assertThat(
            viewModel.productItems.getOrAwaitValue().filterIsInstance<ProductsItem.ProductItem>(),
        ).hasSize(40)
    }

    @Test
    fun `가져올 개수가 20개가 되지 않으면 남은 것을 다 가져온다`() {
        // when
        viewModel.updateProducts()
        viewModel.updateProducts()
        viewModel.updateProducts()

        // then
        assertThat(
            viewModel.productItems.getOrAwaitValue().filterIsInstance<ProductsItem.ProductItem>(),
        ).hasSize(50)
    }
}
