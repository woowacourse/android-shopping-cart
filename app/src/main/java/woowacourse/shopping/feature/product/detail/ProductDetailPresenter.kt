package woowacourse.shopping.feature.product.detail

import woowacourse.shopping.data.cart.CartDao
import woowacourse.shopping.model.ProductState
import woowacourse.shopping.model.mapper.toDomain

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    override val product: ProductState?,
    private val cartDao: CartDao
) : ProductDetailContract.Presenter {

    override fun loadProduct() {
        if (!isValidProduct()) return
        product!!

        view.setViewContent(product)
    }

    override fun addCartProduct() {
        if (!isValidProduct()) return
        product!!

        cartDao.addColumn(product.toDomain())
        view.showCart()
    }

    private fun isValidProduct(): Boolean {
        if (product == null) {
            view.showAccessError()
            view.closeProductDetail()
            return false
        }
        return true
    }
}
