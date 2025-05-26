package woowacourse.shopping.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.FakeCartRepository
import woowacourse.shopping.data.FakeProductRepository
import woowacourse.shopping.data.FakeRecentProductRepository
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.ext.getOrAwaitValue
import woowacourse.shopping.fixture.productFixture1
import woowacourse.shopping.fixture.productFixture2
import woowacourse.shopping.fixture.productFixture3
import woowacourse.shopping.fixture.productFixture4
import woowacourse.shopping.view.main.vm.MainViewModel
import java.time.LocalDateTime

@ExtendWith(InstantTaskExecutorExtension::class)
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var fakeCartRepository: FakeCartRepository
    private lateinit var fakeProductRepository: FakeProductRepository
    private lateinit var fakeRecentProductRepository: FakeRecentProductRepository

    @BeforeEach
    fun setup() {
        fakeCartRepository = FakeCartRepository()
        fakeProductRepository = FakeProductRepository()
        fakeRecentProductRepository = FakeRecentProductRepository()
        viewModel =
            MainViewModel(fakeCartRepository, fakeProductRepository, fakeRecentProductRepository)
    }

    @Test
    fun `상품을 로드하면 carts LiveData가 갱신된다`() {
        // when
        viewModel.loadProducts()

        // then
        val carts = viewModel.carts.getOrAwaitValue()
        assertThat(carts).isNotEmpty()
    }

    @Test
    fun `최근 본 상품 로드 시 recentProducts LiveData가 갱신된다`() {
        // given
        fakeRecentProductRepository.insert(
            RecentProduct(Product(1L, "맥북", Price(1000), ""), LocalDateTime.of(2024, 5, 1, 12, 0)),
        ) {

        }

        // when
        viewModel.loadRecentProducts()

        // then
        val recentProducts = viewModel.recentProducts.getOrAwaitValue()
        assertThat(recentProducts).hasSize(7)
    }

    @Test
    fun `페이지 이동하면 loadProducts 호출`() {
        // when
        viewModel.moveNextPage()

        // then
        val carts = viewModel.carts.getOrAwaitValue()
        assertThat(carts).isNotEmpty()
    }
}
