package woowacourse.shopping.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.product.ProductSinglePage
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.ext.getOrAwaitValue
import woowacourse.shopping.fixture.productFixture1
import woowacourse.shopping.fixture.productFixture2
import woowacourse.shopping.fixture.productFixture3
import woowacourse.shopping.fixture.productFixture4
import woowacourse.shopping.view.main.state.LoadState
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

        every { productRepository.loadSinglePage(0, 20, any()) } answers {
            val callback = thirdArg<(ProductSinglePage) -> Unit>()
            callback(ProductSinglePage(products, true))
        }

        products.forEach {
            every { cartRepository[it.id] } returns Cart(Quantity(1), it.id)
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
    fun `상품 목록을 불러오면 UI 상태에 반영된다`() {
        val result = viewModel.uiState.getOrAwaitValue()

        assertAll(
            { Assertions.assertThat(result.items).hasSize(4) },
            { Assertions.assertThat(result.load).isInstanceOf(LoadState.CanLoad::class.java) },
            {
                Assertions.assertThat(result.items.map { it.item.id })
                    .containsExactly(1L, 2L, 3L, 4L)
            },
        )
    }

    @Test
    fun `장바구니에 담긴 상품의 수량이 증가하면 UI 상태도 증가한다`() {
        val product = viewModel.uiState.getOrAwaitValue().items.first()

        viewModel.increaseCartQuantity(product.item.id)

        val updated = viewModel.uiState.getOrAwaitValue().items.first()
        Assertions.assertThat(updated.cartQuantity.value).isEqualTo(product.cartQuantity.value + 1)
    }
}
