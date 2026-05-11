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
import woowacourse.shopping.model.Price
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductTitle
import woowacourse.shopping.model.Quantity
import woowacourse.shopping.model.ShoppingCartItem
import woowacourse.shopping.repository.ShoppingCartRepository
import kotlin.math.min
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private val product = Product("1", ProductTitle("동원 스위트콘"), Price(99_800), "")

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
            // given
            val shoppingCartViewModel =
                ShoppingCartViewModel(
                    shoppingCartRepository = MockShoppingCartRepository(6),
                )

            shoppingCartViewModel.loadShoppingItems()
            advanceUntilIdle()

            shoppingCartViewModel.uiState.value.canMoveToNextPage shouldBe true
            shoppingCartViewModel.uiState.value.canMoveToPreviousPage shouldBe false

            // when
            shoppingCartViewModel.moveNextPage()
            advanceUntilIdle()

            // then
            shoppingCartViewModel.uiState.value.shoppingCartItems.size shouldBe 1
            shoppingCartViewModel.uiState.value.canMoveToNextPage shouldBe false
            shoppingCartViewModel.uiState.value.canMoveToPreviousPage shouldBe true
        }

    @Test
    fun `상품이 6개인 페이지에서 마지막 페이지에서 돌아가면 5개의 상품이 존재한다`() =
        runTest {
            // given
            val shoppingCartViewModel =
                ShoppingCartViewModel(
                    shoppingCartRepository = MockShoppingCartRepository(6),
                )
            shoppingCartViewModel.loadShoppingItems()
            advanceUntilIdle()

            shoppingCartViewModel.moveNextPage()
            advanceUntilIdle()

            // when
            shoppingCartViewModel.movePreviousPage()
            advanceUntilIdle()

            // then
            shoppingCartViewModel.uiState.value.shoppingCartItems.size shouldBe 5
        }
}

@OptIn(ExperimentalUuidApi::class)
private class MockShoppingCartRepository(
    itemSize: Int,
) : ShoppingCartRepository {
    private val shoppingCartItems =
        MutableList(itemSize) {
            ShoppingCartItem(Uuid.random().toString(), Quantity(0), product)
        }

    override suspend fun increaseItemQuantity(productId: String, quantity: Int) {
        shoppingCartItems.add(ShoppingCartItem(Uuid.random().toString(), Quantity(quantity), product))
    }

    override suspend fun getShoppingCartItem(productId: String): ShoppingCartItem? {
        return null
    }


    override suspend fun getTotalSize(): Int = shoppingCartItems.size

    override suspend fun remove(shoppingCartItemId: String) {
        shoppingCartItems.removeIf { it.id == shoppingCartItemId }
    }

    override suspend fun removeOrDecreaseCartItem(
        shoppingCartItemId: String,
        quantity: Int
    ) {

    }

    override suspend fun getCartItem(shoppingCartItemId: String): ShoppingCartItem? =
        shoppingCartItems.find { it.id == shoppingCartItemId }

    override suspend fun getCartItems(
        offset: Int,
        size: Int,
    ): List<ShoppingCartItem> {
        val offset = offset.coerceIn(0, shoppingCartItems.size)
        return shoppingCartItems.subList(offset, min(offset + size, shoppingCartItems.size))
    }
}
