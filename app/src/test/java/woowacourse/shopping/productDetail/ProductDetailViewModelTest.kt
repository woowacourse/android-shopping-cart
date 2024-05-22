package woowacourse.shopping.productDetail

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.productTestFixture
import woowacourse.shopping.productsTestFixture
import woowacourse.shopping.repository.FakeShoppingCartItemRepository
import woowacourse.shopping.repository.FakeShoppingProductsRepository
import woowacourse.shopping.repository.ShoppingCartItemRepository
import woowacourse.shopping.repository.ShoppingProductsRepository

class ProductDetailViewModelTest {
    private lateinit var shoppingCartItemRepository: ShoppingCartItemRepository
    private lateinit var shoppingProductsRepository: ShoppingProductsRepository
    private lateinit var viewModel: ProductDetailViewModel

    @BeforeEach
    fun setUp() {
        shoppingProductsRepository = FakeShoppingProductsRepository(productsTestFixture(40))
        shoppingCartItemRepository = FakeShoppingCartItemRepository()
    }

    @Test
    fun `현재 상품을 표시한다`() {
        // given
        viewModel = ProductDetailViewModel(3, shoppingProductsRepository, shoppingCartItemRepository)

        // then
        assertThat(viewModel.product).isEqualTo(productTestFixture(3))
    }

    @Test
    fun `현재 상품을 장바구니에 추가한다`() {
        // given
        viewModel = ProductDetailViewModel(3, shoppingProductsRepository, shoppingCartItemRepository)

        // when
        viewModel.addProductToCart()

        // then
        assertThat(shoppingCartItemRepository.loadPagedCartItems(1)).contains(productTestFixture(3))
    }
}
