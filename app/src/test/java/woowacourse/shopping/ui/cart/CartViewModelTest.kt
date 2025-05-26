package woowacourse.shopping.ui.cart

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
import woowacourse.shopping.domain.usecase.DecreaseCartProductQuantityUseCase
import woowacourse.shopping.domain.usecase.GetCartProductsUseCase
import woowacourse.shopping.domain.usecase.IncreaseCartProductQuantityUseCase
import woowacourse.shopping.domain.usecase.RemoveCartProductUseCase
import woowacourse.shopping.model.DUMMY_CART_PRODUCTS_1
import woowacourse.shopping.model.DUMMY_PRODUCT_1
import woowacourse.shopping.util.InstantTaskExecutorExtension
import woowacourse.shopping.util.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var getCartProductsUseCase: GetCartProductsUseCase
    private lateinit var removeCartProductUseCase: RemoveCartProductUseCase
    private lateinit var increaseCartProductQuantityUseCase: IncreaseCartProductQuantityUseCase
    private lateinit var decreaseCartProductQuantityUseCase: DecreaseCartProductQuantityUseCase
    private lateinit var viewModel: CartViewModel

    @BeforeEach
    fun setup() {
        getCartProductsUseCase = mockk()
        removeCartProductUseCase = mockk()
        increaseCartProductQuantityUseCase = mockk()
        decreaseCartProductQuantityUseCase = mockk()

        every {
            getCartProductsUseCase.invoke(any(), any(), any())
        } answers {
            thirdArg<(CartProducts) -> Unit>().invoke(DUMMY_CART_PRODUCTS_1)
        }

        viewModel =
            CartViewModel(
                getCartProductsUseCase,
                removeCartProductUseCase,
                increaseCartProductQuantityUseCase,
                decreaseCartProductQuantityUseCase,
            )
    }

    @Test
    fun `장바구니 상품 목록을 불러온다`() {
        // given & when & then
        assertThat(viewModel.cartProducts.getOrAwaitValue().products).hasSize(5)
        assertThat(viewModel.cartProducts.getOrAwaitValue().products).containsExactlyElementsIn(DUMMY_CART_PRODUCTS_1.products)
    }

    @Test
    fun `장바구니 상품 수량을 증가시키고 수정된 상품 목록에 추가한다`() {
        // given
        val productId = DUMMY_PRODUCT_1.id
        val newQuantity = 10

        every {
            increaseCartProductQuantityUseCase.invoke(eq(productId), any(), any())
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
                .find { it.product.id == productId }
        assertThat(updatedProduct?.quantity).isEqualTo(newQuantity)
        assertThat(viewModel.editedProductIds.getOrAwaitValue()).contains(productId)
    }

    @Test
    fun `장바구니 상품 수량을 감소시키고 장바구니 상품 목록을 불러온 뒤 수정된 상품 목록에 추가한다`() {
        // given
        val productId = DUMMY_PRODUCT_1.id

        every {
            decreaseCartProductQuantityUseCase.invoke(eq(productId), any(), any())
        } answers {
            thirdArg<(Int) -> Unit>().invoke(0)
        }

        // when
        viewModel.decreaseCartProductQuantity(productId)

        // then
        verify { getCartProductsUseCase.invoke(any(), any(), any()) }
        assertThat(viewModel.editedProductIds.getOrAwaitValue()).contains(productId)
    }

    @Test
    fun `장바구니 상품을 제거하고 상품 목록을 불러온다`() {
        // given
        val productId = DUMMY_PRODUCT_1.id

        every { removeCartProductUseCase.invoke(productId) } just Runs
        every { getCartProductsUseCase.invoke(any(), any(), any()) } answers {
            thirdArg<(CartProducts) -> Unit>().invoke(DUMMY_CART_PRODUCTS_1)
        }

        // when
        viewModel.removeCartProduct(productId)

        // then
        verify { removeCartProductUseCase.invoke(productId) }
        verify { getCartProductsUseCase.invoke(any(), any(), any()) }
        assertThat(viewModel.editedProductIds.getOrAwaitValue()).contains(productId)
    }

    @Test
    fun `장바구니 상품 페이지를 증가시키고 상품 목록을 불러온다`() {
        // given
        clearMocks(getCartProductsUseCase)

        every {
            getCartProductsUseCase.invoke(any(), any(), any())
        } answers {
            thirdArg<(CartProducts) -> Unit>().invoke(DUMMY_CART_PRODUCTS_1)
        }

        // when
        viewModel.increasePage()

        // then
        verify { getCartProductsUseCase.invoke(2, 5, any()) }
        assertThat(viewModel.pageState.getOrAwaitValue().current).isEqualTo(2)
    }

    @Test
    fun `장바구니 최대 상품 페이지 수를 불러온다`() {
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
