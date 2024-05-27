package woowacourse.shopping.view.home

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.db.cart.CartRepository
import woowacourse.shopping.data.db.product.ProductRepository
import woowacourse.shopping.data.db.recent.RecentProductRepository
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.helper.FakeCartRepositoryImpl
import woowacourse.shopping.helper.FakeProductRepositoryImpl
import woowacourse.shopping.helper.FakeRecentProductRepositoryImpl
import woowacourse.shopping.helper.InstantTaskExecutorExtension
import woowacourse.shopping.view.state.UIState

@ExtendWith(InstantTaskExecutorExtension::class)
class HomeViewModelTest {
    private lateinit var viewModel: HomeViewModel
    private lateinit var productRepository: ProductRepository
    private val cartRepository: CartRepository = FakeCartRepositoryImpl()
    private val recentProductRepository: RecentProductRepository = FakeRecentProductRepositoryImpl()

    @Test
    fun `초기 로딩 시 빈 데이터가 주어지면 UIState는 Empty로 설정된다`() {
        productRepository = FakeProductRepositoryImpl()
        viewModel = HomeViewModel(productRepository, cartRepository, recentProductRepository)

        assertThat(viewModel.shoppingUiState.value is UIState.Empty).isTrue()
        assertEquals(false, viewModel.canLoadMore.value)
    }

    @Test
    fun `초기 로딩 시 데이터가 주어지면 UIState는 Success로 설정된다`() {
        val products =
            List(20) {
                Product(
                    id = it.toLong(),
                    name = "Product $it",
                    price = 1000,
                    imageUrl = "URL $it",
                )
            }
        productRepository = FakeProductRepositoryImpl(products)
        viewModel = HomeViewModel(productRepository, cartRepository, recentProductRepository)

        assertThat(viewModel.shoppingUiState.value is UIState.Success).isTrue()
        assertEquals(false, viewModel.canLoadMore.value)
    }

    @Test
    fun `로드된 데이터가 20개 이하이면 canLoadMore는 false로 설정된다`() {
        val products =
            List(19) {
                Product(
                    id = it.toLong(),
                    name = "Product $it",
                    price = 1000,
                    imageUrl = "URL $it",
                )
            }
        productRepository = FakeProductRepositoryImpl(products)
        viewModel = HomeViewModel(productRepository, cartRepository, recentProductRepository)

        assertEquals(false, viewModel.canLoadMore.value)
    }

    @Test
    fun `로드된 데이터가 20개 초과이면 canLoadMore는 true로 설정된다`() {
        val products =
            List(21) {
                Product(
                    id = it.toLong(),
                    name = "Product $it",
                    price = 1000,
                    imageUrl = "URL $it",
                )
            }
        productRepository = FakeProductRepositoryImpl(products)
        viewModel = HomeViewModel(productRepository, cartRepository, recentProductRepository)

        assertThat(viewModel.canLoadMore.value).isEqualTo(true)
    }

    @Test
    fun `장바구니 수량이 정상적으로 로드된다`() {
        val product = Product(1, "Product 1", 1000, "URL 1")
        cartRepository.save(product, 3)
        productRepository = FakeProductRepositoryImpl(listOf(product))
        viewModel = HomeViewModel(productRepository, cartRepository, recentProductRepository)

        assertEquals(3, viewModel.totalQuantity.value)
    }

    @Test
    fun `장바구니 수량이 증가하면 totalQuantity가 증가한다`() {
        val product = Product(1, "Product 1", 1000, "URL 1")
        productRepository = FakeProductRepositoryImpl(listOf(product))
        viewModel = HomeViewModel(productRepository, cartRepository, recentProductRepository)

        viewModel.onPlusButtonClick(product)

        assertEquals(1, viewModel.totalQuantity.value)
    }

    @Test
    fun `장바구니 수량이 감소하면 totalQuantity가 감소한다`() {
        val product = Product(1, "Product 1", 1000, "URL 1")
        cartRepository.save(product, 2)
        productRepository = FakeProductRepositoryImpl(listOf(product))
        viewModel = HomeViewModel(productRepository, cartRepository, recentProductRepository)

        viewModel.onQuqntityMinusButtonClick(product.id)

        assertEquals(1, viewModel.totalQuantity.value)
    }

    @Test
    fun `상품 클릭 시 navigateToDetail 이벤트가 발생한다`() {
        val product = Product(1, "Product 1", 1000, "URL 1")
        productRepository = FakeProductRepositoryImpl(listOf(product))
        viewModel = HomeViewModel(productRepository, cartRepository, recentProductRepository)

        viewModel.onProductClick(product.id)

        assertThat(viewModel.navigateToDetail.value?.getContentIfNotHandled()).isEqualTo(product.id)
    }

    @Test
    fun `장바구니 버튼 클릭 시 navigateToCart 이벤트가 발생한다`() {
        productRepository = FakeProductRepositoryImpl()
        viewModel = HomeViewModel(productRepository, cartRepository, recentProductRepository)

        viewModel.onShoppingCartButtonClick()

        assertThat(viewModel.navigateToCart.value?.getContentIfNotHandled()).isEqualTo(true)
    }

    @Test
    fun `updateData 호출 시 로드된 상품 수량이 업데이트 된다`() {
        val product = Product(1, "Product 1", 1000, "URL 1")
        cartRepository.save(product, 2)
        productRepository = FakeProductRepositoryImpl(listOf(product))
        viewModel = HomeViewModel(productRepository, cartRepository, recentProductRepository)

        viewModel.updateData()

        assertEquals(2, viewModel.totalQuantity.value)
        assertThat(viewModel.loadedProductItems.value?.first()?.quantity).isEqualTo(2)
    }
}
