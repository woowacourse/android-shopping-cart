package woowacourse.shopping.viewmodel.product

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.fixture.FakeCartProductRepository
import woowacourse.shopping.fixture.FakeRecentProductRepository
import woowacourse.shopping.view.product.detail.ProductDetailViewModel
import woowacourse.shopping.viewmodel.InstantTaskExecutorExtension

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var cartProductRepository: CartProductRepository
    private lateinit var recentProductRepository: RecentProductRepository
    private lateinit var product: Product

    @BeforeEach
    fun setup() {
        cartProductRepository = FakeCartProductRepository()
        recentProductRepository = FakeRecentProductRepository()
        product = Product(id = 0L, imageUrl = "", name = "Product 0", price = 1000)
        viewModel = ProductDetailViewModel(product, cartProductRepository, recentProductRepository)
    }

    @Test
    fun `장바구니에 상품을 추가하면 repository에 저장된다`() {
        // when
        viewModel.onAddToShoppingCartClick()

        // then
        val cartProducts = cartProductRepository.getAll()
        assertEquals(1, cartProducts.size)
        assertEquals(product, cartProducts.first().product)
    }

    @Test
    fun `장바구니에 상품을 추가한 후 event가 발생한다`() {
        // when
        viewModel.onAddToShoppingCartClick()

        // then
        assertEquals(Unit, viewModel.navigateEvent.getValue())
    }
}
