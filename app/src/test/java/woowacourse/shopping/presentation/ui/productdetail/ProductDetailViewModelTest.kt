package woowacourse.shopping.presentation.ui.productdetail

import androidx.lifecycle.SavedStateHandle
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.repository.ProductHistoryRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.remote.api.DummyData.STUB_PRODUCT_A

@ExtendWith(InstantTaskExecutorExtension::class, MockKExtension::class)
class ProductDetailViewModelTest {
    private lateinit var viewModel: ProductDetailViewModel

    private lateinit var savedStateHandle: SavedStateHandle

    @MockK
    private lateinit var shoppingCartRepository: ShoppingCartRepository

    @MockK
    private lateinit var productRepository: ProductRepository

    @MockK
    private lateinit var productHistoryRepository: ProductHistoryRepository

    private val productId = 1L

    @BeforeEach
    fun setUp() {
        every { productRepository.findProductById(productId) } returns Result.success(STUB_PRODUCT_A.toDomain())
        every { productHistoryRepository.getProductHistory(any()) } returns Result.success(emptyList())
        every { shoppingCartRepository.getAllCartProducts() } returns Result.success(emptyList())
        every { shoppingCartRepository.findCartProduct(1L) } returns Result.success(STUB_PRODUCT_A.toDomain())

        val initialState = mapOf(ProductDetailActivity.PUT_EXTRA_PRODUCT_ID to productId)
        savedStateHandle = SavedStateHandle(initialState)
        viewModel =
            ProductDetailViewModel(
                savedStateHandle,
                productRepository,
                shoppingCartRepository,
                productHistoryRepository,
            )
    }

    @Test
    fun `선택한 상품의 상세 정보를 불러온다`() {
        // then
        Thread.sleep(3000)
        val actual = viewModel.uiState.getOrAwaitValue()
        assertThat(actual.product).isEqualTo(STUB_PRODUCT_A.toDomain())
    }

    @Test
    fun `선택한 상품을 장바구니에 추가한다`() {
        // given
        val productIdSlot = CapturingSlot<Long>()
        val nameSlot = CapturingSlot<String>()
        val priceSlot = CapturingSlot<Int>()
        val quantitySlot = CapturingSlot<Int>()
        val imageUrlSlot = CapturingSlot<String>()

        every {
            shoppingCartRepository.insertCartProduct(
                capture(productIdSlot),
                capture(nameSlot),
                capture(priceSlot),
                capture(quantitySlot),
                capture(imageUrlSlot),
            )
        } returns Result.success(Unit)

        every {
            productHistoryRepository.insertProductHistory(
                any(),
                any(),
                any(),
                any(),
            )
        } returns Result.success(Unit)

        // when
        viewModel.addToCart()

        // then
        assertAll(
            { assertThat(productIdSlot.captured).isEqualTo(STUB_PRODUCT_A.id) },
            { assertThat(nameSlot.captured).isEqualTo(STUB_PRODUCT_A.name) },
            { assertThat(priceSlot.captured).isEqualTo(STUB_PRODUCT_A.price) },
            { assertThat(quantitySlot.captured).isEqualTo(0) },
            { assertThat(imageUrlSlot.captured).isEqualTo(STUB_PRODUCT_A.imageUrl) },
        )
    }
}
