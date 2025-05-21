package woowacourse.shopping.presentation.view

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.fixture.FakeShoppingRepository
import woowacourse.shopping.fixture.dummyProductsFixture
import woowacourse.shopping.presentation.model.FetchPageDirection
import woowacourse.shopping.presentation.view.cart.CartViewModel
import woowacourse.shopping.presentation.view.util.InstantTaskExecutorExtension
import woowacourse.shopping.presentation.view.util.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var viewModel: CartViewModel
    private lateinit var fakeRepository: ShoppingRepository
    private val dummyCartItems =
        dummyProductsFixture
            .take(15)
            .associate { it.id to 1 }
            .toMutableMap()

    @BeforeEach
    fun setUp() {
        fakeRepository =
            FakeShoppingRepository(
                dummyProductsFixture,
                dummyCartItems,
            )
        viewModel = CartViewModel(fakeRepository)
    }

    @Test
    fun `초기화 시 장바구니 아이템이 로드된다`() {
        // When
        val items = viewModel.cartItems.getOrAwaitValue()

        // Then
        assertAll(
            { assertThat(items).isNotNull },
            { assertThat(items).isNotEmpty },
        )
    }

    @Test
    fun `다음 페이지 요청 시 페이지 증가 및 아이템이 추가된다`() {
        // Given
        val before = viewModel.cartItems.getOrAwaitValue()

        // When
        viewModel.fetchCartItems(FetchPageDirection.NEXT)
        val after = viewModel.cartItems.getOrAwaitValue()

        // Then
        assertAll(
            { assertThat(after.size).isGreaterThanOrEqualTo(before.size) },
            { assertThat(viewModel.page.getOrAwaitValue()).isEqualTo(2) },
        )
    }

    @Test
    fun `삭제 성공 시 현재 페이지를 다시 조회한다`() {
        // Given
        val items = viewModel.cartItems.getOrAwaitValue()
        val target = items.last()

        // When
        viewModel.deleteCartItem(target)
        val newItems = viewModel.cartItems.getOrAwaitValue()

        // Then
        assertAll(
            { assertThat(newItems).doesNotContain(target) },
            { assertThat(newItems.size).isEqualTo(items.size) },
            { assertThat(newItems.last().product.id).isNotEqualTo(target.product.id) },
        )
    }

    @Test
    fun `다음 페이지에 조회 가능한 상품 여부이 있다면 다음 페이지 조회 가능 상태가 True로 설정된다`() {
        // When
        val hasMore = viewModel.hasMore.getOrAwaitValue()

        // Then
        assertThat(hasMore).isTrue()
    }
}
