package woowacourse.shopping.view.productDetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.invoke
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.shoppingCart.repository.ShoppingCartRepository
import woowacourse.shopping.fixture.PRODUCT_LUCKY
import woowacourse.shopping.view.common.InstantTaskExecutorExtension
import woowacourse.shopping.view.common.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var productDetailViewModel: ProductDetailViewModel
    private lateinit var shoppingCartRepository: ShoppingCartRepository

    @BeforeEach
    fun setUp() {
        shoppingCartRepository = mockk()
        productDetailViewModel = ProductDetailViewModel(shoppingCartRepository)
    }

    @Test
    fun `상품을 업데이트하면 product의 값이 해당 상품이 된다`() {
        // given:
        // when:
        productDetailViewModel.updateProduct(PRODUCT_LUCKY)

        // then:
        val result = productDetailViewModel.product.getOrAwaitValue()
        assertThat(result).isEqualTo(PRODUCT_LUCKY)
    }

    @Test
    fun `쇼핑카트에 상품을 추가했을 때 성공 시 휘발성 이벤트 ADD_SHOPPING_CART_SUCCESS 값을 가진다`() {
        // given:
        productDetailViewModel.updateProduct(PRODUCT_LUCKY)
        every { shoppingCartRepository.add(PRODUCT_LUCKY, captureLambda()) } answers {
            lambda<(Result<Unit>) -> Unit>().invoke(Result.success(Unit))
        }

        // when:
        productDetailViewModel.addToShoppingCart()

        // then:
        val eventResult = productDetailViewModel.event.getValue()
        assertThat(eventResult).isEqualTo(ProductDetailEvent.ADD_SHOPPING_CART_SUCCESS)
    }

    @Test
    fun `쇼핑카트에 상품을 추가했을 때 실패 시 휘발성 이벤트 ADD_SHOPPING_CART_FAILURE 값을 가진다`() {
        // given:
        productDetailViewModel.updateProduct(PRODUCT_LUCKY)
        every { shoppingCartRepository.add(PRODUCT_LUCKY, captureLambda()) } answers {
            lambda<(Result<Unit>) -> Unit>().invoke(Result.failure(RuntimeException()))
        }

        // when:
        productDetailViewModel.addToShoppingCart()

        // then:
        val eventResult = productDetailViewModel.event.getValue()
        assertThat(eventResult).isEqualTo(ProductDetailEvent.ADD_SHOPPING_CART_FAILURE)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }
}
