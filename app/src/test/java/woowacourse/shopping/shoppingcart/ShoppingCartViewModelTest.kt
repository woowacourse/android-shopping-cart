package woowacourse.shopping.shoppingcart

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.fake.FakeShoppingCartRepository

@OptIn(ExperimentalCoroutinesApi::class)
class ShoppingCartViewModelTest {
    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `상품이 6개있다면 다음 페이지로 이동 가능하다`() =
        runTest {
            val shoppingCartViewModel =
                ShoppingCartViewModel(
                    shoppingCartRepository = FakeShoppingCartRepository(itemSize = 6),
                )

            shoppingCartViewModel.loadShoppingItems()
            advanceUntilIdle()

            shoppingCartViewModel.uiState.value.canMoveToNextPage shouldBe true
            shoppingCartViewModel.uiState.value.canMoveToPreviousPage shouldBe false

            shoppingCartViewModel.moveNextPage()
            advanceUntilIdle()

            shoppingCartViewModel.uiState.value.shoppingCartItems.size shouldBe 1
            shoppingCartViewModel.uiState.value.canMoveToNextPage shouldBe false
            shoppingCartViewModel.uiState.value.canMoveToPreviousPage shouldBe true
        }

    @Test
    fun `상품이 6개인 페이지에서 마지막 페이지에서 돌아가면 5개의 상품이 존재한다`() =
        runTest {
            val shoppingCartViewModel =
                ShoppingCartViewModel(
                    shoppingCartRepository = FakeShoppingCartRepository(itemSize = 6),
                )

            shoppingCartViewModel.loadShoppingItems()
            advanceUntilIdle()

            shoppingCartViewModel.moveNextPage()
            advanceUntilIdle()

            shoppingCartViewModel.movePreviousPage()
            advanceUntilIdle()

            shoppingCartViewModel.uiState.value.shoppingCartItems.size shouldBe 5
        }

    @Test
    fun `장바구니 상품 수량을 추가하면 수량이 증가한다`() =
        runTest {
            val shoppingCartViewModel =
                ShoppingCartViewModel(
                    shoppingCartRepository = FakeShoppingCartRepository(initialQuantity = 1),
                )

            shoppingCartViewModel.loadShoppingItems()
            advanceUntilIdle()

            shoppingCartViewModel.increaseItemQuantity(productId = "1", quantity = 1)
            advanceUntilIdle()

            shoppingCartViewModel.uiState.value.shoppingCartItems
                .first()
                .quantity shouldBe 2
        }

    @Test
    fun `장바구니 상품 수량을 감소하면 수량이 감소한다`() =
        runTest {
            val shoppingCartViewModel =
                ShoppingCartViewModel(
                    shoppingCartRepository = FakeShoppingCartRepository(initialQuantity = 2),
                )

            shoppingCartViewModel.loadShoppingItems()
            advanceUntilIdle()

            shoppingCartViewModel.decreaseItemQuantity(productId = "1", quantity = 1)
            advanceUntilIdle()

            shoppingCartViewModel.uiState.value.shoppingCartItems
                .first()
                .quantity shouldBe 1
        }
}
