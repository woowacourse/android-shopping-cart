package woowacourse.shopping.ui.viewmodel

import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.model.CartProducts
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.model.DUMMY_CART_PRODUCTS
import woowacourse.shopping.model.DUMMY_PRODUCT_1
import woowacourse.shopping.ui.cart.CartViewModel
import woowacourse.shopping.util.InstantTaskExecutorExtension
import woowacourse.shopping.util.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var cartRepository: CartRepository
    private lateinit var viewModel: CartViewModel

    @BeforeEach
    fun setup() {
        cartRepository = mockk(relaxed = true)

        every {
            cartRepository.fetchCartProducts(any(), any(), any())
        } answers {
            thirdArg<(CartProducts) -> Unit>().invoke(DUMMY_CART_PRODUCTS)
        }

        viewModel = CartViewModel(cartRepository)
    }

    @Test
    fun `loadCartProducts가 호출되면 LiveData에 DUMMY_CART_PRODUCTS가 저장된다`() {
        // when
        viewModel = CartViewModel(cartRepository)

        // then
        assertThat(viewModel.cartProducts.getOrAwaitValue().products).hasSize(5)
        assertThat(viewModel.cartProducts.getOrAwaitValue().products).containsExactlyElementsIn(DUMMY_CART_PRODUCTS.products)
    }

    @Test
    fun `increaseCartProductQuantity가 호출되면 수량을 증가시키고 editedProductIds에 추가하며 상태를 업데이트한다`() {
        // given
        val productId = DUMMY_PRODUCT_1.id
        val newQuantity = 10

        every {
            cartRepository.increaseProductQuantity(eq(productId), any(), any())
        } answers {
            thirdArg<(Int) -> Unit>().invoke(newQuantity)
        }

        // when
        viewModel.increaseCartProductQuantity(productId)

        // then
        val updatedProduct =
            viewModel.cartProducts
                .getOrAwaitValue()
                .products
                ?.find { it.product.id == productId }

        assertThat(updatedProduct?.quantity).isEqualTo(newQuantity)
        assertThat(viewModel.editedProductIds.getOrAwaitValue()).contains(productId)
    }

    @Test
    fun `decreaseCartProductQuantity는 수량 감소 후 0일 경우 editedProductIds에 추가하며 상품을 다시 불러온다`() {
        // given
        val productId = DUMMY_PRODUCT_1.id

        every {
            cartRepository.decreaseProductQuantity(eq(productId), any(), any())
        } answers {
            thirdArg<(Int) -> Unit>().invoke(0)
        }

        // when
        viewModel.decreaseCartProductQuantity(productId)

        // then
        verify { cartRepository.fetchCartProducts(any(), any(), any()) }
        assertThat(viewModel.editedProductIds.getOrAwaitValue()).contains(productId)
    }

    @Test
    fun `removeCartProduct는 제품을 삭제하고 editedProductIds에 추가하며 상품을 다시 불러온다`() {
        // given
        val productId = DUMMY_PRODUCT_1.id

        every {
            cartRepository.removeCartProduct(productId)
        } just Runs

        // when
        viewModel.removeCartProduct(productId)

        // then
        verify { cartRepository.removeCartProduct(productId) }
        verify { cartRepository.fetchCartProducts(any(), any(), any()) }

        assertThat(viewModel.editedProductIds.getOrAwaitValue()).contains(productId)
    }

    @Test
    fun `increasePage 호출 시 페이지 증가하고 데이터 다시 불러온다`() {
        // given
        clearMocks(cartRepository)

        every {
            cartRepository.fetchCartProducts(any(), any(), any())
        } answers {
            thirdArg<(CartProducts) -> Unit>().invoke(DUMMY_CART_PRODUCTS)
        }

        // when
        viewModel.increasePage()

        // then
        verify { cartRepository.fetchCartProducts(2, 5, any()) }
        assertThat(viewModel.pageState.getOrAwaitValue().current).isEqualTo(2)
    }

    @Test
    fun `updateTotalPage는 pageState의 total 값을 업데이트한다`() {
        // given
        val expected = 100

        // when
        viewModel.updateTotalPage(expected)

        // then
        assertThat(viewModel.pageState.getOrAwaitValue().total).isEqualTo(expected)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }
}
