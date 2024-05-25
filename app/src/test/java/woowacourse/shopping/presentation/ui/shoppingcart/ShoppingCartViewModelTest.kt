package woowacourse.shopping.presentation.ui.shoppingcart

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.remote.api.DummyData.CART_PRODUCTS
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@ExtendWith(MockKExtension::class, InstantTaskExecutorExtension::class)
class ShoppingCartViewModelTest {
    private lateinit var viewModel: ShoppingCartViewModel

    @MockK
    private lateinit var repository: ShoppingCartRepository

    @BeforeEach
    fun setUp() {
        every { repository.getCartProductsPaged(0, 5) } returns Result.success(CART_PRODUCTS.subList(0, 5).map { it.toDomain() })
        every { repository.getCartProductsPaged(1, 5) } returns Result.success(CART_PRODUCTS.subList(5, 10).map { it.toDomain() })
        every { repository.getCartProductsTotal() } returns Result.success(60)

        viewModel = ShoppingCartViewModel(repository)
        val latch = CountDownLatch(1)
        latch.await(1, TimeUnit.SECONDS)
    }

    @Test
    fun `첫 번째 페이지에 장바구니를 불러온다`() {
        // then
        val actual = viewModel.uiState.getOrAwaitValue()
        assertThat(actual.pagingCartProduct.products).isEqualTo(CART_PRODUCTS.subList(0, 5).map { it.toDomain() })
    }

    @Test
    fun `주문을 삭제하면 장바구니에 주문이 사라진다`() {
        // given
        val productIdSlot = slot<Long>()
        every { repository.deleteCartProduct(capture(productIdSlot)) } returns Result.success(Unit)
        every { repository.getCartProductsPaged(0, 5) } returns Result.success(CART_PRODUCTS.subList(1, 5).map { it.toDomain() })

        // when
        viewModel.deleteCartProduct(CART_PRODUCTS.first().id)

        // then
        val latch = CountDownLatch(1)
        latch.await(1, TimeUnit.SECONDS)

        verify { repository.deleteCartProduct(CART_PRODUCTS.first().id) }
        assertThat(productIdSlot.captured).isEqualTo(CART_PRODUCTS.first().id)

        val actual = viewModel.uiState.getOrAwaitValue()
        assertThat(actual.pagingCartProduct.products).isEqualTo(CART_PRODUCTS.subList(1, 5).map { it.toDomain() })
    }

    @Test
    fun `첫 번째 페이지에서 다음 페이지로 넘어가면 다음 페이지 장바구니를 불러온다`() {
        // when
        viewModel.loadNextPage()

        Thread.sleep(3000)
        // then
        val actual = viewModel.uiState.getOrAwaitValue()
        assertThat(actual.pagingCartProduct.products).isEqualTo(CART_PRODUCTS.subList(5, 10).map { it.toDomain() })
    }

    @Test
    fun `두 번째 페이지에서 이전 페이지로 넘어가면 다음 페이지 장바구니를 불러온다`() {
        // given
        viewModel.loadNextPage()

        // when
        viewModel.loadPreviousPage()

        // then
        val actual = viewModel.uiState.getOrAwaitValue()
        assertThat(actual.pagingCartProduct.products).isEqualTo(CART_PRODUCTS.subList(0, 5).map { it.toDomain() })
    }
}
