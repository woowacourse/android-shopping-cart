package woowacourse.shopping.presentation.detail.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.fake.FakeCartRepository
import woowacourse.shopping.fake.FakeProductRepository
import woowacourse.shopping.fake.FakeRecentProductRepository
import woowacourse.shopping.fake.fakeProduct
import woowacourse.shopping.presentation.detail.model.DetailUiState

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {
    private val dispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: DetailViewModel
    private lateinit var productRepository: FakeProductRepository
    private lateinit var cartRepository: FakeCartRepository
    private lateinit var recentProductRepository: FakeRecentProductRepository

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        val products = (1L..5L).map { fakeProduct(it) }
        productRepository = FakeProductRepository(products)
        cartRepository = FakeCartRepository()
        recentProductRepository = FakeRecentProductRepository(products)
        viewModel =
            DetailViewModel(
                productRepository = productRepository,
                cartRepository = cartRepository,
                recentProductRepository = recentProductRepository,
            )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadProduct는 상품 정보를 uiState에 업데이트한다`() =
        runTest {
            viewModel.loadProduct(id = 1L, isFromLastSeen = false)

            val state = viewModel.uiState.value

            assertThat(state).isInstanceOf(DetailUiState.Success::class.java)
            if (state is DetailUiState.Success) assertThat(state.product.id).isEqualTo(1L)
        }

    @Test
    fun `loadProduct는 장바구니에 담긴 상품 수량과 관계 없이 수량을 1 반환한다`() =
        runTest {
            cartRepository.addItem(1L, 3)

            viewModel.loadProduct(id = 1L, isFromLastSeen = false)
            val state = viewModel.uiState.value

            assertThat(state).isInstanceOf(DetailUiState.Success::class.java)
            if (state is DetailUiState.Success) assertThat(state.quantity).isEqualTo(1)
        }

    @Test
    fun `increase는 quantity를 1 증가시킨다`() {
        viewModel.loadProduct(1L, false)
        val state = viewModel.uiState.value
        assertThat(state).isInstanceOf(DetailUiState.Success::class.java)
        if (state is DetailUiState.Success) {
            val initial = state.quantity
            viewModel.increase()

            val updatedState = viewModel.uiState.value as DetailUiState.Success
            assertThat(updatedState.quantity).isEqualTo(initial + 1)
        }
    }

    @Test
    fun `decrease는 quantity가 1보다 크면 1 감소시킨다`() {
        viewModel.loadProduct(1L, false)
        viewModel.increase()
        viewModel.increase()

        viewModel.decrease()

        val state = viewModel.uiState.value
        assertThat(state).isInstanceOf(DetailUiState.Success::class.java)
        if (state is DetailUiState.Success) assertThat(state.quantity).isEqualTo(2)
    }

    @Test
    fun `decrease는 quantity가 1이면 감소시키지 않는다`() {
        viewModel.loadProduct(1L, false)
        viewModel.decrease()

        val state = viewModel.uiState.value
        assertThat(state).isInstanceOf(DetailUiState.Success::class.java)
        if (state is DetailUiState.Success) assertThat(state.quantity).isEqualTo(1)
    }

    @Test
    fun `addToCart는 cartRepository에 상품을 추가한다`() =
        runTest {
            viewModel.addToCart(id = 1L, quantity = 3)

            assertThat(cartRepository.getQuantity(1L)).isEqualTo(3)
        }
}
