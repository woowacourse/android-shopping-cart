package woowacourse.shopping.presentation.home

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.model.Product
import woowacourse.shopping.domain.repository.FakeProductRepository
import woowacourse.shopping.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class HomeViewModelTest {
    private lateinit var homeViewModel: HomeViewModel

    @BeforeEach
    fun setup() {
        homeViewModel = HomeViewModel(FakeProductRepository())
    }

    @Test
    fun `첫 페이지의 상품 데이터를 제공한다`() {
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
                Product(8, "Product 8", "", 8000),
                Product(9, "Product 9", "", 9000),
                Product(10, "Product 10", "", 10000),
                Product(11, "Product 11", "", 11000),
                Product(12, "Product 12", "", 12000),
                Product(13, "Product 13", "", 13000),
                Product(14, "Product 14", "", 14000),
                Product(15, "Product 15", "", 15000),
                Product(16, "Product 16", "", 16000),
                Product(17, "Product 17", "", 17000),
                Product(18, "Product 18", "", 18000),
                Product(19, "Product 19", "", 19000),
                Product(20, "Product 20", "", 20000),
            ),
        )
    }

    @Test
    fun `더보기 작업을 수행하면 다음 페이지를 불러온다`() {
        homeViewModel.onLoadClick()
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
                Product(8, "Product 8", "", 8000),
                Product(9, "Product 9", "", 9000),
                Product(10, "Product 10", "", 10000),
                Product(11, "Product 11", "", 11000),
                Product(12, "Product 12", "", 12000),
                Product(13, "Product 13", "", 13000),
                Product(14, "Product 14", "", 14000),
                Product(15, "Product 15", "", 15000),
                Product(16, "Product 16", "", 16000),
                Product(17, "Product 17", "", 17000),
                Product(18, "Product 18", "", 18000),
                Product(19, "Product 19", "", 19000),
                Product(20, "Product 20", "", 20000),
                Product(21, "Product 21", "", 21000),
                Product(22, "Product 22", "", 22000),
                Product(23, "Product 23", "", 23000),
                Product(24, "Product 24", "", 24000),
                Product(25, "Product 25", "", 25000),
                Product(26, "Product 26", "", 26000),
                Product(27, "Product 27", "", 27000),
                Product(28, "Product 28", "", 28000),
                Product(29, "Product 29", "", 29000),
                Product(30, "Product 30", "", 30000),
                Product(31, "Product 31", "", 31000),
                Product(32, "Product 32", "", 32000),
                Product(33, "Product 33", "", 33000),
                Product(34, "Product 34", "", 34000),
                Product(35, "Product 35", "", 35000),
                Product(36, "Product 36", "", 36000),
                Product(37, "Product 37", "", 37000),
                Product(38, "Product 38", "", 38000),
                Product(39, "Product 39", "", 39000),
                Product(40, "Product 40", "", 40000),
            ),
        )
    }
}
