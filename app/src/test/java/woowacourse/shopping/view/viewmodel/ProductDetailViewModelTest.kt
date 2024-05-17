package woowacourse.shopping.view.viewmodel

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.MockProductRepository
import woowacourse.shopping.MockShoppingCartRepository
import woowacourse.shopping.TestFixture.getOrAwaitValue
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.view.detail.ProductDetailViewModel
import woowacourse.shopping.view.products.ProductListViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    private lateinit var productRepository: ProductRepository
    private lateinit var shoppingCartRepository: ShoppingCartRepository
    private lateinit var viewModel: ProductDetailViewModel

    @BeforeEach
    fun setUp() {
        productRepository = MockProductRepository()
        shoppingCartRepository = MockShoppingCartRepository()
        viewModel = ProductDetailViewModel(
            productRepository = productRepository,
            shoppingCartRepository = shoppingCartRepository,
        )
    }

    @Test
    fun `상품아이디로_상품을_요청하면_아이디와_일치하는_상품_목록을_반환해야_한다`() {
        viewModel.loadProductItem(0)
        val actual = viewModel.product.getOrAwaitValue()
        val expected = (productRepository as MockProductRepository).products[0]

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `상품을_장바구니에_추가하면_장바구니에_상품이_추가되어야_한다`() {
        val newProduct =
            Product(
                id = 3L,
                imageUrl = "",
                price = 5_000,
                name = "아이스 아메리카노",
            )
        viewModel.addShoppingCartItem(newProduct).join()

        val actual = (shoppingCartRepository as MockShoppingCartRepository).cartItems.last()
        val expected = CartItem(3L, newProduct)

        assertThat(actual).isEqualTo(expected)
    }
}
