package woowacourse.shopping.view.productdetail

import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.domain.RecentViewedRepository
import woowacourse.shopping.model.ProductModel

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val cartRepository: CartRepository,
    private val recentViewedRepository: RecentViewedRepository,
) : ProductDetailContract.Presenter {
    override fun putInCart(product: ProductModel) {
        cartRepository.add(product.id, 1)
        view.startCartActivity()
    }

    override fun updateRecentViewedProducts(id: Int) {
        recentViewedRepository.add(id)
    }
}
