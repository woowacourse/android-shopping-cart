package woowacourse.shopping.feature.productdetail.viewmodel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.Money
import woowacourse.shopping.domain.Product
import woowacourse.shopping.feature.MainDispatcherExtension
import woowacourse.shopping.feature.fake.FakeCartRepository
import woowacourse.shopping.feature.fake.FakeProductRepository

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherExtension::class)
class ProductDetailViewModelTest {

    private val sampleProducts: List<Product> = (1..3).map {
        Product(id = it.toString(), name = "상품$it", price = Money(it * 1_000), imageUrl = "")
    }

    @Test
    fun `초기 진입 시 상품 상세를 불러와 state productState 에 Success 로 노출한다`() = runTest {
        val viewModel = newViewModel()

        viewModel.initialLoading(productId = "2")

        val state = viewModel.uiState.value.productState
        assertTrue(state is ProductDetailLoadingState.Success)
        assertEquals("2", (state as ProductDetailLoadingState.Success).product.id)
    }

    @Test
    fun `recentProductId 가 현재 상품과 다르면 state recentProductState 에 노출된다`() = runTest {
        val viewModel = newViewModel()

        viewModel.initialLoading(productId = "2", recentProductId = "1")

        val recentState = viewModel.uiState.value.recentProductState
        assertTrue(recentState is ProductDetailLoadingState.Success)
        assertEquals("1", (recentState as ProductDetailLoadingState.Success).product.id)
    }

    @Test
    fun `recentProductId 가 현재 상품과 같으면 recentProductState 는 노출되지 않는다`() = runTest {
        val viewModel = newViewModel()

        viewModel.initialLoading(productId = "2", recentProductId = "2")

        val recentState = viewModel.uiState.value.recentProductState
        assertEquals(ProductDetailLoadingState.None, recentState)
    }

    @Test
    fun `increase 이벤트의 결과가 state quantity 에 반영된다`() = runTest {
        val viewModel = newViewModel()
        viewModel.initialLoading(productId = "1")

        viewModel.increase()
        viewModel.increase()

        assertEquals(3, viewModel.uiState.value.quantity)
    }

    @Test
    fun `decrease 이벤트의 결과가 state quantity 에 반영된다`() = runTest {
        val viewModel = newViewModel()
        viewModel.initialLoading(productId = "1")

        viewModel.decrease()

        assertEquals(0, viewModel.uiState.value.quantity)
    }

    @Test
    fun `addToCart 는 기존 수량과 현재 quantity 의 합을 CartRepository 에 반영한다`() = runTest {
        val cartRepository = FakeCartRepository()
        cartRepository.increase(sampleProducts[0])
        val viewModel = newViewModel(cartRepository = cartRepository)
        viewModel.initialLoading(productId = "1")
        viewModel.increase()
        viewModel.increase()

        viewModel.addToCart()

        val cart = cartRepository.loadCart()
        assertEquals(4, cart.quantityOf("1"))
    }

    private fun newViewModel(
        productRepository: FakeProductRepository = FakeProductRepository(sampleProducts),
        cartRepository: FakeCartRepository = FakeCartRepository(),
    ): ProductDetailViewModel = ProductDetailViewModel(
        productRepository = productRepository,
        cartRepository = cartRepository,
    )
}
