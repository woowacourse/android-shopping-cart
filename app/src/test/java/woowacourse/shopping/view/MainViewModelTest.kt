package woowacourse.shopping.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.product.ProductResult
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.ext.getOrAwaitValue
import woowacourse.shopping.fixture.productFixture1
import woowacourse.shopping.fixture.productFixture2
import woowacourse.shopping.fixture.productFixture3
import woowacourse.shopping.fixture.productFixture4
import woowacourse.shopping.view.main.vm.LoadState
import woowacourse.shopping.view.main.vm.MainViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var productRepository: ProductRepository
    private lateinit var cartRepository: CartRepository

    @BeforeEach
    fun setUp() {
        productRepository = mockk()
        cartRepository = mockk()

        val products =
            listOf(
                productFixture1,
                productFixture2,
                productFixture3,
                productFixture4,
            )

        every { productRepository.loadSinglePage(0, 20) } returns
            ProductResult(
                products = products,
                hasNextPage = true,
            )

        products.forEach {
            every { cartRepository[it.id] } returns Cart(it.id, Quantity(1), it.id)
        }

        viewModel = MainViewModel(productRepository, cartRepository)
    }

    @Test
    fun `저장소의 상품 목록을 가져온다`() {
        val result = viewModel.uiState.getOrAwaitValue()

        assertAll(
            { assertThat(result.items).hasSize(4) },
            { assertThat(result.load).isInstanceOf(LoadState.CanLoad::class.java) },
            { assertThat(result.items.map { it.item.id }).containsExactly(1L, 2L, 3L, 4L) },
        )
    }

    @Test
    fun `상품 하나의 수량이 증가한다`() {
        val original = viewModel.uiState.getOrAwaitValue().items.first()
        viewModel.increaseQuantity(original.item.id)

        val updated = viewModel.uiState.getOrAwaitValue().items.first()
        assertThat(updated.quantity.value).isEqualTo(original.quantity.value + 1)
    }

    @Test
    fun `상품 하나의 수량이 감소한다`() {
        val original = viewModel.uiState.getOrAwaitValue().items.first()
        viewModel.decreaseQuantity(original.item.id)

        val updated = viewModel.uiState.getOrAwaitValue().items.first()
        assertThat(updated.quantity.value).isEqualTo(original.quantity.value - 1)
    }
}
