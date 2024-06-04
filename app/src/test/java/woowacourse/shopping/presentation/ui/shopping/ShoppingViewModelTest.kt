package woowacourse.shopping.presentation.ui.shopping

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentRepository
import woowacourse.shopping.dummyCartProducts
import woowacourse.shopping.dummyProducts
import woowacourse.shopping.dummyRecentProducts
import woowacourse.shopping.dummyShoppingProducts
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.presentation.ui.UiState

@ExtendWith(InstantTaskExecutorExtension::class)
@ExtendWith(MockKExtension::class)
class ShoppingViewModelTest {
    @RelaxedMockK
    private lateinit var productRepository: ProductRepository

    @RelaxedMockK
    private lateinit var cartRepository: CartRepository

    @RelaxedMockK
    private lateinit var recentRepository: RecentRepository

    @InjectMockKs
    private lateinit var viewModel: ShoppingViewModel

    @Test
    fun `viewModel이 초기화되면 최근 본 상품과 장바구니 상품, 상품 리스트 20개가 불러와진다`() {
        // given
        every { recentRepository.loadAll() } returns Result.success(dummyRecentProducts)
        every { cartRepository.loadAll() } returns Result.success(dummyCartProducts)
        every { productRepository.load(any(), any()) } returns Result.success(dummyProducts)

        // when
        viewModel.loadInitialShoppingItems()

        // then
        assertEquals(
            UiState.Success(dummyRecentProducts),
            viewModel.recentProducts.getOrAwaitValue(20),
        )
        assertEquals(
            UiState.Success(dummyCartProducts),
            viewModel.cartProducts.getOrAwaitValue(15),
        )
        assertEquals(
            UiState.Success(dummyShoppingProducts),
            viewModel.shoppingProducts.getOrAwaitValue(15),
        )
    }

    @Test
    fun `최근 본 상품 데이터 로드가 실패하면 해당하는 Error로 상태가 변화한다`() {
        // given
        every { recentRepository.loadAll() } returns Result.failure(Throwable())
        every { cartRepository.loadAll() } returns Result.success(dummyCartProducts)
        every { productRepository.load(any(), any()) } returns Result.success(dummyProducts)

        // when
        viewModel.loadInitialShoppingItems()

        // then
        Assertions.assertThat(
            viewModel.error.getOrAwaitValue(15).getContentIfNotHandled(),
        ).isEqualTo(ShoppingError.RecentProductItemsNotFound)
    }

    @Test
    fun `장바구니 상품 데이터 로드가 실패하면 해당하는 Error로 상태가 변화한다`() {
        // given
        every { recentRepository.loadAll() } returns Result.success(dummyRecentProducts)
        every { cartRepository.loadAll() } returns Result.failure(Throwable())
        every { productRepository.load(any(), any()) } returns Result.success(dummyProducts)

        // when
        viewModel.loadInitialShoppingItems()

        // then
        Assertions.assertThat(
            viewModel.error.getOrAwaitValue(15).getContentIfNotHandled(),
        ).isEqualTo(ShoppingError.CartItemsNotFound)
    }

    @Test
    fun `상품 데이터 로드가 실패하면 해당하는 Error로 상태가 변화한다`() {
        // given
        every { recentRepository.loadAll() } returns Result.success(dummyRecentProducts)
        every { cartRepository.loadAll() } returns Result.success(dummyCartProducts)
        every { productRepository.load(any(), any()) } returns Result.failure(Throwable())

        // when
        viewModel.loadInitialShoppingItems()

        // then
        Assertions.assertThat(
            viewModel.error.getOrAwaitValue(15).getContentIfNotHandled(),
        ).isEqualTo(ShoppingError.ProductItemsNotFound)
    }

    @Test
    fun `초기화 후, 더보기 버튼이 눌리면 데이터가 20개 더 불러와진다`() {
        // given
        every { recentRepository.loadAll() } returns Result.success(dummyRecentProducts)
        every { cartRepository.loadAll() } returns Result.success(dummyCartProducts)
        every { productRepository.load(any(), any()) } returns Result.success(dummyProducts)
        viewModel.loadInitialShoppingItems()

        // when
        viewModel.fetchProductForNewPage()

        // then
        assertEquals(
            viewModel.shoppingProducts.getOrAwaitValue(15),
            UiState.Success(dummyShoppingProducts + dummyShoppingProducts),
        )
    }

    @Test
    fun `더보기 버튼이 눌렸을 때 데이터 로드가 실패하면 해당하는 Error로 상태가 변화한다`() {
        every { productRepository.load(any(), any()) } returns Result.failure(Throwable())
        every { cartRepository.loadAll() } returns Result.success(dummyCartProducts)
        viewModel.fetchProductForNewPage()
        assertEquals(
            viewModel.error.getOrAwaitValue(15).getContentIfNotHandled(),
            ShoppingError.AllProductsLoaded,
        )
    }
}
