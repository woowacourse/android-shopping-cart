package woowacourse.shopping.presentation.detail

import androidx.lifecycle.SavedStateHandle
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.model.product.CartableProduct
import woowacourse.shopping.data.model.product.Product
import woowacourse.shopping.domain.repository.FakeCartRepository
import woowacourse.shopping.domain.repository.FakeProductHistoryRepository
import woowacourse.shopping.domain.repository.FakeProductRepository
import woowacourse.shopping.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class DetailViewModelTest {
    private lateinit var detailViewModel: DetailViewModel

    @BeforeEach
    fun setUp() {
        detailViewModel =
            DetailViewModel(
                FakeProductRepository(),
                FakeCartRepository(),
                FakeProductHistoryRepository(),
                1,
                2,
                SavedStateHandle(),
            )
    }

    @Test
    fun `상품 아이디를 활용하여 상품 상세 정보를 불러온다`() {
        val productInformation = detailViewModel.productInformation.getOrAwaitValue()

        assertThat(productInformation).isEqualTo(
            CartableProduct(
                product =
                    Product(
                        id = 1,
                        name = "사과1",
                        imageSource = "image1",
                        price = 1000,
                    ),
                cartItem = null,
            ),
        )
    }

    @Test
    fun `상품을 장바구니에 추가할 수 있다`() {
        detailViewModel.onQuantityChange(1, 1)
        detailViewModel.updateCartStatus(1)
        assertThat(detailViewModel.productInformation.getOrAwaitValue().quantity).isEqualTo(1)
    }

    @Test
    fun `이미 장바구니에 존재하는 아이템의 수량을 0 이상으로 설정한 경우 수량을 변경할 수 있다`() {
        detailViewModel.onQuantityChange(1, 1)
        detailViewModel.updateCartStatus(1)
        detailViewModel.onQuantityChange(1, 2)
        detailViewModel.updateCartStatus(1)
        assertThat(detailViewModel.productInformation.getOrAwaitValue().quantity).isEqualTo(2)
    }

    @Test
    fun `상품 수량이 양수로 변경될 수 있다`() {
        detailViewModel.onQuantityChange(1, 2)
        assertThat(detailViewModel.productInformation.getOrAwaitValue().quantity).isEqualTo(2)
    }

    @Test
    fun `상품 수량이 음수로는 변경되지 않는다`() {
        detailViewModel.onQuantityChange(1, 1)
        detailViewModel.onQuantityChange(1, -1)
        assertThat(detailViewModel.productInformation.getOrAwaitValue().quantity).isEqualTo(1)
    }
}
