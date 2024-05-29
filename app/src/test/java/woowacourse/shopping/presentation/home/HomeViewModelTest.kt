package woowacourse.shopping.presentation.home

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.model.Product
import woowacourse.shopping.domain.repository.FakeCartRepository
import woowacourse.shopping.domain.repository.FakeProductRepository
import woowacourse.shopping.domain.repository.FakeRecentRecentProductRepository
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.presentation.dummy.DummyCartItems
import woowacourse.shopping.presentation.dummy.DummyProductHistories
import woowacourse.shopping.presentation.dummy.DummyProducts
import woowacourse.shopping.presentation.home.viewmodel.HomeViewModel
import woowacourse.shopping.presentation.uistate.Order

@ExtendWith(InstantTaskExecutorExtension::class)
class HomeViewModelTest {
    private lateinit var homeViewModel: HomeViewModel

    @BeforeEach
    fun setup() {
        homeViewModel =
            HomeViewModel(
                FakeProductRepository(DummyProducts().products),
                FakeCartRepository(DummyCartItems().carts),
                FakeRecentRecentProductRepository(DummyProductHistories().productHistories),
            )
    }

    @Test
    fun `장바구니 총 갯수를 제공한다`() {
        val actualResult = homeViewModel.totalCartCount.getOrAwaitValue()

        assertThat(actualResult).isEqualTo(7)
    }

    @Test
    fun `장바구니에 아이템을 추가한다`() {
        homeViewModel.onAddCartItem(8)

        val actualResult = homeViewModel.totalCartCount.getOrAwaitValue()

        assertThat(actualResult).isEqualTo(8)
    }

    @Test
    fun `첫 페이지의 상품 데이터를 제공한다`() {
        val actualResult = homeViewModel.orders.getOrAwaitValue()

        assertThat(actualResult).isEqualTo(
            listOf(
                Order(1, 1, Product(1, "Product 1", "", 1000)),
                Order(2, 1, Product(2, "Product 2", "", 2000)),
                Order(3, 1, Product(3, "Product 3", "", 3000)),
                Order(4, 1, Product(4, "Product 4", "", 4000)),
                Order(5, 1, Product(5, "Product 5", "", 5000)),
                Order(6, 1, Product(6, "Product 6", "", 6000)),
                Order(7, 1, Product(7, "Product 7", "", 7000)),
            ),
        )
    }
}
