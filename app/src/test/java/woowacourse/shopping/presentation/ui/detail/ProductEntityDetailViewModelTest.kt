package woowacourse.shopping.presentation.ui.detail

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.cartProduct
import woowacourse.shopping.domain.Repository
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.detail.ProductDetailViewModel.Companion.PRODUCT_NOT_FOUND

@ExtendWith(InstantTaskExecutorExtension::class)
@ExtendWith(MockKExtension::class)
class ProductEntityDetailViewModelTest {
    @MockK
    private lateinit var repository: Repository

    @InjectMockKs
    private lateinit var viewModel: ProductDetailViewModel

    @Test
    fun `loadById로 특정 상품의 데이터를 가져온다`() {
        every { repository.findProductById(any()) } returns Result.success(cartProduct)
        every { repository.saveRecent(any()) } returns Result.success(1L)
        viewModel.findCartProductById(0)
        Thread.sleep(1000)
        Assertions.assertEquals(viewModel.product.getOrAwaitValue(), UiState.Success(cartProduct))
    }

    @Test
    fun `loadById로 특정 상품의 데이터를 가져오기 실패하면 Error state로 전환한다`() {
        every { repository.findProductById(any()) } returns Result.failure(Throwable())
        viewModel.findCartProductById(0)
        Thread.sleep(1000)
        Assertions.assertEquals(viewModel.errorHandler.getOrAwaitValue(1).getContentIfNotHandled(), PRODUCT_NOT_FOUND)
    }

    @Test
    fun `saveCartItem으로 상품을 장바구니에 저장한다`() {
        every { repository.saveCart(any()) } returns Result.success(1)
        viewModel.onAddToCart(cartProduct)
        Thread.sleep(1000)
        verify(exactly = 1) { repository.saveCart(any()) }
    }
}
