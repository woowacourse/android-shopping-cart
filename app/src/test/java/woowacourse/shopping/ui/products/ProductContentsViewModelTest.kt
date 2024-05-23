package woowacourse.shopping.ui.products

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.data.CartsImpl
import woowacourse.shopping.model.data.ProductsImpl
import woowacourse.shopping.model.data.RecentProductsImpl
import woowacourse.shopping.ui.products.viewmodel.ProductContentsViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductContentsViewModelTest {
    private lateinit var viewModel: ProductContentsViewModel

    @BeforeEach
    fun setUp() {
        ProductsImpl.deleteAll()
        CartsImpl.deleteAll()
    }

    @Test
    fun `상품은 한 화면에 20개까지만 보여져야 한다`() {
        // given
        repeat(100) {
            ProductsImpl.save(product)
        }

        // when
        viewModel = ProductContentsViewModel(ProductsImpl, RecentProductsImpl, CartsImpl)

        // then
        assertThat(viewModel.productWithQuantity.getOrAwaitValue().size).isEqualTo(20)
    }

    @Test
    fun `첫번째 상품은 맥북이어야 한다`() {
        // given
        ProductsImpl.save(product)

        // when
        viewModel = ProductContentsViewModel(ProductsImpl, RecentProductsImpl, CartsImpl)

        // then
        assertThat(viewModel.productWithQuantity.getOrAwaitValue()[0].product.name).isEqualTo("맥북")
    }

    companion object {
        private val product = Product(id = 0L, imageUrl = "", name = "맥북", price = 100)
    }
}
