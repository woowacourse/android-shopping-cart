package woowacourse.shopping.productlist

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
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import woowacourse.shopping.fake.FakeProductRepository
import woowacourse.shopping.fake.FakeShoppingCartRepository
import woowacourse.shopping.fake.FakeViewedProductRepository

@OptIn(ExperimentalCoroutinesApi::class)
class ProductListViewModelTest {
    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @ParameterizedTest
    @CsvSource("20, 20", "15, 15", "25, 20")
    fun `로드된 상품 개수에 따라 최대 20개까지 반환한다`(
        itemSize: Int,
        expectedItemSize: Int,
    ) = runTest {
        val productListViewModel =
            ProductListViewModel(
                productRepository = FakeProductRepository(itemSize),
                shoppingCartRepository = FakeShoppingCartRepository(),
                viewedProductRepository = FakeViewedProductRepository(),
            )

        productListViewModel.loadMoreProducts()
        advanceUntilIdle()

        productListViewModel.uiState.value.productUiModels.size shouldBe expectedItemSize
    }

    @ParameterizedTest
    @CsvSource("20, 1, 20", "25, 2, 25", "50, 2, 40")
    fun `다음 페이지로 이동하면 기존 상품에 추가된 상품을 반환한다`(
        itemSize: Int,
        pageMoveCount: Int,
        expectedItemSize: Int,
    ) = runTest {
        val productListViewModel =
            ProductListViewModel(
                productRepository = FakeProductRepository(itemSize),
                shoppingCartRepository = FakeShoppingCartRepository(),
                viewedProductRepository = FakeViewedProductRepository(),
            )

        repeat(pageMoveCount) {
            productListViewModel.loadMoreProducts()
        }
        advanceUntilIdle()

        productListViewModel.uiState.value.productUiModels.size shouldBe expectedItemSize
    }

    @Test
    fun `상품 수량을 추가하면 장바구니 수량이 증가한다`() =
        runTest {
            val productListViewModel =
                ProductListViewModel(
                    productRepository = FakeProductRepository(),
                    shoppingCartRepository = FakeShoppingCartRepository(itemSize = 0),
                    viewedProductRepository = FakeViewedProductRepository(),
                )

            productListViewModel.loadProducts()
            advanceUntilIdle()

            productListViewModel.increaseItemQuantity(productId = "1")
            advanceUntilIdle()

            productListViewModel.uiState.value.productUiModels
                .first()
                .quantity shouldBe 1
            productListViewModel.uiState.value.cartItemCount shouldBe 1
        }

    @Test
    fun `상품 수량을 감소하면 장바구니 수량이 감소한다`() =
        runTest {
            val shoppingCartRepository = FakeShoppingCartRepository(initialQuantity = 2)
            val productListViewModel =
                ProductListViewModel(
                    productRepository = FakeProductRepository(),
                    shoppingCartRepository = shoppingCartRepository,
                    viewedProductRepository = FakeViewedProductRepository(),
                )

            productListViewModel.loadProducts()
            advanceUntilIdle()

            productListViewModel.decreaseItemQuantity(productId = "1")
            advanceUntilIdle()

            productListViewModel.uiState.value.productUiModels
                .first()
                .quantity shouldBe 1
            productListViewModel.uiState.value.cartItemCount shouldBe 1
        }
}
