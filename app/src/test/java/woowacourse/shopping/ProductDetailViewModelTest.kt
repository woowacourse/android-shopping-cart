package woowacourse.shopping

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.fixtures.InstantTaskExecutorExtension
import woowacourse.shopping.fixtures.getOrAwaitValue
import woowacourse.shopping.productdetail.ProductDetailViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    private lateinit var viewModel: ProductDetailViewModel

    @BeforeEach
    fun setUp() {
        viewModel = ProductDetailViewModel(DummyShoppingRepository)
    }

    @Test
    fun `장바구니 담기에 성공하면, isAddSuccess에 true가 반환된다`()  {
        // when
        viewModel.loadProductDetail(0)
        viewModel.addProductToCart()

        // given
        val actual = viewModel.isAddSuccess.getOrAwaitValue()

        // then
        assertThat(actual).isEqualTo(true)
    }
}
