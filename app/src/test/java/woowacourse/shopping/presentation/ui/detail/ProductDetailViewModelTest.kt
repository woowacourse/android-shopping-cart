package woowacourse.shopping.presentation.ui.detail

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.cart
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentRepository
import woowacourse.shopping.dummyProduct
import woowacourse.shopping.dummyRecentProduct
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.shoppingProduct

@ExtendWith(InstantTaskExecutorExtension::class)
@ExtendWith(MockKExtension::class)
class ProductDetailViewModelTest {
    @MockK
    private lateinit var productRepository: ProductRepository

    @MockK
    private lateinit var cartRepository: CartRepository

    @MockK
    private lateinit var recentRepository: RecentRepository

    @InjectMockKs
    private lateinit var viewModel: ProductDetailViewModel

    @Test
    fun `loadById로 특정 상품의 데이터를 가져온다`() {
        every { productRepository.loadById(any()) } returns Result.success(dummyProduct)
        every { cartRepository.find(any()) } returns Result.success(cart)
        viewModel.fetchInitialData(0)
        Assertions.assertEquals(
            viewModel.shoppingProduct.getOrAwaitValue(1),
            UiState.Success(shoppingProduct),
        )
    }

    @Test
    fun `loadById로 특정 상품의 데이터를 가져오기 실패하면 해당하는 Error 상태로 전환한다`() {
        every { productRepository.loadById(any()) } returns Result.failure(Throwable())
        viewModel.fetchInitialData(0)
        Assertions.assertEquals(
            viewModel.error.getOrAwaitValue(1).getContentIfNotHandled(),
            DetailError.ProductItemsNotFound,
        )
    }

    @Test
    fun `초기화 후 장바구니에 담기 버튼이 눌리면 상품과 수량을 저장한다`() {
        // given
        every { productRepository.loadById(any()) } returns Result.success(dummyProduct)
        every { cartRepository.find(any()) } returns Result.success(cart)
        every {
            cartRepository.setQuantity(shoppingProduct.toProduct(), shoppingProduct.quantity)
        } returns Result.success(shoppingProduct.id)

        // when
        viewModel.fetchInitialData(0)
        viewModel.saveCartItem()

        // then
        Assertions.assertEquals(
            viewModel.moveEvent.getOrAwaitValue(10).getContentIfNotHandled(),
            FromDetailToScreen.Shopping(shoppingProduct.id, shoppingProduct.quantity),
        )
    }

    @Test
    fun `마지막 상품을 불러와 확인할 수 있다`() {
        // given
        every { recentRepository.loadMostRecent() } returns Result.success(dummyRecentProduct)

        // when
        viewModel.loadLastProduct()

        // then
        Assertions.assertEquals(
            viewModel.lastProduct.getOrAwaitValue(10),
            UiState.Success(dummyRecentProduct),
        )
    }

    @Test
    fun `마지막 상품을 불러오지 못하면 해당하는 Error 이벤트를 보낸다`() {
        // given
        every { recentRepository.loadMostRecent() } returns Result.failure(Throwable())

        // when
        viewModel.loadLastProduct()

        // then
        Assertions.assertEquals(
            viewModel.error.getOrAwaitValue(10).getContentIfNotHandled(),
            DetailError.RecentItemNotFound,
        )
    }
}
