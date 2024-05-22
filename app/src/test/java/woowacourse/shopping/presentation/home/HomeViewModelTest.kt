package woowacourse.shopping.presentation.home

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.model.Product
import woowacourse.shopping.domain.repository.FakeCartRepository
import woowacourse.shopping.domain.repository.FakeProductRepository
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.presentation.dummy.DummyCartItems
import woowacourse.shopping.presentation.dummy.DummyProducts
import woowacourse.shopping.presentation.home.viewmodel.HomeViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class HomeViewModelTest {
    private lateinit var homeViewModel: HomeViewModel

    @BeforeEach
    fun setup() {
        homeViewModel =
            HomeViewModel(
                FakeProductRepository(DummyProducts().products),
                FakeCartRepository(DummyCartItems().cartItems),
            )
    }

    @Test
    fun `장바구니 총 갯수를 제공한다`() {
        homeViewModel.loadTotalCartCount()

        val actualResult = homeViewModel.totalCartCount.getOrAwaitValue()

        assertThat(actualResult).isEqualTo(6)
    }

    @Test
    fun `장바구니에 아이템을 추가한다`() {
        homeViewModel.addCartItem(7)

        val actualResult = homeViewModel.totalCartCount.getOrAwaitValue()

        assertThat(actualResult).isEqualTo(7)
    }

    @Test
    fun `첫 페이지의 상품 데이터를 제공한다`() {
        homeViewModel.loadProducts()

        val actualResult = homeViewModel.products.getOrAwaitValue()

        assertThat(actualResult).isEqualTo(
            listOf(
                Product(1, "Product 1", "", 1000),
                Product(2, "Product 2", "", 2000),
                Product(3, "Product 3", "", 3000),
                Product(4, "Product 4", "", 4000),
                Product(5, "Product 5", "", 5000),
                Product(6, "Product 6", "", 6000),
                Product(7, "Product 7", "", 7000),
            ),
        )
    }
}
