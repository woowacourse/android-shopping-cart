package woowacourse.shopping.viewmodel

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.DummyProductRepository
import woowacourse.shopping.DummyShoppingRepository
import woowacourse.shopping.productlist.LoadProductState
import woowacourse.shopping.productlist.ProductListViewModel
import woowacourse.shopping.productlist.toProductUiModel
import woowacourse.shopping.viewmodel.fixtures.InstantTaskExecutorExtension
import woowacourse.shopping.viewmodel.fixtures.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductListViewModelTest {
    private lateinit var viewModel: ProductListViewModel

    @BeforeEach
    fun setUp() {
        viewModel = ProductListViewModel(DummyProductRepository, DummyShoppingRepository)
    }

    @Test
    fun `0부터 19의 위치에 해당하는 데이터를 로드한다`() {
        // when
        viewModel.loadProducts()

        // given
        val actual = viewModel.loadState.getOrAwaitValue() as LoadProductState.ShowProducts

        // then
        assertThat(actual.result.products).containsAll(
            DummyProductRepository.products(0, 20).map { it.toProductUiModel(0) },
        )
        assertThat(actual.result.products.last()).isEqualTo(
            DummyProductRepository.productById(19).toProductUiModel(0),
        )
    }

    @Test
    fun `0부터 19의 위치에 해당하는 데이터에, 20에서 39위치에 해당하는 데이터가 추가되는 형태로 데이터가 들어온다`() {
        // given
        viewModel.loadProducts()
        val firstActual = (viewModel.loadState.getOrAwaitValue() as LoadProductState.ShowProducts).result.products

        viewModel.loadProducts()
        val secondActual = (viewModel.loadState.getOrAwaitValue() as LoadProductState.ShowProducts).result.products

        // then
        assertThat(firstActual).containsAll(secondActual.subList(0, 19))
        assertThat(firstActual).doesNotContainAnyElementsOf(secondActual.subList(20, 40))
    }

    @Test
    fun `데이터를 로드할 때 마다, 데이터의 개수가 20개씩 추가된다`() {
        // when
        viewModel.loadProducts()

        // given
        val firstActual = viewModel.loadState.getOrAwaitValue() as LoadProductState.ShowProducts

        // then
        assertThat(firstActual.result.products).isEqualTo(20)

        // when
        viewModel.loadProducts()

        // given
        val secondActual = viewModel.loadState.getOrAwaitValue() as LoadProductState.ShowProducts

        // then
        assertThat(secondActual.result.products).isEqualTo(40)
    }
}
