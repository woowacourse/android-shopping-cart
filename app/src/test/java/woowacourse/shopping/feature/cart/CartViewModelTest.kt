package woowacourse.shopping.feature.cart

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.CartContent
import woowacourse.shopping.domain.Money
import woowacourse.shopping.domain.Product
import woowacourse.shopping.feature.MainDispatcherExtension
import woowacourse.shopping.feature.fake.FakeCartRepository

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherExtension::class)
class CartViewModelTest {

    private val sampleProducts: List<Product> = (1..3).map {
        Product(id = it.toString(), name = "상품$it", price = Money(it * 1_000), imageUrl = "")
    }

    @Test
    fun `초기 진입 시 장바구니 항목을 불러와 state paginatedCartContents 에 노출한다`() = runTest {
        val viewModel = newViewModel(
            initial = listOf(
                CartContent(sampleProducts[0], 1),
                CartContent(sampleProducts[1], 2),
            ),
        )

        viewModel.initialLoading()

        val items = viewModel.uiState.value.paginatedCartContents
        assertEquals(listOf("1", "2"), items.map { it.id })
        assertEquals(listOf(1, 2), items.map { it.quantity })
    }

    @Test
    fun `이미 존재하는 상품을 증가시키면 수량이 누적된다`() = runTest {
        val viewModel = newViewModel(initial = listOf(CartContent(sampleProducts[0], 1)))
        viewModel.initialLoading()

        viewModel.increase("1")

        val target = viewModel.uiState.value.paginatedCartContents.first { it.id == "1" }
        assertEquals(2, target.quantity)
    }

    @Test
    fun `보유 수량과 같은 수량을 감소시키면 해당 상품이 제거된다`() = runTest {
        val viewModel = newViewModel(initial = listOf(CartContent(sampleProducts[0], 1)))
        viewModel.initialLoading()

        viewModel.decrease("1")

        val items = viewModel.uiState.value.paginatedCartContents
        assertTrue(items.none { it.id == "1" })
    }

    @Test
    fun `보유 수량보다 적게 감소시키면 수량이 줄어든다`() = runTest {
        val viewModel = newViewModel(initial = listOf(CartContent(sampleProducts[0], 3)))
        viewModel.initialLoading()

        viewModel.decrease("1")

        val target = viewModel.uiState.value.paginatedCartContents.first { it.id == "1" }
        assertEquals(2, target.quantity)
    }

    @Test
    fun `state paginatedCartContents 가 비면 isEndPage 가 true 가 된다`() = runTest {
        val viewModel = newViewModel(initial = listOf(CartContent(sampleProducts[0], 1)))
        viewModel.initialLoading()

        viewModel.deleteCartItem("1")

        assertTrue(viewModel.uiState.value.paginatedCartContents.isEmpty())
        assertTrue(viewModel.isEndPage())
    }

    @Test
    fun `외부에서 장바구니에 변경이 생기면 cart 재조회로 state 에 반영된다`() = runTest {
        val cartRepository = FakeCartRepository(listOf(CartContent(sampleProducts[0], 1)))
        val viewModel = CartViewModel(initialPageSize = 5, cartRepository = cartRepository)
        viewModel.initialLoading()

        cartRepository.increase(sampleProducts[1])
        viewModel.initialLoading()

        val items = viewModel.uiState.value.paginatedCartContents
        assertEquals(setOf("1", "2"), items.map { it.id }.toSet())
    }

    private fun newViewModel(initial: List<CartContent>): CartViewModel = CartViewModel(
        initialPageSize = 5,
        cartRepository = FakeCartRepository(initial),
    )
}
