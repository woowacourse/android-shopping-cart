package woowacourse.shopping.ui.detail

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.data.CartsImpl
import woowacourse.shopping.model.data.ProductsImpl
import woowacourse.shopping.ui.detail.viewmodel.ProductDetailViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    private lateinit var viewModel: ProductDetailViewModel

    @BeforeEach
    fun setUp() {
        ProductsImpl.deleteAll()
        CartsImpl.deleteAll()
        viewModel = ProductDetailViewModel(ProductsImpl)
    }

    @Test
    fun `선택한 상품이 불러와진다`() {
        // given
        val productId = ProductsImpl.save(product)

        // when
        viewModel.loadProduct(productId)

        // then
        assertEquals(
            viewModel.productWithQuantity.getOrAwaitValue().product,
            product.copy(id = productId),
        )
    }

    @Test
    fun `상품이 장바구니에 담긴다`() {
        // given
        val productId = ProductsImpl.save(product)
        viewModel.loadProduct(productId)

        // when
        viewModel.plusCount()
        viewModel.addProductToCart()
        val actual = CartsImpl.findAll().size

        // then
        assertThat(actual).isEqualTo(1)
    }

    companion object {
        private val product = Product(id = 0L, imageUrl = "", name = "맥북", price = 100)
    }
}
