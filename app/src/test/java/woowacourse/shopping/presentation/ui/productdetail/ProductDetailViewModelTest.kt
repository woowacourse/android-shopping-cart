package woowacourse.shopping.presentation.ui.productdetail

import androidx.lifecycle.SavedStateHandle
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.repsoitory.DummyProductList
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
@ExtendWith(MockKExtension::class)
class ProductDetailViewModelTest {
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: ProductDetailViewModel

    @MockK
    private lateinit var shoppingCartRepository: ShoppingCartRepository

    @BeforeEach
    fun setUp() {
        val initialState = mutableMapOf(ProductDetailActivity.PUT_EXTRA_PRODUCT_ID to 1)
        savedStateHandle = SavedStateHandle(initialState)
        viewModel =
            ProductDetailViewModel(savedStateHandle, DummyProductList, shoppingCartRepository)
    }

    @Test
    fun `선택한 상품의 상세 정보를 불러온다`() {
        // given & when
        val actual = viewModel.product.getOrAwaitValue()

        // then
        val expected = DummyProductList.findProductById(1).getOrThrow()
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `선택한 상품을 장바구니에 추가한다`() {
        // given
        every { shoppingCartRepository.addOrder(any()) } just runs

        // when
        viewModel.onAddToCartButtonClick()

        // then
        val product = DummyProductList.findProductById(1).getOrThrow()
        verify { shoppingCartRepository.addOrder(product) }
    }
}
