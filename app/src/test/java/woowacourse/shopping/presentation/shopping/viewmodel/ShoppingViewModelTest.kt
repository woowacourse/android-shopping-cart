package woowacourse.shopping.presentation.shopping.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.fake.FakeCartRepository
import woowacourse.shopping.fake.FakeProductRepository
import woowacourse.shopping.fake.FakeRecentProductRepository
import woowacourse.shopping.fake.fakeProduct

@OptIn(ExperimentalCoroutinesApi::class)
class ShoppingViewModelTest {
    private val dispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: ShoppingViewModel
    private lateinit var productRepository: FakeProductRepository
    private lateinit var cartRepository: FakeCartRepository
    private lateinit var recentProductRepository: FakeRecentProductRepository

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        val products = (1L..30L).map { fakeProduct(it) }
        productRepository = FakeProductRepository(products)
        cartRepository = FakeCartRepository()
        recentProductRepository = FakeRecentProductRepository(products)
        viewModel =
            ShoppingViewModel(
                productRepository = productRepository,
                cartRepository = cartRepository,
                recentProductRepository = recentProductRepository,
            )
    }

    @Test
    fun `데이터를 불러올 때 제한된 개수의 상품 데이터를 불러온다`() =
        runTest {
            viewModel.loadMore()
            advanceUntilIdle()

            val state = viewModel.uiState.value
            Assertions.assertThat(state.products.size).isEqualTo(20)
            Assertions.assertThat(state.offset).isEqualTo(20)
        }

    @Test
    fun `데이터를 처음 불러오고 나서 offset은 처음 불러온 데이터의 크기가 된다`() =
        runTest {
            viewModel.loadMore()
            advanceUntilIdle()

            val state = viewModel.uiState.value
            Assertions.assertThat(state.offset).isEqualTo(20)
        }

    @Test
    fun `상품 데이터를 불러온 뒤 아직 불러오지 않은 상품이 있다면 canLoadMore는 true를 반환한다`() =
        runTest {
            viewModel.loadMore()
            advanceUntilIdle()

            val state = viewModel.uiState.value
            Assertions.assertThat(state.canLoadMore).isTrue
        }

    @Test
    fun `상품 데이터를 불러온 뒤 아직 불러오지 않은 상품이 없다면 canLoadMore는 false를 반환한다`() =
        runTest {
            viewModel.loadMore()
            advanceUntilIdle()
            viewModel.loadMore()
            advanceUntilIdle()

            val state = viewModel.uiState.value
            Assertions.assertThat(state.canLoadMore).isFalse
        }

    @Test
    fun `상품 1개를 장바구니에 담으면 장바구니의 담긴 상품의 수량이 1이 된다`() =
        runTest {
            viewModel.loadMore()
            viewModel.increase(1L)

            val state = viewModel.uiState.value
            val item = state.products.first { it.product.id == 1L }
            Assertions.assertThat(item.quantity).isEqualTo(1)
        }

    @Test
    fun `5개의 상품을 각각 1개씩 장바구니에 담으면 장바구니 총 상품 수량은 5가 된다`() =
        runTest {
            viewModel.loadMore()
            viewModel.increase(1L)
            viewModel.increase(2L)
            viewModel.increase(3L)
            viewModel.increase(4L)
            viewModel.increase(5L)

            val state = viewModel.uiState.value
            Assertions.assertThat(state.totalQuantity).isEqualTo(5)
        }

    @Test
    fun `장바구니에 담긴 1개의 상품의 수량을 1개 감소시키면 수량이 0이 된다`() =
        runTest {
            viewModel.loadMore()
            viewModel.increase(1L)

            val state = viewModel.uiState.value
            val itemB = state.products.first { it.product.id == 1L }
            Assertions.assertThat(itemB.quantity).isEqualTo(1)
            viewModel.decrease(1L)

            val updatedState = viewModel.uiState.value
            val updatedItem = updatedState.products.first { it.product.id == 1L }
            Assertions.assertThat(updatedItem.quantity).isEqualTo(0)
        }
}
