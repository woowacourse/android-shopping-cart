package woowacourse.shopping.presentation.productdetail

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import woowacourse.shopping.data.ProductFixture
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.domain.model.cart.Cart
import woowacourse.shopping.domain.model.product.Products
import woowacourse.shopping.presentation.MainDispatcherRule
import woowacourse.shopping.presentation.cart.FakeCartRepository
import woowacourse.shopping.presentation.shopping.FakeRecentlyViewedProductRepository
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class, ExperimentalCoroutinesApi::class)
class ProductDetailViewModelTest {
    @JvmField
    @RegisterExtension
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `수량을 증가시키면 선택 수량이 1 증가한다`() {
        val viewModel = createViewModel()

        viewModel.increaseQuantity()

        assertThat(viewModel.uiState.value.quantity).isEqualTo(2)
    }

    @Test
    fun `수량이 1보다 크면 수량을 감소시킨다`() {
        val viewModel = createViewModel()

        viewModel.increaseQuantity()
        viewModel.decreaseQuantity()

        assertThat(viewModel.uiState.value.quantity).isEqualTo(1)
    }

    @Test
    fun `장바구니에 상품을 추가하면 선택 수량만큼 장바구니에 담긴다`() =
        runTest {
            val cartRepository = FakeCartRepository()
            val viewModel = createViewModel(cartRepository = cartRepository)
            val product = ProductFixture.productList.first()

            viewModel.increaseQuantity()
            viewModel.increaseQuantity()

            viewModel.addToCart(product.productId)
            advanceUntilIdle()

            val cart = cartRepository.getItems()

            assertThat(cart.cartItems).hasSize(1)
            assertThat(
                cart.cartItems
                    .first()
                    .product.productId,
            )
        }

    @Test
    fun `장바구니에 상품을 추가하면 메시지 이벤트를 발행한다`() =
        runTest {
            val viewModel = createViewModel()
            val product = ProductFixture.productList.first()

            viewModel.addToCart(product.productId)
            advanceUntilIdle()

            assertThat(viewModel.uiEvent.first())
                .isEqualTo(ProductDetailUiEvent.ShowMessage("장바구니에 상품을 담았습니다"))
        }

    @Test
    fun `상품을 조회하면 최근 본 상품에 기록한다`() =
        runTest {
            val recentlyViewedProductRepository = FakeRecentlyViewedProductRepository()
            val viewModel = createViewModel(recentlyViewedPRepository = recentlyViewedProductRepository)
            val product = ProductFixture.productList.first()

            viewModel.viewProduct(product.productId)
            advanceUntilIdle()

            val recentlyViewedProducts = recentlyViewedProductRepository.getRecentlyViewedProducts()

            assertThat(recentlyViewedProducts.productItems).containsExactly(product)
        }

    private fun createViewModel(
        cartRepository: FakeCartRepository = FakeCartRepository(Cart()),
        recentlyViewedPRepository: FakeRecentlyViewedProductRepository = FakeRecentlyViewedProductRepository(),
    ): ProductDetailViewModel =
        ProductDetailViewModel(
            productRepository =
                ProductRepositoryImpl(
                    products = Products(ProductFixture.productList),
                ),
            cartRepository = cartRepository,
            recentlyViewedProductRepository = recentlyViewedPRepository,
        )
}
