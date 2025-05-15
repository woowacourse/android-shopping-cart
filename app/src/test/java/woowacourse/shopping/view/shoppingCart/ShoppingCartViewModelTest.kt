package woowacourse.shopping.view.shoppingCart

import io.mockk.Awaits
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.shoppingCart.repository.ShoppingCartRepository
import woowacourse.shopping.fixture.PRODUCT_AIDA

class ShoppingCartViewModelTest {
    private lateinit var repository: ShoppingCartRepository
    private lateinit var viewModel: ShoppingCartViewModel

    @BeforeEach
    fun setUp() {
        repository = mockk()
        viewModel = ShoppingCartViewModel(repository)
    }

    @Test
    fun `장바구니에 담은 상품들을 보여준다`() {
        // given
        every { repository.hasNext } just Awaits
        every { repository.hasPrevious } just Awaits
        every { repository.load(any(), any()) } just Awaits

        // when
        viewModel.updateShoppingCart()

        // then
        verify { repository.load(any(), any()) }
    }

    @Test
    fun `제거하고 싶은 상품을 장바구니에서 삭제할 수 있다`() {
        // given:
        every { repository.remove(any()) } just Awaits

        // when:
        viewModel.removeShoppingCartProduct(PRODUCT_AIDA)

        // then:
        verify { repository.remove(any()) }
    }

    @Test
    fun `장바구니의 다음 페이지 정보를 확인할 수 있다`() {
        // given:
        every { repository.hasNext } just Awaits
        every { repository.hasPrevious } just Awaits
        every { repository.load(any(), any()) } just Awaits

        // when:
        viewModel.plusPage()

        // then:
        verify { repository.load(any(), any()) }
    }

    @Test
    fun `장바구니의 이전 페이지 정보를 확인할 수 있다`() {
        // given:
        every { repository.hasNext } just Awaits
        every { repository.hasPrevious } just Awaits
        every { repository.load(any(), any()) } just Awaits

        // when:
        viewModel.minusPage()

        // then:
        verify { repository.load(any(), any()) }
    }
}
