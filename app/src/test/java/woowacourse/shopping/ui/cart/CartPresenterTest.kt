package woowacourse.shopping.ui.cart

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.database.FakeCartRepository
import woowacourse.shopping.database.FakeProductRepository
import woowacourse.shopping.ui.productdetail.ProductDetailContract
import woowacourse.shopping.ui.productdetail.ProductDetailPresenter

class CartPresenterTest {
    private lateinit var cartPresenter: CartContract.Presenter
    private lateinit var productDetailPresenter: ProductDetailContract.Presenter
    private lateinit var cartView: CartContract.View
    private lateinit var productDetailView: ProductDetailContract.View

    @Before
    fun setUp() {
        cartView = mockk()
        productDetailView = mockk()
        cartPresenter = CartPresenter(cartView, FakeCartRepository)
        productDetailPresenter =
            ProductDetailPresenter(productDetailView, FakeProductRepository, FakeCartRepository)
    }

    @Test
    fun 장바구니에_담은_상품을_삭제하면_장바구니_목록에_해당_상품이_삭제된다() {
        every { cartView.setCartItems(any()) } answers { }

        cartPresenter.deleteCartItem(2)

        val actual = FakeCartRepository.findAll().map { it.id }.contains(2)
        assertFalse(actual)
    }

    @Test
    fun 장바구니에서_수량을_증가시키면_수량이_1_증가한다() {
        productDetailPresenter.addProductToCart(2, 5)
        cartPresenter.plusItemCount(2L, 5)

        val actual = FakeCartRepository.findAll().filter { item ->
            item.id == 2L
        }[0].count
        assertTrue(actual == 6)
    }

    @Test
    fun 장바구니에서_수량을_감소시키면_수량이_1_감소한다() {
        productDetailPresenter.addProductToCart(2, 5)
        cartPresenter.minusItemCount(2L, 5)

        val actual = FakeCartRepository.findAll().filter { item ->
            item.id == 2L
        }[0].count
        assertTrue(actual == 4)
    }
}
