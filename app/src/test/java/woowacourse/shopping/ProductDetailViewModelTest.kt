package woowacourse.shopping

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.fixtures.InstantTaskExecutorExtension
import woowacourse.shopping.productdetail.ProductDetailViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    private lateinit var viewModel: ProductDetailViewModel

    @BeforeEach
    fun setUp() {
        viewModel = ProductDetailViewModel(DummyShoppingRepository)
    }
}
