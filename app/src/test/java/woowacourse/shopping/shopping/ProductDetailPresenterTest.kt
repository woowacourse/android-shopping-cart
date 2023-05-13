package woowacourse.shopping.shopping

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.common.data.dao.CartDao
import woowacourse.shopping.common.data.database.state.State
import woowacourse.shopping.common.model.mapper.CartProductMapper.toView
import woowacourse.shopping.common.model.mapper.ProductMapper.toView
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.URL
import woowacourse.shopping.productdetail.ProductDetailContract
import woowacourse.shopping.productdetail.ProductDetailPresenter

class ProductDetailPresenterTest {
    private lateinit var presenter: ProductDetailPresenter
    private lateinit var view: ProductDetailContract.View
    private lateinit var product: Product
    private lateinit var cartState: State<Cart>
    private lateinit var cartDao: CartDao

    @Before
    fun setUP() {
        view = mockk()
        product = makeProductMock()
        cartState = mockk()
        cartDao = mockk()

        every {
            view.updateProductDetail(any())
        } just runs

        presenter = ProductDetailPresenter(view, product, cartState, cartDao)
    }

    @Test
    fun 프레젠터가_생성되면_뷰의_상품_상세정보를_갱신한다() {
        // given

        // when

        // then
        verify {
            view.updateProductDetail(product.toView())
        }
    }

    @Test
    fun 카트에_상품을_담으면_카트에_상품을_추가하고_카트를_보여준다() {
        // given
        val cart: Cart = mockk()
        val cartProduct = CartProduct(0, product)
        val addedCart = Cart(
            listOf(cartProduct)
        )

        every {
            cartDao.selectAll()
        } returns cart

        every {
            cart.makeCartProduct(any())
        } returns cartProduct

        every {
            cart.add(cartProduct)
        } returns addedCart

        every {
            cartState.save(any())
            cartDao.insertCartProduct(any())
            view.showCart()
        } just runs

        // when
        presenter.addToCart()

        // then
        verify {
            cartDao.selectAll()
            cartState.save(addedCart)
            cartDao.insertCartProduct(cartProduct.toView())
            view.showCart()
        }
    }

    private fun makeProductMock(): Product = Product(
        URL(""),
        "",
        0
    )
}
