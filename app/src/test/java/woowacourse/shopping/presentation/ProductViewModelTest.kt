package woowacourse.shopping.presentation

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.product.ProductViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductViewModelTest {
    private lateinit var repository: ProductRepository
    private lateinit var viewModel: ProductViewModel

    @BeforeEach
    fun setUp() {
        repository = mockk()
        every { repository.getPagedProducts(any(), any()) } returns
            List(10) {
                Product(
                    productId = it.toLong(),
                    imageUrl = "",
                    name = "Product $it",
                    _price = Price(1000),
                )
            }
        viewModel = ProductViewModel(repository)
    }

    @Test
    fun `fetchData 초기 호출 시 상품 10개를 반환한다`() {
        viewModel.fetchData()

        val products = viewModel.products.getOrAwaitValue()
        assertThat(products).hasSize(10)
    }

    @Test
    fun `loadMore 호출 시 상품 10개가 추가된다`() {
        viewModel.fetchData()
        viewModel.loadMore()

        val products = viewModel.products.getOrAwaitValue()
        assertThat(products).hasSize(20)
    }

    @Test
    fun `모든 데이터를 불러오지 않으면 더보기 버튼은 true가 된다`() {
        viewModel.fetchData()
        repeat(3) { viewModel.loadMore() }

        val showLoadMore = viewModel.showLoadMore.getOrAwaitValue()
        assertThat(showLoadMore).isTrue()
    }

    @Test
    fun `모든 데이터를 불러오면 더보기 버튼은 false가 된다`() {
        viewModel.fetchData()
        repeat(4) { viewModel.loadMore() }

        val showLoadMore = viewModel.showLoadMore.getOrAwaitValue()
        assertThat(showLoadMore).isFalse()
    }
}
