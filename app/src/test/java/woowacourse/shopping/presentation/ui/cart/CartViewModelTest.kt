package woowacourse.shopping.presentation.ui.cart

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.domain.repository.CartRepository

@ExtendWith(InstantTaskExecutorExtension::class)
@ExtendWith(MockKExtension::class)
class CartViewModelTest {
    @RelaxedMockK
    private lateinit var cartRepository: CartRepository

    @InjectMockKs
    private lateinit var viewModel: CartViewModel

    @BeforeEach
    fun setUp() {
        every { cartRepository.getMaxPage(any()) } returns Result.success(0) // 초기 오프셋 처리
        viewModel = CartViewModel(cartRepository)
    }

//    @Test
//    fun `카트 아이템을 pageCount개씩 불러온다`() {
//        every { cartRepository.load(any(), any()) } returns Result.success(dummyCartProducts)
//        viewModel.loadProductByPage(0)
//        assertThat(viewModel.shoppingProducts.getOrAwaitValue(3)).isEqualTo(
//            UiState.Success(
//                dummyShoppingProducts,
//            ),
//        )
//    }
//
//    @Test
//    fun `카트 아이템을 불러오기 실패하면 해당하는 Error 상태로 변화한다`() {
//        every { cartRepository.load(any(), any()) } returns Result.failure(Throwable())
//        viewModel.loadProductByPage(0)
//        assertThat(
//            viewModel.error.getOrAwaitValue(1).getContentIfNotHandled(),
//        ).isEqualTo(ShoppingError.CartItemsNotFound)
//    }
//
//    @Test
//    fun `데이터를 삭제한 뒤에 새로운 데이터를 불러온다`() {
//        every { cartRepository.deleteProduct(any()) } returns Result.success(0)
//        every { cartRepository.getMaxPage(any()) } returns Result.success(0)
//        every { cartRepository.load(any(), any()) } returns Result.success(dummyCartProducts)
//        viewModel.deleteProduct(dummyProduct)
//        assertThat(viewModel.shoppingProducts.getOrAwaitValue(3)).isEqualTo(
//            UiState.Success(
//                dummyShoppingProducts,
//            ),
//        )
//    }
//
//    @Test
//    fun `데이터 삭제에 실패하면 해당하는 Error 상태로 변화한다`() {
//        every { cartRepository.deleteProduct(any()) } returns Result.failure(Throwable())
//        viewModel.deleteProduct(dummyProduct)
//        assertThat(
//            viewModel.error.getOrAwaitValue(1).getContentIfNotHandled(),
//        ).isEqualTo(ShoppingError.CartItemNotDeleted)
//    }
}
