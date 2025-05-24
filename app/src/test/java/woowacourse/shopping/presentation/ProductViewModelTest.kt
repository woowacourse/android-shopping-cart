package woowacourse.shopping.presentation

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.Fixture
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.presentation.product.ProductViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductViewModelTest {
    private lateinit var cartRepository: CartRepository
    private lateinit var productRepository: ProductRepository
    private lateinit var recentProductRepository: RecentProductRepository
    private lateinit var viewModel: ProductViewModel

    @BeforeEach
    fun setUp() {
        cartRepository = mockk(relaxed = true)
        productRepository = mockk(relaxed = true)
        recentProductRepository = mockk(relaxed = true)

        every { productRepository.fetchPagingProducts(any(), any(), any()) } answers {
            val callback = thirdArg<(Result<List<CartItem>>) -> Unit>()
            callback(Result.success(Fixture.mockedCartItems))
        }

        viewModel = ProductViewModel(cartRepository, productRepository, recentProductRepository)
    }

    @Test
    fun `fetchData 초기 호출 시 상품 10개를 반환한다`() {
        viewModel.fetchData(0)

        val products = viewModel.products.getOrAwaitValue()
        val data = (products as ResultState.Success).data
        assertThat(data).hasSize(10)
    }

    @Test
    fun `loadMore 호출 시 상품 10개가 추가된다`() {
        viewModel.fetchData(0)
        viewModel.loadMore()

        val products = viewModel.products.getOrAwaitValue()
        val data = (products as ResultState.Success).data
        assertThat(data).hasSize(20)
    }

    @Test
    fun `모든 데이터를 불러오지 않으면 더보기 버튼은 true가 된다`() {
        viewModel.fetchData(0)
        repeat(3) { viewModel.loadMore() }

        val showLoadMore = viewModel.showLoadMore.getOrAwaitValue()
        assertThat(showLoadMore).isTrue()
    }

    @Test
    fun `모든 데이터를 불러오면 더보기 버튼은 false가 된다`() {
        viewModel.fetchData(0)
        repeat(4) { viewModel.loadMore() }

        val showLoadMore = viewModel.showLoadMore.getOrAwaitValue()
        assertThat(showLoadMore).isFalse()
    }
}
