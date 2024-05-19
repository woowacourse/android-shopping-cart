package woowacourse.shopping.ui.detail

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.data.CartsImpl
import woowacourse.shopping.model.data.ProductsImpl

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    private lateinit var viewModel: ProductDetailViewModel

    @BeforeEach
    fun setUp() {
        viewModel = ProductDetailViewModel(ProductsImpl, CartsImpl)
    }

    @Test
    fun `선택한 상품이 불러와진다`() {
        // given
        val productId = ProductsImpl.save(product)

        // when
        viewModel.loadProduct(productId)

        // then
        assertEquals(viewModel.product.getOrAwaitValue(), product.copy(id = productId))
    }

    @Test
    fun `상품이 장바구니에 담긴다`() {
        // given
        val productId = ProductsImpl.save(product)
        viewModel.loadProduct(productId)

        // when
        viewModel.addProductToCart()

        // then
        assertEquals(CartsImpl.find(0), product.copy(id = 0))
    }

    companion object {
        private val product = Product(imageUrl = "", name = "맥북", price = 100)
    }
}
