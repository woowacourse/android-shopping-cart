package woowacourse.shopping.presentation.detail

import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.FakeProductRepository

@ExtendWith(InstantTaskExecutorExtension::class)
class DetailViewModelTest {
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var cartRepository: CartRepository
    private lateinit var addCompleteListener: AddCompleteListener

    @BeforeEach
    fun setUp() {
        cartRepository = mockk<CartRepository>(relaxed = true)
        addCompleteListener = mockk<AddCompleteListener>(relaxed = true)
        detailViewModel = DetailViewModel(FakeProductRepository(), cartRepository)
    }

    @Test
    fun `상품 아이디를 활용하여 상품 상세 정보를 불러온다`() {
        detailViewModel.loadProductInformation(1)

        val productInformation = detailViewModel.productInformation.value

        assertThat(productInformation).isEqualTo(
            Product(1, "Product 1", "", 1000),
        )
    }

    @Test
    fun `장바구니에 상품을 담을 수 있다`() {
        detailViewModel.addToCart(1, addCompleteListener)

        verify { cartRepository.addCartItem(1, any()) }
    }
}
