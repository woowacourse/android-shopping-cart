package woowacourse.shopping.presentation.ui.productdetail

import androidx.lifecycle.SavedStateHandle
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.repsoitory.DummyData.STUB_PRODUCT_A
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class, MockKExtension::class)
class ProductDetailViewModelTest {
    private lateinit var viewModel: ProductDetailViewModel

    private lateinit var savedStateHandle: SavedStateHandle

    @MockK
    private lateinit var shoppingCartRepository: ShoppingCartRepository

    @MockK
    private lateinit var productRepository: ProductRepository

    private val productId = 1

    @BeforeEach
    fun setUp() {
        every { productRepository.findProductById(productId) } returns Result.success(STUB_PRODUCT_A)

        val initialState = mapOf(ProductDetailActivity.PUT_EXTRA_PRODUCT_ID to productId)
        savedStateHandle = SavedStateHandle(initialState)
        viewModel =
            ProductDetailViewModel(savedStateHandle, productRepository, shoppingCartRepository)
    }

    @Test
    fun `선택한 상품의 상세 정보를 불러온다`() {
        // then
        val actual = viewModel.product.getOrAwaitValue()
        assertThat(actual).isEqualTo(STUB_PRODUCT_A)
    }

    @Test
    fun `선택한 상품을 장바구니에 추가한다`() {
        // given
        val productSlot = CapturingSlot<Product>()
        every { shoppingCartRepository.addOrder(capture(productSlot)) } just runs

        // when
        viewModel.addToCart()

        // then
        assertThat(productSlot.captured).isEqualTo(STUB_PRODUCT_A)
    }
}
