package woowacourse.shopping.presentation.shopping

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import woowacourse.shopping.data.ProductFixture
import woowacourse.shopping.data.network.FakeNetworkMonitor
import woowacourse.shopping.data.repository.FakeCartRepository
import woowacourse.shopping.data.repository.FakeProductRepository
import woowacourse.shopping.data.repository.FakeRecentlyViewedProductRepository
import woowacourse.shopping.domain.model.cart.Cart
import woowacourse.shopping.domain.model.product.Products
import woowacourse.shopping.domain.model.product.RecentlyViewedProducts
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.presentation.MainDispatcherRule

@OptIn(ExperimentalCoroutinesApi::class)
class ProductListViewModelTest {
    @JvmField
    @RegisterExtension
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `초기화 시 첫 페이지 상품을 불러온다`() =
        runTest {
            val viewModel = createViewModel()

            advanceUntilIdle()

            assertThat(viewModel.uiState.value.products.productItems)
                .containsExactlyElementsOf(ProductFixture.productList.take(20))
        }

    @Test
    fun `더보기를 누르면 다음 페이지 상품을 추가로 불러온다`() =
        runTest {
            val viewModel = createViewModel()

            advanceUntilIdle()
            viewModel.loadMore()
            advanceUntilIdle()

            assertThat(viewModel.uiState.value.currentPageIndex).isEqualTo(1)
            assertThat(viewModel.uiState.value.products.productItems)
                .containsExactlyElementsOf(ProductFixture.productList)
        }

    @Test
    fun `상품 수량을 증가시키면 장바구니에 상품이 추가된다`() =
        runTest {
            val viewModel =
                createViewModel(
                    cartRepository =
                        FakeCartRepository(
                            productRepository =
                                FakeProductRepository(
                                    products = Products(ProductFixture.productList),
                                ),
                        ),
                )
            val product = ProductFixture.productList.first()

            advanceUntilIdle()
            viewModel.increaseQuantity(product.productId)
            advanceUntilIdle()

            val cartItem =
                viewModel.uiState.value.cart.cartItems
                    .first()

            assertThat(cartItem.product.productId).isEqualTo(product.productId)
            assertThat(cartItem.quantity).isEqualTo(1)
        }

    @Test
    fun `상품 수량을 감소시키면 장바구니 수량이 감소한다`() =
        runTest {
            val product = ProductFixture.productList.first()
            val cartRepository =
                FakeCartRepository(
                    cart = Cart().increaseQuantity(product, 2),
                )
            val viewModel =
                createViewModel(
                    cartRepository = cartRepository,
                )

            advanceUntilIdle()
            viewModel.decreaseQuantity(product.productId)
            advanceUntilIdle()

            val cartItem =
                viewModel.uiState.value.cart.cartItems
                    .first()

            assertThat(cartItem.quantity).isEqualTo(1)
        }

    @Test
    fun `최근 본 상품을 새로고침하면 최근 본 상품 목록이 반영된다`() =
        runTest {
            val recentlyViewedProducts =
                Products(
                    listOf(
                        ProductFixture.productList[1],
                        ProductFixture.productList[0],
                    ),
                )

            val viewModel =
                createViewModel(
                    recentlyViewedProductRepository =
                        FakeRecentlyViewedProductRepository(
                            recentlyViewedProducts =
                                RecentlyViewedProducts(
                                    recentlyViewedProducts.productItems,
                                ),
                        ),
                )

            advanceUntilIdle()
            viewModel.refreshRecentlyViewedProducts()
            advanceUntilIdle()

            assertThat(viewModel.uiState.value.recentlyViewedProducts.productItems)
                .containsExactly(
                    ProductFixture.productList[1],
                    ProductFixture.productList[0],
                )
        }

    private fun createViewModel(
        cartRepository: CartRepository? = null,
        recentlyViewedProductRepository: FakeRecentlyViewedProductRepository = FakeRecentlyViewedProductRepository(),
    ): ProductListViewModel {
        val productRepository =
            FakeProductRepository(
                products = Products(ProductFixture.productList),
            )
        return ProductListViewModel(
            productRepository =
                FakeProductRepository(
                    products = Products(ProductFixture.productList),
                ),
            cartRepository =
                cartRepository ?: FakeCartRepository(
                    cart = Cart(),
                    productRepository = productRepository,
                ),
            recentlyViewedProductRepository = recentlyViewedProductRepository,
            networkMonitor = FakeNetworkMonitor(),
        )
    }
}
