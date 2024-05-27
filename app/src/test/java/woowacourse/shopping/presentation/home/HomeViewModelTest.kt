package woowacourse.shopping.presentation.home

import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.model.cart.CartItem
import woowacourse.shopping.data.model.history.ProductHistory
import woowacourse.shopping.data.model.history.RecentProduct
import woowacourse.shopping.data.model.product.Product
import woowacourse.shopping.domain.repository.FakeCartRepository
import woowacourse.shopping.domain.repository.FakeProductRepository
import woowacourse.shopping.domain.repository.cart.CartRepository
import woowacourse.shopping.fixture.getFixtureCartableProducts
import woowacourse.shopping.getOrAwaitValue
import kotlin.concurrent.thread

@ExtendWith(InstantTaskExecutorExtension::class)
class HomeViewModelTest {
    private lateinit var homeViewModel: HomeViewModel

    @BeforeEach
    fun setup() {
        homeViewModel =
            HomeViewModel(
                FakeProductRepository(),
                FakeCartRepository(),
            )
    }

    @Test
    fun `첫 페이지의 상품 데이터를 제공한다`() {
        val actualResult = homeViewModel.products.getOrAwaitValue()
        assertThat(actualResult).isEqualTo(getFixtureCartableProducts(20))
    }

    @Test
    fun `지정한 개수만큼 최근 본 상품들을 불러올 수 있다`() {
        thread {
            val productHistory = homeViewModel.productHistory.getOrAwaitValue()
            assertThat(productHistory).isEqualTo(
                listOf(
                    RecentProduct(ProductHistory(100, 100), Product(100, "사과100", "image100", 100000)),
                    RecentProduct(ProductHistory(99, 99), Product(99, "사과99", "image99", 99000)),
                    RecentProduct(ProductHistory(98, 98), Product(98, "사과98", "image98", 98000)),
                    RecentProduct(ProductHistory(97, 97), Product(97, "사과97", "image97", 97000)),
                    RecentProduct(ProductHistory(96, 96), Product(96, "사과96", "image96", 96000)),
                    RecentProduct(ProductHistory(95, 95), Product(95, "사과95", "image95", 95000)),
                    RecentProduct(ProductHistory(94, 94), Product(94, "사과94", "image94", 94000)),
                    RecentProduct(ProductHistory(93, 93), Product(93, "사과93", "image93", 93000)),
                    RecentProduct(ProductHistory(92, 92), Product(92, "사과92", "image92", 92000)),
                    RecentProduct(ProductHistory(91, 91), Product(91, "사과91", "image91", 91000)),
                ),
            )
        }.join()
    }

    @Test
    fun `장바구니에 담긴 상품의 총 개수를 불러올 수 있다`() {
        thread {
            val totalQuantity = homeViewModel.totalQuantity.getOrAwaitValue()
            assertThat(totalQuantity).isEqualTo(100)
        }.join()
    }

    @Test
    fun `다음 페이지의 상품 데이터를 불러올 수 있다`() {
        thread {
            homeViewModel.loadNextPage()
            val actualResult = homeViewModel.products.getOrAwaitValue()
            assertThat(actualResult).isEqualTo(getFixtureCartableProducts(40))
        }.join()
    }

    @Test
    fun `장바구니에 상품을 추가하지 않은 상태에서 수량을 변경시키면 상품을 장바구니에 추가한다`() {
        val cartRepository = mockk<CartRepository>(relaxed = true)
        val viewModel =
            HomeViewModel(
                FakeProductRepository(),
                cartRepository,
            )
        viewModel.onQuantityChange(12, 11)
        verify { cartRepository.addCartItem(CartItem(null, 12, 11)) }
    }

    @Test
    fun `장바구니에 상품을 추가한 상태에서 수량을 변경시키면 상품의 수량이 변경된다`() {
        val cartRepository = mockk<CartRepository>(relaxed = true)
        val viewModel =
            HomeViewModel(
                FakeProductRepository(),
                cartRepository,
            )
        viewModel.onQuantityChange(12, 1)
        viewModel.onQuantityChange(12, 11)
        verify { cartRepository.addCartItem(CartItem(null, 12, 11)) }
    }
}
