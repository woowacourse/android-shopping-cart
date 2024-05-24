package woowacourse.shopping.ui.detail

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.data.recentproduct.RecentProductRepositoryImpl
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.ui.FakeCartDao
import woowacourse.shopping.ui.FakeProductServerApi
import woowacourse.shopping.ui.FakeRecentProductDao
import woowacourse.shopping.ui.detail.viewmodel.ProductDetailViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    private lateinit var viewModel: ProductDetailViewModel
    private val productRepository = ProductRepositoryImpl(FakeProductServerApi)
    private val recentProductRepository = RecentProductRepositoryImpl.get(FakeRecentProductDao)
    private val cartRepository = CartRepositoryImpl.get(FakeCartDao)

    @BeforeEach
    fun setUp() {
        cartRepository.deleteAll()
        viewModel =
            ProductDetailViewModel(productRepository, recentProductRepository, cartRepository)
    }

    @Test
    fun `선택한 상품이 불러와진다`() {
        // given

        // when
        viewModel.loadProduct(0L)

        // then
        assertEquals(viewModel.productWithQuantity.getOrAwaitValue().product.name, "맥북")
    }

    @Test
    fun `상품이 장바구니에 담긴다`() {
        // given
        viewModel.loadProduct(0L)

        // when
        viewModel.plusCount()
        viewModel.addProductToCart()
        val actual = cartRepository.findAll().size

        // then
        assertThat(actual).isEqualTo(1)
    }
}
