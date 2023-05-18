package woowacourse.shopping.presentation.cart

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.cart.CartEntity
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.model.Price
import woowacourse.shopping.model.Product
import woowacourse.shopping.presentation.model.CartProductModel
import woowacourse.shopping.presentation.model.CheckableCartProductModel
import woowacourse.shopping.presentation.model.ProductModel

class CartPresenterTest {

    private lateinit var presenter: CartContract.Presenter
    private lateinit var view: CartContract.View
    private lateinit var cartRepository: CartRepository
    private lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        productRepository = mockk(relaxed = true)
        presenter = CartPresenter(view, cartRepository, productRepository)
    }

    @Test
    fun `카트의 첫번째 페이지 상품 목록을 불러온다`() {
        // given
        val pageSlot = slot<Int>()
        val cartProductsSlot = slot<List<CartProductModel>>()

        every { view.setPage(capture(pageSlot)) } just runs
        every { view.setCartProductModels(capture(cartProductsSlot)) } just runs

        every { cartRepository.getCartEntities() } returns (1..8).map { CartEntity(it, 1) }
        every { productRepository.findProductById(any()) } returns
            Product(1, "test.com", "햄버거", Price(10000))

        // when
        presenter.loadCart()

        // then
        val actualPage = pageSlot.captured
        val cartProducts = cartProductsSlot.captured

        assertThat(actualPage).isEqualTo(1)
        assertThat(cartProducts).isEqualTo(
            List(5) {
                CheckableCartProductModel(
                    ProductModel(1, "test.com", "햄버거", 10000),
                    1,
                    true,
                )
            },
        )
    }

    @Test
    fun `1 에서 페이지를 증가하면 다음 2 페이지 상품을 본다`() {
        // given
        val pageSlot = slot<Int>()
        val cartProductsSlot = slot<List<CartProductModel>>()
        every { view.setPage(capture(pageSlot)) } just runs
        every { view.setCartProductModels(capture(cartProductsSlot)) } just runs
        every { cartRepository.getCartEntities() } returns (1..8).map { CartEntity(it, 1) }
        every { productRepository.findProductById(any()) } returns
            Product(1, "test.com", "햄버거", Price(10000))

        presenter.loadCart()

        // when
        presenter.plusPage()

        // then
        val actual = pageSlot.captured
        val cartProducts = cartProductsSlot.captured

        assertThat(actual).isEqualTo(2)
        assertThat(cartProducts).isEqualTo(
            List(3) {
                CheckableCartProductModel(
                    ProductModel(1, "test.com", "햄버거", 10000),
                    1,
                    true,
                )
            },
        )
    }

    @Test
    fun `2 에서 페이지를 감소하면 이전 1 페이지 상품을 본다`() {
        // given
        val pageSlot = slot<Int>()
        val cartProductsSlot = slot<List<CartProductModel>>()
        every { view.setPage(capture(pageSlot)) } just runs
        every { view.setCartProductModels(capture(cartProductsSlot)) } just runs
        every { cartRepository.getCartEntities() } returns (1..8).map { CartEntity(it, 1) }
        every { productRepository.findProductById(any()) } returns
            Product(1, "test.com", "햄버거", Price(10000))

        presenter.loadCart()
        presenter.plusPage()

        // when
        presenter.minusPage()

        // then
        val actual = pageSlot.captured
        val cartProducts = cartProductsSlot.captured

        assertThat(actual).isEqualTo(1)
        assertThat(cartProducts).isEqualTo(
            List(5) {
                CheckableCartProductModel(
                    ProductModel(1, "test.com", "햄버거", 10000),
                    1,
                    true,
                )
            },
        )
    }

    @Test
    fun deleteProduct() {
        // given
        val pageSlot = slot<Int>()
        val cartProductsSlot = slot<List<CartProductModel>>()
        every { view.setPage(capture(pageSlot)) } just runs
        every { view.setCartProductModels(capture(cartProductsSlot)) } just runs
        every { cartRepository.getCartEntities() } returns listOf(CartEntity(1, 1))
        every { productRepository.findProductById(any()) } returns
            Product(1, "test.com", "햄버거", Price(10000))

        presenter.loadCart()

        // when
        presenter.deleteProduct(ProductModel(1, "test.com", "햄버거", 10000))

        // then
        val actual = pageSlot.captured
        val cartProducts = cartProductsSlot.captured

        assertThat(actual).isEqualTo(1)
        assertThat(cartProducts).isEqualTo(listOf<CartProductModel>())
    }
}
