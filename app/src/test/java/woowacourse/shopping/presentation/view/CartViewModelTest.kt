package woowacourse.shopping.presentation.view

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.fixture.FakeCartRepository
import woowacourse.shopping.fixture.productsFixture
import woowacourse.shopping.presentation.model.FetchPageDirection
import woowacourse.shopping.presentation.view.cart.CartViewModel
import woowacourse.shopping.presentation.view.util.InstantTaskExecutorExtension
import woowacourse.shopping.presentation.view.util.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var viewModel: CartViewModel
    private val dummyCartItems =
        productsFixture.take(10).map {
            CartItem(it, 2)
        }

    @BeforeEach
    fun setUp() {
        val fakeCartRepository = FakeCartRepository(dummyCartItems)
        viewModel =
            CartViewModel(fakeCartRepository)
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
            { assertThat(newItems.last().productId).isNotEqualTo(target.productId) },
        )
    }

    @Test
    fun `다음 페이지에 조회 가능한 상품 여부이 있다면 다음 페이지 조회 가능 상태가 True로 설정된다`() {
        // When
        val hasMore = viewModel.hasMore.getOrAwaitValue()

        // Then
        assertThat(hasMore).isTrue()
    }

    @Test
    fun `특정_상품의_구매_수량을_증가시킬_수_있다`() {
        // When
        val before = viewModel.cartItems.getOrAwaitValue()
        val target = before.first()
        viewModel.addProductToCart(target.productId)

        // Then
        val after = viewModel.cartItems.getOrAwaitValue()
        assertThat(after.first().quantity).isGreaterThan(target.quantity)
    }

    @Test
    fun `특정_상품의_구매_수량을_감소시킬_수_있다`() {
        // Give
        val before = viewModel.cartItems.getOrAwaitValue()
        val target = before.first()

        // When
        viewModel.removeProductFromCart(target.productId)

        // Then
        val after = viewModel.cartItems.getOrAwaitValue()
        assertThat(after.first().quantity).isLessThan(target.quantity)
    }

    @Test
    fun `구매_수량이_1인_특정_상품의_구매_수량을_감소시키면_삭제된다`() {
        // When
        val before = viewModel.cartItems.getOrAwaitValue()
        val target = before.first()
        viewModel.removeProductFromCart(target.productId)
        viewModel.removeProductFromCart(target.productId)

        // Then
        val after = viewModel.cartItems.getOrAwaitValue()
        assertThat(after).doesNotContain(target)
    }
}
