package woowacourse.shopping.productdetail

import woowacourse.shopping.common.data.dao.CartDao
import woowacourse.shopping.common.data.database.state.CartState
import woowacourse.shopping.common.data.database.state.State
import woowacourse.shopping.common.model.mapper.CartProductMapper.toViewModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toViewModel
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val product: Product,
    private val cartState: State<Cart> = CartState,
    private val cartDao: CartDao
) : ProductDetailContract.Presenter {
    init {
        view.updateProductDetail(product.toViewModel())
    }

    override fun addToCart() {
        val cart = cartDao.selectAll()
        val cartProduct = cart.makeCartProduct(product)
        cartState.save(cart.add(cartProduct))
        cartDao.insertCartProduct(cartProduct.toViewModel())
        view.showCart()
    }
}
