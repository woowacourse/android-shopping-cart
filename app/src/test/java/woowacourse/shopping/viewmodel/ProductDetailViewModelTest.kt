package woowacourse.shopping.viewmodel

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.PRODUCT_1
import woowacourse.shopping.PRODUCT_2
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.view.productdetail.ProductDetailViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var repository: CartRepository

    @BeforeEach
    fun setUp() {
        repository = CartRepositoryImpl
        viewModel = ProductDetailViewModel(repository)
    }

    @Test
    fun `장바구니 담기 버튼을 클릭하면 해당 상품이 누적 저장된다`() {
        // given

        // when
        viewModel.onAddToCartClicked(PRODUCT_1)
        viewModel.onAddToCartClicked(PRODUCT_2)

        // then
        repository.products shouldBe listOf(PRODUCT_1, PRODUCT_2)
        viewModel.addToCart.value shouldBe Unit
    }
}
