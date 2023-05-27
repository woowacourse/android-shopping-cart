package woowacourse.shopping.presentation.cart

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductDataSource
import woowacourse.shopping.model.CartProduct
import woowacourse.shopping.model.Counter
import woowacourse.shopping.model.Price
import woowacourse.shopping.model.Product
import woowacourse.shopping.presentation.model.CheckableCartProductModel
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.model.UnCheckableCartProductModel

class CartPresenterTest {

    private lateinit var presenter: CartContract.Presenter
    private lateinit var view: CartContract.View
    private lateinit var cartRepository: CartRepository
    private lateinit var productRepository: ProductDataSource

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        productRepository = mockk(relaxed = true)
        presenter = CartPresenter(view, cartRepository)
    }

    @Test
    fun `카트의 첫번째 페이지 상품 목록을 불러온다`() {
        // given
        val pageSlot = slot<Int>()
        val cartProductsSlot = slot<List<CheckableCartProductModel>>()

        every { view.showPageNumber(capture(pageSlot)) } just runs
        every { view.showCartProductModels(capture(cartProductsSlot)) } just runs

        every { cartRepository.getCartProducts() } returns (1L..8L).map {
            CartProduct(
                it,
                Product(it, "test.com", "햄버거", Price(10000)),
                1,
                true,
            )
        }
        // when
        presenter.loadCart()

        // then
        val actualPage = pageSlot.captured
        val cartProducts = cartProductsSlot.captured

        assertThat(actualPage).isEqualTo(1)
        assertThat(cartProducts).isEqualTo(
            (1L..5L).map {
                CheckableCartProductModel(
                    it,
                    ProductModel((it), "test.com", "햄버거", 10000),
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
        val cartProductsSlot = slot<List<CheckableCartProductModel>>()
        every { view.showPageNumber(capture(pageSlot)) } just runs
        every { view.showCartProductModels(capture(cartProductsSlot)) } just runs
        every { cartRepository.getCartProducts() } returns (1L..8L).map {
            CartProduct(
                it,
                Product(it, "test.com", "햄버거", Price(10000)),
                1,
                true,
            )
        }
        presenter.loadCart()

        // when
        presenter.plusPage()

        // then
        val actual = pageSlot.captured
        val cartProducts = cartProductsSlot.captured

        assertThat(actual).isEqualTo(2)
        assertThat(cartProducts).isEqualTo(
            (6L..8L).map {
                CheckableCartProductModel(
                    it,
                    ProductModel(it, "test.com", "햄버거", 10000),
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
        val cartProductsSlot = slot<List<CheckableCartProductModel>>()
        every { view.showPageNumber(capture(pageSlot)) } just runs
        every { view.showCartProductModels(capture(cartProductsSlot)) } just runs
        every { cartRepository.getCartProducts() } returns (1L..8L).map {
            CartProduct(
                it,
                Product(it, "test.com", "햄버거", Price(10000)),
                1,
                true,
            )
        }
        every { productRepository.findProductById(any()) } returns
            Product(1, "test.com", "햄버거", Price(10000))
        presenter = CartPresenter(view, cartRepository, Counter(1))

        presenter.loadCart()

        // when
        presenter.minusPage()

        // then
        val actual = pageSlot.captured
        val cartProducts = cartProductsSlot.captured

        assertThat(actual).isEqualTo(1)
        assertThat(cartProducts).isEqualTo(
            (1L..5L).map {
                CheckableCartProductModel(
                    it,
                    ProductModel(it, "test.com", "햄버거", 10000),
                    1,
                    true,
                )
            },
        )
    }

    @Test
    fun `id 1번 상품을 삭제한다`() {
        // given
        val pageSlot = slot<Int>()
        val cartProductsSlot = slot<List<CheckableCartProductModel>>()

        every { view.showPageNumber(capture(pageSlot)) } just runs
        every { view.showCartProductModels(capture(cartProductsSlot)) } just runs
        every { cartRepository.getCartProducts() } returns (1L..5L).map {
            CartProduct(
                it,
                Product(it, "test.com", "햄버거", Price(10000)),
                1,
                true,
            )
        }
        presenter.loadCart()

        // when
        presenter.deleteCartProduct(
            UnCheckableCartProductModel(
                1L,
                ProductModel(1L, "test.com", "햄버거", 10000),
                1,
            ),
        )

        // then
        val actual = pageSlot.captured
        val cartProducts = cartProductsSlot.captured

        assertThat(actual).isEqualTo(1)
        assertThat(cartProducts).isEqualTo(
            (2L..5L).map {
                CheckableCartProductModel(
                    it,
                    ProductModel(it, "test.com", "햄버거", 10000),
                    1,
                    true,
                )
            },
        )
    }

    @Test
    fun `1번 상품이 2개일때 1 증가시키면 3이 된다`() {
        // given
        val cartProductsSlot = slot<List<CheckableCartProductModel>>()
        every { view.showCartProductModels(capture(cartProductsSlot)) } just runs
        every { cartRepository.getCartProducts() } returns (1L..5L).map {
            CartProduct(
                it,
                Product(it, "test.com", "햄버거", Price(10000)),
                1,
                true,
            )
        }

        presenter.loadCart()

        // when
        presenter.addProductCartCount(
            CheckableCartProductModel(
                1L,
                ProductModel(1L, "test.com", "햄버거", 10000),
                1,
                true,
            ),
        )

        // then
        val cartProduct = cartProductsSlot.captured.find { it.cartId == 1L }

        assertThat(cartProduct?.count).isEqualTo(2)
    }

    @Test
    fun `1번 상품이 2개일때 1 감소시키면 1이 된다`() {
        // given
        val cartProductsSlot = slot<List<CheckableCartProductModel>>()
        every { view.showCartProductModels(capture(cartProductsSlot)) } just runs
        every { cartRepository.getCartProducts() } returns (1L..5L).map {
            CartProduct(
                it,
                Product(it, "test.com", "햄버거", Price(10000)),
                2,
                true,
            )
        }

        presenter.loadCart()

        // when
        presenter.subProductCartCount(
            CheckableCartProductModel(
                1L,
                ProductModel(1, "test.com", "햄버거", 10000),
                1,
                true,
            ),
        )

        // then
        val cartProduct = cartProductsSlot.captured.find { it.cartId == 1L }

        assertThat(cartProduct?.count).isEqualTo(1)
    }

    @Test
    fun `1번 상품 선택이 변경된다`() {
        // given
        val cartProductsSlot = slot<List<CheckableCartProductModel>>()
        every { view.showCartProductModels(capture(cartProductsSlot)) } just runs
        every { cartRepository.getCartProducts() } returns (1L..5L).map {
            CartProduct(
                it,
                Product(it, "test.com", "햄버거", Price(10000)),
                2,
                true,
            )
        }
        every { productRepository.findProductById(any()) } returns
            Product(1, "test.com", "햄버거", Price(10000))

        presenter.loadCart()

        // when
        presenter.changeProductSelected(
            ProductModel(1, "test.com", "햄버거", 10000),
        )

        // then
        val cartProduct = cartProductsSlot.captured.find { it.cartId == 1L }

        assertThat(cartProduct?.isChecked).isTrue
    }
}
