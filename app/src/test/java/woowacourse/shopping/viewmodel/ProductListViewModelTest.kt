package woowacourse.shopping.viewmodel

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.DummyProductRepository
import woowacourse.shopping.UserShoppingCartRepository
import woowacourse.shopping.productlist.ProductListViewModel
import woowacourse.shopping.uimodel.toProductUiModel
import woowacourse.shopping.viewmodel.fixtures.InstantTaskExecutorExtension
import woowacourse.shopping.viewmodel.fixtures.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductListViewModelTest {
    private lateinit var viewModel: ProductListViewModel

    @BeforeEach
    fun setUp() {
        viewModel = ProductListViewModel(DummyProductRepository, UserShoppingCartRepository)
    }

    @Test
    fun `0부터 19의 위치에 해당하는 데이터를 로드한다`() {
        // given
        val actual = viewModel.productUiModels.getOrAwaitValue()

        // then
        assertThat(actual).containsAll(
            DummyProductRepository.products(0, 20).map {
                it.toProductUiModel()
            }
        )
        assertThat(actual.last()).isEqualTo(
            DummyProductRepository.productById(19).toProductUiModel()
        )
    }

    @Test
    fun `0부터 19의 위치에 해당하는 데이터에, 20에서 39위치에 해당하는 데이터가 추가되는 형태로 데이터가 들어온다`() {
        // when
        val firstPosition = 0
        val secondPosition = 20

        // given
        val firstActual = viewModel.productUiModels.getOrAwaitValue()

        viewModel.loadProducts(secondPosition)
        val secondActual = viewModel.productUiModels.getOrAwaitValue()

        // then
        assertThat(firstActual).containsAll(secondActual.subList(firstPosition, 19))
        assertThat(firstActual).doesNotContainAnyElementsOf(
            secondActual.subList(
                secondPosition,
                40
            )
        )
    }

    @Test
    fun `데이터를 로드할 때 마다, 데이터의 개수가 20개씩 추가된다`() {
        // given
        val firstActual = viewModel.productUiModels.getOrAwaitValue()

        // then
        assertThat(firstActual.size).isEqualTo(20)

        // when
        viewModel.loadProducts(20)

        // given
        val secondActual = viewModel.productUiModels.getOrAwaitValue()

        // then
        assertThat(secondActual.size).isEqualTo(40)
    }

    @Test
    fun `잘못된 값이 들어올 경우, 예외를 발생시킨다`() {
        assertThrows<Exception> {
            viewModel.loadProducts(-1)
        }
    }
}
