package woowacourse.shopping.presentation

import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.Fixture
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.presentation.productdetail.ProductDetailViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    private lateinit var cartRepository: CartRepository
    private lateinit var viewModel: ProductDetailViewModel

    @BeforeEach
    fun setUp() {
        cartRepository = mockk<CartRepository>(relaxed = true)
        viewModel = ProductDetailViewModel(cartRepository)
    }

    @Test
    fun 상품_정보를_불러온다() {
        viewModel.fetchData(Fixture.dummyProduct)

        val product = viewModel.product.getOrAwaitValue()
        assertThat(product).isEqualTo(Fixture.dummyProduct)
    }
}
