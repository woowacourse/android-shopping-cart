package woowacourse.shopping.ui.viewmodel

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.ui.productdetail.ProductDetailViewModel
import woowacourse.shopping.util.InstantTaskExecutorExtension
import woowacourse.shopping.util.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    private lateinit var viewModel: ProductDetailViewModel

    @BeforeEach
    fun setup() {
        viewModel = ProductDetailViewModel()
    }

    @Test
    fun `product 값을 ID에 해당하는 상품으로 업데이트한다`() {
        // given
        val targetProductId = 1
        val expectedProduct = ProductDummyRepositoryImpl.fetchProductDetail(targetProductId)

        // when
        viewModel.loadProductDetail(targetProductId)

        // then
        val actual = viewModel.cartProduct.getOrAwaitValue()
        Assertions.assertEquals(expectedProduct, actual)
    }

    @Test
    fun `현재 선택된 상품을 장바구니에 추가한다`() {
        // given
        val productId = 2
        val product = ProductDummyRepositoryImpl.fetchProductDetail(productId)!!

        viewModel.loadProductDetail(productId)

        // when
        viewModel.updateCartProduct()

        // then
        val updatedCart = CartDummyRepositoryImpl.fetchCartProducts(page = 1)
        Assertions.assertTrue(updatedCart.any { it.name == product.name })
    }
}
