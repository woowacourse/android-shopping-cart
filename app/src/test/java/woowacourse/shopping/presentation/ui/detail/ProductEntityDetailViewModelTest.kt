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
import woowacourse.shopping.domain.ProductCartRepository
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.detail.ProductDetailViewModel.Companion.PRODUCT_NOT_FOUND
import woowacourse.shopping.product

@ExtendWith(InstantTaskExecutorExtension::class)
@ExtendWith(MockKExtension::class)
class ProductEntityDetailViewModelTest {
    @MockK
    private lateinit var productRepository: ProductRepository

    @MockK
    private lateinit var productCartRepository: ProductCartRepository

    @InjectMockKs
    private lateinit var viewModel: ProductDetailViewModel

    @Test
    fun `loadById로 특정 상품의 데이터를 가져온다`() {
        every { productRepository.findById(any()) } returns Result.success(product)
        viewModel.findById(0)
        Assertions.assertEquals(viewModel.products.getOrAwaitValue(1), UiState.Success(product))
    }

    @Test
    fun `loadById로 특정 상품의 데이터를 가져오기 실패하면 Error state로 전환한다`() {
        every { productRepository.findById(any()) } returns Result.failure(Throwable())
        viewModel.findById(0)
        Assertions.assertEquals(viewModel.errorHandler.getOrAwaitValue(1).peekContent(), PRODUCT_NOT_FOUND)
    }

    @Test
    fun `saveCartItem으로 상품을 장바구니에 저장한다`() {
        every { productCartRepository.save(any()) } returns Result.success(1)
        viewModel.saveCartItem(product)
        verify(exactly = 1) { productCartRepository.save(any()) }
    }
}
