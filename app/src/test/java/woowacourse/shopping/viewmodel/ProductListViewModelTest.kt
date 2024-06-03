package woowacourse.shopping.viewmodel

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.productlist.ProductListViewModel
import woowacourse.shopping.productlist.toProductUiModel
import woowacourse.shopping.productlist.uimodel.ProductChangeEvent
import woowacourse.shopping.repository.DummyProductRepository
import woowacourse.shopping.repository.DummyShoppingRepository
import woowacourse.shopping.viewmodel.fixtures.InstantTaskExecutorExtension
import woowacourse.shopping.viewmodel.fixtures.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductListViewModelTest {
    private lateinit var viewModel: ProductListViewModel

    @BeforeEach
    fun setUp() {
        viewModel = ProductListViewModel(DummyProductRepository, DummyShoppingRepository)
        DummyShoppingRepository.clear()
    }

    @Test
    fun `0부터 19의 위치에 해당하는 데이터를 로드한다`() {
        // when
        viewModel.initProducts()

        // given
        val actual = viewModel.productChangeEvent.getOrAwaitValue() as ProductChangeEvent.ShowProducts

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
        viewModel.initProducts()
        val firstActual =
            (viewModel.productChangeEvent.getOrAwaitValue() as ProductChangeEvent.ShowProducts).result.products

        viewModel.loadMoreProducts()
        val secondActual =
            (viewModel.productChangeEvent.getOrAwaitValue() as ProductChangeEvent.ShowProducts).result.products

        // then
        assertThat(firstActual).hasSize(20)
        assertThat(secondActual).hasSize(20)
        assertThat(firstActual).doesNotContainAnyElementsOf(secondActual)
    }

    @Test
    fun `데이터를 로드할 때 마다, 데이터의 개수가 20개씩 추가된다`() {
        // when
        viewModel.initProducts()

        // given
        val firstActual = viewModel.productChangeEvent.getOrAwaitValue() as ProductChangeEvent.ShowProducts

        // then
        assertThat(firstActual.result.products).hasSize(20)

        // when
        viewModel.loadMoreProducts()

        // given
        val secondActual = viewModel.productChangeEvent.getOrAwaitValue() as ProductChangeEvent.ShowProducts

        // then
        assertThat(secondActual.result.products).hasSize(20)
    }
}
