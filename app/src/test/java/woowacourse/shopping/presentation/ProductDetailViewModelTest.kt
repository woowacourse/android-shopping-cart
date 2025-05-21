package woowacourse.shopping.presentation

import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.FIXTURE
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.productdetail.ProductDetailViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    private lateinit var repository: ProductRepository
    private lateinit var viewModel: ProductDetailViewModel

    @BeforeEach
    fun setUp() {
        repository = mockk()
        viewModel = ProductDetailViewModel(repository)
    }

    @Test
    fun 상품_정보를_불러온다() {
        viewModel.fetchData(FIXTURE.DUMMY_PRODUCT)

        val product = viewModel.product.getOrAwaitValue()
        assertThat(product).isEqualTo(FIXTURE.DUMMY_PRODUCT)
    }
}
