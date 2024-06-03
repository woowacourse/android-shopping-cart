package woowacourse.shopping.viewmodel

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.productdetail.ProductDetailViewModel
import woowacourse.shopping.repository.DummyProductRepository
import woowacourse.shopping.repository.DummyShoppingRepository
import woowacourse.shopping.viewmodel.fixtures.InstantTaskExecutorExtension

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    private lateinit var viewModel: ProductDetailViewModel

    @BeforeEach
    fun setUp() {
        viewModel = ProductDetailViewModel(DummyProductRepository, DummyShoppingRepository)
    }

    @Test
    fun `장바구니 담기에 성공하면, isAddSuccess에 true가 반환된다`() {
        // when
        viewModel.initProductDetail(0)
        viewModel.addProductToCart()

        // given
        val actual = viewModel.isAddSuccess.getValue()

        // then
        assertThat(actual).isEqualTo(true)
    }
}
