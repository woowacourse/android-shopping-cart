package woowacourse.shopping.shopping

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.common.model.mapper.ProductMapper.toViewModel
import woowacourse.shopping.data.repository.CartRepository
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
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUP() {
        view = mockk()
        product = makeProductMock()
        cartRepository = mockk()

        every {
            view.updateProductDetail(any())
        } just runs

        presenter = ProductDetailPresenter(view, product, cartRepository)
    }

    @Test
    fun 프레젠터가_생성되면_뷰의_상품_상세정보를_갱신한다() {
        // given

        // when

        // then
        verify {
            view.updateProductDetail(product.toViewModel())
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
            cartRepository.selectAll()
        } returns cart

        every {
            cart.add(cartProduct)
        } returns addedCart

        every {
            cartRepository.insertCartProduct(any())
            view.showCart()
        } just runs

        // when
        presenter.addToCart()

        // then
        verify {
            cartRepository.selectAll()
            cartRepository.insertCartProduct(cartProduct.product)
            view.showCart()
        }
    }

    private fun makeProductMock(): Product = Product(
        URL(""),
        "",
        0
    )
}
