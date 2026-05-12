package woowacourse.shopping.feature.productlist.viewmodel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.CartContent
import woowacourse.shopping.domain.Money
import woowacourse.shopping.domain.Product
import woowacourse.shopping.feature.MainDispatcherExtension
import woowacourse.shopping.feature.fake.FakeCartRepository
import woowacourse.shopping.feature.fake.FakeProductRepository
import woowacourse.shopping.feature.fake.FakeRecentProductRepository

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherExtension::class)
class ProductListViewModelTest {

    private val sampleProducts: List<Product> = (1..5).map {
        Product(id = it.toString(), name = "상품$it", price = Money(it * 1_000), imageUrl = "")
    }

    @Test
    fun `초기 진입 시 상품 목록을 불러와 state productUiModels 에 노출한다`() = runTest {
        val viewModel = newViewModel()

        viewModel.initialLoading()

        val state = viewModel.uiState.value
        assertEquals(sampleProducts.size, state.productUiModels.size)
        assertEquals("상품1", state.productUiModels.first().name)
    }

    @Test
    fun `최근 본 상품이 state recentProducts 에 노출된다`() = runTest {
        val viewModel = newViewModel(
            recentProductRepository = FakeRecentProductRepository(listOf("3", "1")),
        )

        viewModel.initialLoading()

        val state = viewModel.uiState.value
        assertEquals(listOf("3", "1"), state.recentProducts.map { it.id })
        assertEquals("3", state.mostRecentProductId)
    }

    @Test
    fun `수량 증가 이벤트가 state productUiModels 의 수량에 반영된다`() = runTest {
        val viewModel = newViewModel()
        viewModel.initialLoading()

        viewModel.increase("2")

        val target = viewModel.uiState.value.productUiModels.first { it.id == "2" }
        assertEquals(1, target.quantity)
        assertEquals(1, viewModel.uiState.value.cartTotalQuantity)
    }

    @Test
    fun `이미 존재하는 상품을 추가하면 수량이 누적된다`() = runTest {
        val viewModel = newViewModel()
        viewModel.initialLoading()

        viewModel.increase("1")
        viewModel.increase("1")

        val target = viewModel.uiState.value.productUiModels.first { it.id == "1" }
        assertEquals(2, target.quantity)
    }

    @Test
    fun `보유 수량과 같은 수량을 감소시키면 해당 상품이 제거된다`() = runTest {
        val viewModel = newViewModel(
            cartRepository = FakeCartRepository(listOf(CartContent(sampleProducts[0], 1))),
        )
        viewModel.initialLoading()

        viewModel.decrease("1")

        val target = viewModel.uiState.value.productUiModels.first { it.id == "1" }
        assertEquals(0, target.quantity)
        assertEquals(0, viewModel.uiState.value.cartTotalQuantity)
    }

    @Test
    fun `보유 수량보다 적게 감소시키면 수량이 줄어든다`() = runTest {
        val viewModel = newViewModel(
            cartRepository = FakeCartRepository(listOf(CartContent(sampleProducts[0], 3))),
        )
        viewModel.initialLoading()

        viewModel.decrease("1")

        val target = viewModel.uiState.value.productUiModels.first { it.id == "1" }
        assertEquals(2, target.quantity)
    }

    @Test
    fun `장바구니에서 수량이 바뀐 뒤 cartRefresh 를 호출하면 state 에 반영된다`() = runTest {
        val cartRepository = FakeCartRepository()
        val viewModel = newViewModel(cartRepository = cartRepository)
        viewModel.initialLoading()

        cartRepository.increase(sampleProducts[2])
        viewModel.cartRefresh()

        val target = viewModel.uiState.value.productUiModels.first { it.id == "3" }
        assertEquals(1, target.quantity)
    }

    @Test
    fun `insertRecentProduct 는 RecentProductRepository 에 기록을 위임한다`() = runTest {
        val recentRepository = FakeRecentProductRepository()
        val viewModel = newViewModel(recentProductRepository = recentRepository)
        viewModel.initialLoading()

        viewModel.insertRecentProduct("2")

        assertEquals(listOf("2"), recentRepository.insertedIds)
    }

    private fun newViewModel(
        cartRepository: FakeCartRepository = FakeCartRepository(),
        productRepository: FakeProductRepository = FakeProductRepository(sampleProducts),
        recentProductRepository: FakeRecentProductRepository = FakeRecentProductRepository(),
    ): ProductListViewModel = ProductListViewModel(
        cartRepository = cartRepository,
        productRepository = productRepository,
        recentProductRepository = recentProductRepository,
    )
}
