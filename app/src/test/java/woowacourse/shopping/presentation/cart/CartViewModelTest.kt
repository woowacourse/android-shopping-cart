package woowacourse.shopping.presentation.cart

import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.model.cart.CartItem
import woowacourse.shopping.data.model.cart.CartedProduct
import woowacourse.shopping.data.model.product.Product
import woowacourse.shopping.domain.repository.FakeCartRepository
import woowacourse.shopping.domain.repository.FakeProductRepository
import woowacourse.shopping.domain.repository.cart.CartRepository
import woowacourse.shopping.domain.repository.product.ProductRepository
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.presentation.home.products.ProductQuantity
import kotlin.concurrent.thread

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var cartViewModel: CartViewModel

    @BeforeEach
    fun setUp() {
        cartViewModel = CartViewModel(FakeCartRepository(), FakeProductRepository())
    }

    @Test
    fun `장바구니에 담아놓은 상품들을 불러올 수 있다`() {
        cartViewModel.loadCurrentPageCartItems()
        val orders = cartViewModel.cartableProducts.getOrAwaitValue()
        assertThat(orders).isEqualTo(
            listOf(
                CartedProduct(
                    cartItem = CartItem(id = 1, productId = 1, quantity = 1),
                    product = Product(id = 1, name = "사과1", imageSource = "image1", price = 1000),
                ),
                CartedProduct(
                    cartItem = CartItem(id = 2, productId = 2, quantity = 1),
                    product = Product(id = 2, name = "사과2", imageSource = "image2", price = 2000),
                ),
                CartedProduct(
                    cartItem = CartItem(id = 3, productId = 3, quantity = 1),
                    product = Product(id = 3, name = "사과3", imageSource = "image3", price = 3000),
                ),
                CartedProduct(
                    cartItem = CartItem(id = 4, productId = 4, quantity = 1),
                    product = Product(id = 4, name = "사과4", imageSource = "image4", price = 4000),
                ),
                CartedProduct(
                    cartItem = CartItem(id = 5, productId = 5, quantity = 1),
                    product = Product(id = 5, name = "사과5", imageSource = "image5", price = 5000),
                ),
            ),
        )
    }

    @Test
    fun `장바구니의 다음 페이지를 불러올 수 있다`() {
        // given
        val cartRepository = mockk<CartRepository>(relaxed = true)
        val productRepository = mockk<ProductRepository>(relaxed = true)
        val viewmodel = CartViewModel(cartRepository, productRepository)

        // when
        viewmodel.loadNextPageCartItems()

        // then
        verify { cartRepository.fetchCartItems(1) }
        verify { cartRepository.fetchCartItems(2) }
    }

    @Test
    fun `장바구니의 이전 페이지를 불러올 수 있다`() {
        // given
        val cartRepository = mockk<CartRepository>(relaxed = true)
        val productRepository = mockk<ProductRepository>(relaxed = true)
        val viewmodel = CartViewModel(cartRepository, productRepository)

        // when
        viewmodel.loadNextPageCartItems()
        viewmodel.loadPreviousPageCartItems()

        // then
        verify { cartRepository.fetchCartItems(0) }
        verify { cartRepository.fetchCartItems(1) }
    }

    @Test
    fun `장바구니에 담긴 상품을 삭제할 수 있다`() {
        val cartRepository = mockk<CartRepository>(relaxed = true)
        val productRepository = mockk<ProductRepository>(relaxed = true)
        val deletedItem = slot<CartItem>()
        val viewmodel = CartViewModel(cartRepository, productRepository)

        viewmodel.onCartItemDelete(
            CartedProduct(
                cartItem = CartItem(id = 1, productId = 1, quantity = 1),
                product = Product(id = 1, name = "사과1", imageSource = "image1", price = 1000),
            ),
        )
        verify { cartRepository.removeCartItem(capture(deletedItem)) }
        assertThat(deletedItem.captured).isEqualTo(
            CartItem(id = 1, productId = 1, quantity = 1),
        )
        verify { cartRepository.fetchCartItems(0) }
        assertThat(viewmodel.alteredCartItems).isEqualTo(
            arrayListOf(
                ProductQuantity(1, 0),
            ),
        )
    }

    @Test
    fun `장바구니에 담긴 아이템의 수량이 0 이상의 숫자로 변경되는 경우 수량이 변경된다`() {
        val cartRepository = mockk<CartRepository>(relaxed = true)

        thread {
            cartViewModel.onQuantityChange(1L, 100)
            verify { cartRepository.updateQuantity(1, 100) }
            verify { cartRepository.fetchCartItems(0) }
        }.join()

        /*thread {
            var cartableProducts : List<CartedProduct>? = arrayListOf()

            val latch = CountDownLatch(1)
            val observer = object : Observer<List<CartedProduct>> {
                override fun onChanged(value: List<CartedProduct>) {
                    cartableProducts = value
                    cartViewModel.cartableProducts.removeObserver(this)
                    latch.countDown()
                }
            }
            cartViewModel.cartableProducts.observeForever(observer)
            latch.await(10, TimeUnit.SECONDS)
            assertThat(cartableProducts).isNotEmpty()
            assertThat(cartableProducts).isEqualTo(null)
        }.join()*/
    }

    @Test
    fun `장바구니에 담긴 아이템의 수량이 0으로 변경되는 경우 장바구니 아이템이 삭제된다`() {
        val cartRepository = mockk<CartRepository>(relaxed = true)

        thread {
            cartViewModel.onQuantityChange(1L, 0)
            verify { cartRepository.removeCartItem(CartItem(1, 1, 1)) }
            verify { cartRepository.fetchCartItems(0) }
        }.join()
    }
}
