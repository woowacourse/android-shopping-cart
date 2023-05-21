package woowacourse.shopping.feature.product.detail

import woowacourse.shopping.data.cart.CartDbHandler
import woowacourse.shopping.feature.model.ProductState
import woowacourse.shopping.feature.model.mapper.toDomain

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val db: CartDbHandler,
) : ProductDetailContract.Presenter {

    override fun addColumn(product: ProductState) {
        db.addColumn(product.toDomain())
    }
}
