package woowacourse.shopping.presentation.ui.productdetail

import androidx.lifecycle.SavedStateHandle
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.repsoitory.DummyData.STUB_PRODUCT_A
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
@ExtendWith(MockKExtension::class)
class ProductDetailViewModelTest {
    private lateinit var viewModel: ProductDetailViewModel

    private lateinit var savedStateHandle: SavedStateHandle

    @MockK
    private lateinit var shoppingCartRepository: ShoppingCartRepository

    @MockK
    private lateinit var productRepository: ProductRepository

    private val productId = 1

    private fun initViewModel() {
        val initialState = mutableMapOf(ProductDetailActivity.PUT_EXTRA_PRODUCT_ID to productId)
        savedStateHandle = SavedStateHandle(initialState)
        viewModel =
            ProductDetailViewModel(savedStateHandle, productRepository, shoppingCartRepository)
    }

    @Test
    fun `선택한 상품의 상세 정보를 불러온다`() {
        // given & when
        every { productRepository.findProductById(productId) } returns Result.success(STUB_PRODUCT_A)
        initViewModel()

        // then
        val actual = viewModel.product.getOrAwaitValue()
        assert(actual == STUB_PRODUCT_A)
    }

    @Test
    fun `선택한 상품을 장바구니에 추가한다`() {
        // given
        every { productRepository.findProductById(productId) } returns Result.success(STUB_PRODUCT_A)
        every { shoppingCartRepository.addOrder(STUB_PRODUCT_A) } just runs
        initViewModel()

        // when
        viewModel.addToCart()

        // then
        verify { shoppingCartRepository.addOrder(STUB_PRODUCT_A) }
    }
}
