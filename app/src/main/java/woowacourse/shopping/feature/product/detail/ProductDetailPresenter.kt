package woowacourse.shopping.feature.product.detail

import woowacourse.shopping.data.cart.CartDao
import woowacourse.shopping.model.CartProductState.Companion.MAX_COUNT_VALUE
import woowacourse.shopping.model.CartProductState.Companion.MIN_COUNT_VALUE
import woowacourse.shopping.model.ProductState
import woowacourse.shopping.model.mapper.toDomain

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    override val product: ProductState?,
    private val cartDao: CartDao
) : ProductDetailContract.Presenter {

    private var count = MIN_COUNT_VALUE

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
        view.closeProductDetail()
    }

    override fun minusCount() {
        count--
        if (count < MIN_COUNT_VALUE) count++
        view.setCount(count)
    }

    override fun plusCount() {
        count++
        if (MAX_COUNT_VALUE < count) count--
        view.setCount(count)
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
