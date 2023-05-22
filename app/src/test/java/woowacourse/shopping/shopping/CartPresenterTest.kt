package woowacourse.shopping.shopping

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Test
import woowacourse.shopping.cart.CartContract
import woowacourse.shopping.cart.CartPresenter
import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.CheckableCartProductModel
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.mapper.CartProductMapper.toViewModel
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.CheckableCartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Shop
import woowacourse.shopping.domain.URL

class CartPresenterTest {

    private lateinit var view: CartContract.View
    private lateinit var cart: Cart
    private lateinit var cartRepository: CartRepository

    @Test
    fun 프레젠터가_생성되면_뷰의_장바구니_네비게이터_푸터를_갱신한다() {
        // given
        view = mockk()
        cart = mockk(relaxed = true)
        cartRepository = mockk()

        initAnswers()

        // when
        CartPresenter(
            view = view, cart = cart, cartRepository = cartRepository, productRepository = mockk(relaxed = true), countPerPage = 1
        )

        // then
        verify {
            cartRepository.selectAll(any())
            view.updateCart(any())
            view.updateNavigator(any())
            view.updateTotalPrice(any())
            view.updateOrderText(any())
            view.updateTotalCheck(any())
        }
    }

    @Test
    fun 장바구니_아이템을_제거하면_저장하고_뷰에_갱신한다() {
        // given
        view = mockk()
        cart = Cart(
            listOf(
                CheckableCartProduct(
                    true,
                    CartProduct(
                        0, Product(0, URL(""), "", 0)
                    )
                )
            )
        )
        cartRepository = mockk()

        initAnswers()

        val presenter = CartPresenter(
            view = view, cart = cart, cartRepository = cartRepository, productRepository = mockk(relaxed = true), countPerPage = 1
        )

        every {
            cartRepository.deleteCartProduct(any())
        } just runs

        // when
        presenter.deleteCartProduct(cart.products[0].product.toViewModel())

        // then
        verify {
            cartRepository.deleteCartProduct(cart.products[0].product.product)
        }

        verify {
            cartRepository.selectAll(any())
            view.updateCart(any())
            view.updateNavigator(any())
            view.updateTotalPrice(any())
            view.updateOrderText(any())
            view.updateTotalCheck(any())
        }
    }

    @Test
    fun 상품을_선택하면_체크하고_뷰에_업데이트한다() {
        // given
        view = mockk()
        cart = Cart(emptyList())
        cartRepository = mockk()
        val cartProduct = CheckableCartProduct(
            true,
            CartProduct(
                0, Product(0, URL(""), "", 0)
            )
        )

        initAnswers()

        every {
            cartRepository.selectAll(any())
        } returns Shop(listOf(cartProduct.product))

        val presenter = CartPresenter(
            view = view, cart = cart, cartRepository = cartRepository, productRepository = mockk(relaxed = true), countPerPage = 1
        )

        // when
        val checkableCartProductModel = CheckableCartProductModel(
            true,
            CartProductModel(
                0, ProductModel(0, "", "", 0)
            )
        )
        presenter.checkCartProduct(checkableCartProductModel, false)

        // then
        val expect = listOf(
            CheckableCartProductModel(
                false,
                CartProductModel(
                    0, ProductModel(0, "", "", 0)
                )
            )
        )

        verify {
            cartRepository.selectAll(any())
            view.updateCart(expect)
            view.updateNavigator(any())
            view.updateTotalPrice(any())
            view.updateOrderText(any())
            view.updateTotalCheck(any())
        }
    }

    private fun initAnswers() {
        every {
            cartRepository.selectAllCount()
        } returns 1

        every {
            cartRepository.selectAll(any())
        } returns Shop(emptyList())

        every {
            view.updateCart(any())
            view.updateNavigator(any())
            view.updateTotalPrice(any())
            view.updateOrderText(any())
            view.updateTotalCheck(any())
        } just runs
    }
}
