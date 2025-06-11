package woowacourse.shopping.view.product

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.product.repository.ProductsRepository
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.InstantTaskExecutorExtension
import woowacourse.shopping.view.getOrAwaitValue
import woowacourse.shopping.view.productDetail.ProductDetailViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    private lateinit var repository: ProductsRepository
    private lateinit var viewModel: ProductDetailViewModel

    @BeforeEach
    fun setUp() {
        repository = FakeProductsRepository()
        viewModel = ProductDetailViewModel(repository)
        viewModel.loadProduct(Product(0, "럭키", 1_000))
    }

    @Test
    fun `가장 최근에 본 상품을 알 수 있다`() {
        viewModel.loadLatestViewedProduct()
        assertThat(viewModel.latestViewedProduct.getOrAwaitValue()).isEqualTo(
            Product(
                id = 0,
                name = "럭키",
                price = 4_000,
            ),
        )
    }
}
