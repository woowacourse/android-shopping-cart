package woowacourse.shopping.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.fixture.FakeCartItemRepository
import woowacourse.shopping.product.catalog.ProductUiModel
import woowacourse.shopping.util.InstantTaskExecutorExtension
import woowacourse.shopping.util.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CartViewModel

    @Test
    fun `초기에는 첫 페이지 상품만 보여준다`() {
        viewModel =
            CartViewModel(
                FakeCartItemRepository(0),
            )

        val products = viewModel.cartProducts.getOrAwaitValue()

        assertThat(products.size).isEqualTo(0)
        assertThat(viewModel.getPage()).isEqualTo(0)
    }

    @Test
    fun `상품 삭제 후 현재 페이지가 유효하지 않으면 이전 페이지로 이동한다`() {
        viewModel =
            CartViewModel(
                FakeCartItemRepository(11),
            )

        repeat(2) { viewModel.onNextPage() }
        assertThat(viewModel.getPage()).isEqualTo(2)

        val lastItem = viewModel.cartProducts.getOrAwaitValue().last()
        viewModel.onDeleteProduct(lastItem)

        assertThat(viewModel.getPage()).isEqualTo(1)
        assertThat(viewModel.cartProducts.getOrAwaitValue().size).isEqualTo(5)
    }

    @Test
    fun `다음 페이지 버튼은 다음 페이지가 있을 때만 활성화된다`() {
        viewModel =
            CartViewModel(
                FakeCartItemRepository(10),
            )
        assertThat(viewModel.isNextButtonEnabled()).isTrue

        repeat(2) { viewModel.onNextPage() }

        assertThat(viewModel.isNextButtonEnabled()).isFalse
    }

    @Test
    fun `이전 페이지 버튼은 첫 페이지가 아닐 때만 활성화된다`() {
        viewModel =
            CartViewModel(
                FakeCartItemRepository(10),
            )

        assertThat(viewModel.isPrevButtonEnabled()).isFalse

        viewModel.onNextPage()
        assertThat(viewModel.isPrevButtonEnabled()).isTrue
    }

    @Test
    fun `상품의 수량을 증가시킬 수 있다`() {
        val cartRepository = FakeCartItemRepository(1)
        val product =
            ProductUiModel(
                id = 1L,
                name = "아이스 카페 아메리카노",
                imageUrl = "https://example.com/image.jpg",
                price = 4000,
                quantity = 2,
            )

        cartRepository.insertCartItem(product) {}

        viewModel = CartViewModel(cartRepository)

        viewModel.increaseQuantity(product)

        val updated = viewModel.product.value
        assertThat(updated?.quantity).isEqualTo(3)
    }

    @Test
    fun `상품의 수량을 감소시킬 수 있다`() {
        val cartRepository = FakeCartItemRepository(1)
        val product =
            ProductUiModel(
                id = 1L,
                name = "아이스 카페 아메리카노",
                imageUrl = "https://example.com/image.jpg",
                price = 4000,
                quantity = 3,
            )

        cartRepository.insertCartItem(product) {}

        viewModel = CartViewModel(cartRepository)

        viewModel.decreaseQuantity(product)

        val updated = viewModel.product.getOrAwaitValue()
        assertThat(updated.quantity).isEqualTo(2)
    }

    @Test
    fun `상품 수량은 최소 1까지 감소 가능하다`() {
        val cartRepository = FakeCartItemRepository(1)
        val product =
            ProductUiModel(
                id = 1L,
                name = "아이스 카페 아메리카노",
                imageUrl = "https://example.com/image.jpg",
                price = 4000,
                quantity = 1,
            )

        cartRepository.insertCartItem(product) {}

        viewModel = CartViewModel(cartRepository)

        viewModel.decreaseQuantity(product)

        val updated = viewModel.product.getOrAwaitValue()
        assertThat(updated.quantity).isEqualTo(1)
    }
}
