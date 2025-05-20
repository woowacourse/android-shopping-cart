package woowacourse.shopping.view.shoppingCart

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
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.shoppingCart.repository.ShoppingCartRepository
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.fixture.SHOPPING_CART_PRODUCT_ITEMS_4_PAGE_1
import woowacourse.shopping.fixture.SHOPPING_CART_PRODUCT_ITEMS_4_PAGE_2
import woowacourse.shopping.fixture.SHOPPING_CART_PRODUCT_ITEMS_5_PAGE_1MORE
import woowacourse.shopping.fixture.getProducts
import woowacourse.shopping.view.common.InstantTaskExecutorExtension
import woowacourse.shopping.view.common.getOrAwaitValue
import woowacourse.shopping.view.shoppingCart.ShoppingCartItem.PaginationItem
import woowacourse.shopping.view.shoppingCart.ShoppingCartItem.ProductItem

@ExtendWith(InstantTaskExecutorExtension::class)
class ShoppingCartViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var shoppingCartViewModel: ShoppingCartViewModel
    private lateinit var shoppingCartRepository: ShoppingCartRepository

    @BeforeEach
    fun setUp() {
        shoppingCartRepository = mockk()
        shoppingCartViewModel = ShoppingCartViewModel(shoppingCartRepository)
    }

    @Test
    fun `장바구니 업데이트 성공 시 상품 개수가 5개 이하일 경우 ProductItem 과 다음페이지와 이전페이지의 값이 false 인 PaginationItem 으로 구성된 shoppingCart 값이 된다`() {
        // given: 장바구니 업데이트 성공 시 상품 개수가 5개 이하일 경우
        every {
            shoppingCartRepository.load(any(), any(), captureLambda())
        } answers {
            lambda<(Result<List<Product>>) -> Unit>().invoke(
                Result.success(
                    getProducts(4),
                ),
            )
        }

        // when:
        shoppingCartViewModel.updateShoppingCart()

        // then: ProductItem 과 nextEnabled 와 previousEnabled 가 false 인 PaginationItem 으로 구성된 shoppingCart 값이 된다
        val result = shoppingCartViewModel.shoppingCart.getOrAwaitValue()
        assertAll({
            assertThat(result.count { it is ProductItem }).isEqualTo(4)
            assertThat(result.last()).isInstanceOf(PaginationItem::class.java)
            assertThat(result).isEqualTo(SHOPPING_CART_PRODUCT_ITEMS_4_PAGE_1)
        })
    }

    @Test
    fun `장바구니 업데이트 성공 시 상품 개수가 5개 초과일 경우 next 페이지가 존재하는 PaginationItem 이 포함된다`() {
        // given:
        every {
            shoppingCartRepository.load(any(), any(), captureLambda())
        } answers {
            lambda<(Result<List<Product>>) -> Unit>().invoke(Result.success(getProducts(6)))
        }

        // when:
        shoppingCartViewModel.updateShoppingCart()

        // then:
        val result = shoppingCartViewModel.shoppingCart.getOrAwaitValue()
        val pagination = result.last() as PaginationItem
        assertAll({
            assertThat(result.count { it is ProductItem }).isEqualTo(5)
            assertThat(pagination.nextEnabled).isTrue()
            assertThat(pagination.previousEnabled).isFalse()
            assertThat(result).isEqualTo(SHOPPING_CART_PRODUCT_ITEMS_5_PAGE_1MORE)
        })
    }

    @Test
    fun `장바구니 업데이트 실패 시 단발성 이벤트로 UPDATE_SHOPPING_CART_FAILURE 값을 가진다`() {
        // given:
        every {
            shoppingCartRepository.load(any(), any(), captureLambda())
        } answers {
            lambda<(Result<List<Product>>) -> Unit>().invoke(Result.failure(RuntimeException()))
        }

        // when:
        shoppingCartViewModel.updateShoppingCart()

        // then:
        val event = shoppingCartViewModel.event.getValue()
        assertThat(event).isEqualTo(ShoppingCartEvent.UPDATE_SHOPPING_CART_FAILURE)
    }

    @Test
    fun `장바구니에서 제거할 상품을 제거했을 때 성공 시 장바구니 업데이트가 다시 호출된다`() {
        // given:
        val productToRemove: Product = getProducts(1)[0]

        every {
            shoppingCartRepository.remove(eq(productToRemove), captureLambda())
        } answers {
            lambda<(Result<Unit>) -> Unit>().invoke(Result.success(Unit))
        }

        every {
            shoppingCartRepository.load(any(), any(), captureLambda())
        } answers {
            lambda<(Result<List<Product>>) -> Unit>().invoke(Result.success(emptyList()))
        }

        // when:
        shoppingCartViewModel.removeShoppingCartProduct(productToRemove)

        // then:
        val result = shoppingCartViewModel.shoppingCart.getOrAwaitValue()
        assertThat(result).isEqualTo(listOf(PaginationItem(1, false, false)))
    }

    @Test
    fun `removeShoppingCartProduct 실패 시 단발성 이벤트로 REMOVE_SHOPPING_CART_PRODUCT_FAILURE 값을 가진다`() {
        // given:
        val productToRemove: Product = getProducts(1)[0]

        every {
            shoppingCartRepository.remove(eq(productToRemove), captureLambda())
        } answers {
            lambda<(Result<Unit>) -> Unit>().invoke(Result.failure(RuntimeException()))
        }

        // when:
        shoppingCartViewModel.removeShoppingCartProduct(productToRemove)

        // then:
        val event = shoppingCartViewModel.event.getValue()
        assertThat(event).isEqualTo(ShoppingCartEvent.REMOVE_SHOPPING_CART_PRODUCT_FAILURE)
    }

    @Test
    fun `페이지를 증가하면 updateShoppingCart 가 호출되어 장바구니가 변경된다`() {
        // given:
        every {
            shoppingCartRepository.load(any(), any(), captureLambda())
        } answers {
            lambda<(Result<List<Product>>) -> Unit>().invoke(Result.success(getProducts(4)))
        }

        // when:
        shoppingCartViewModel.plusPage()

        // then:
        val result = shoppingCartViewModel.shoppingCart.getOrAwaitValue()
        assertAll({
            assertThat(result.count { it is ProductItem }).isEqualTo(4)
            assertThat(result).isEqualTo(SHOPPING_CART_PRODUCT_ITEMS_4_PAGE_2)
        })
    }

    @Test
    fun `페이지를 감소하면 updateShoppingCart 가 호출되어 장바구니가 변경된다`() {
        // given:
        every {
            shoppingCartRepository.load(any(), any(), captureLambda())
        } answers {
            lambda<(Result<List<Product>>) -> Unit>().invoke(Result.success(getProducts(4)))
        }

        // when:
        shoppingCartViewModel.minusPage()

        // then:
        val result = shoppingCartViewModel.shoppingCart.getOrAwaitValue()
        assertAll({
            assertThat(result.count { it is ProductItem }).isEqualTo(4)
            assertThat(result).isEqualTo(SHOPPING_CART_PRODUCT_ITEMS_4_PAGE_1)
        })
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }
}
