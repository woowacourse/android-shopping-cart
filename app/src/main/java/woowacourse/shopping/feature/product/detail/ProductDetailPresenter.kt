package woowacourse.shopping.feature.product.detail

import woowacourse.shopping.data.cart.CartDbHandler
import woowacourse.shopping.model.ProductState
import woowacourse.shopping.model.mapper.toDomain

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    override val product: ProductState?,
    private val cartDbHandler: CartDbHandler
) : ProductDetailContract.Presenter {

    override fun loadProduct() {
        if (!isValidProduct()) return
        product!!

        view.setViewContent(product)
    }

    override fun addCartProduct() {
        if (!isValidProduct()) return
        product!!

        cartDbHandler.addColumn(product.toDomain())
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
