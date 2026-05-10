package woowacourse.shopping.presentation.cart

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import woowacourse.shopping.domain.model.cart.Cart
import woowacourse.shopping.presentation.MainDispatcherRule
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {
    @JvmField
    @RegisterExtension
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `초기화 시 장바구니 첫 페이지를 불러온다`() =
        runTest {
            val repository = FakeCartRepository(Cart(CartFixture.cartItems))
            val viewModel = CartViewModel(repository)

            advanceUntilIdle()

            assertThat(viewModel.uiState.value.cart.cartItems).hasSize(5)
            assertThat(viewModel.uiState.value.totalItemCount).isEqualTo(7)
            assertThat(viewModel.uiState.value.currentPageIndex).isEqualTo(0)
        }

    @Test
    fun `다음 페이지로 이동하면 다음 장바구니 상품을 불러온다`() =
        runTest {
            val repository = FakeCartRepository(Cart(CartFixture.cartItems))
            val viewModel = CartViewModel(repository)

            advanceUntilIdle()

            viewModel.goToNextPage()
            advanceUntilIdle()

            assertThat(viewModel.uiState.value.currentPageIndex).isEqualTo(1)
            assertThat(viewModel.uiState.value.cart.cartItems).hasSize(2)
        }

    @Test
    fun `이전 페이지로 이동하면 이전 장바구니 상품을 불러온다`() =
        runTest {
            val repository = FakeCartRepository(Cart(CartFixture.cartItems))
            val viewModel = CartViewModel(repository)

            advanceUntilIdle()

            viewModel.goToNextPage()
            advanceUntilIdle()

            viewModel.goToPreviousPage()
            advanceUntilIdle()

            assertThat(viewModel.uiState.value.currentPageIndex).isEqualTo(0)
            assertThat(viewModel.uiState.value.cart.cartItems).hasSize(5)
        }

    @Test
    fun `상품 수량을 증가시키면 장바구니 수량이 증가한다`() =
        runTest {
            val repository = FakeCartRepository(Cart(CartFixture.cartItems))
            val viewModel = CartViewModel(repository)
            advanceUntilIdle()

            val product = CartFixture.cartItems.first().product

            viewModel.increaseQuantity(product)
            advanceUntilIdle()

            val updateItem =
                viewModel.uiState.value.cart.cartItems
                    .first { it.product.productId == product.productId }

            assertThat(updateItem.quantity).isEqualTo(2)
        }

    @Test
    fun `수량이 1인 상품을 감소시키면 삭제 확인 다이얼로그 상태가 설정된다`() =
        runTest {
            val repository = FakeCartRepository(Cart(CartFixture.cartItems))
            val viewModel = CartViewModel(repository)
            advanceUntilIdle()

            val productId =
                CartFixture.cartItems
                    .first()
                    .product.productId

            viewModel.decreaseQuantity(productId)

            assertThat(viewModel.uiState.value.deleteProductId).isEqualTo(productId)
        }

    @Test
    fun `상품을 삭제하면 장바구니에서 제거되고 메시지 이벤트를 발행한다`() =
        runTest {
            val repository = FakeCartRepository(Cart(CartFixture.cartItems))
            val viewModel = CartViewModel(repository)
            advanceUntilIdle()

            val productId =
                CartFixture.cartItems
                    .first()
                    .product.productId

            viewModel.deleteProduct(productId)
            advanceUntilIdle()

            assertThat(
                viewModel.uiState.value.cart.cartItems.none {
                    it.product.productId == productId
                },
            ).isTrue()

            assertThat(viewModel.uiEvent.first()).isEqualTo(CartUiEvent.ShowMessage("삭제되었습니다."))
        }

    @Test
    fun `삭제 다이얼로그를 닫으면 deleteProductId가 초기화된다`() =
        runTest {
            val repository = FakeCartRepository(Cart(CartFixture.cartItems))
            val viewModel = CartViewModel(repository)
            val productId =
                CartFixture.cartItems
                    .first()
                    .product.productId

            viewModel.showDeleteDialog(productId)
            viewModel.dismissDeleteDialog()

            assertThat(viewModel.uiState.value.deleteProductId).isNull()
        }
}
