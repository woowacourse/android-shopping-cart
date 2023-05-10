package woowacourse.shopping.view.productdetail

import woowacourse.shopping.data.CartDbRepository
import woowacourse.shopping.domain.RecentViewedRepository
import woowacourse.shopping.model.ProductModel

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val cartDbRepository: CartDbRepository,
    private val recentViewedRepository: RecentViewedRepository,
) : ProductDetailContract.Presenter {
    override fun putInCart(product: ProductModel) {
        cartDbRepository.add(product.id, 1)
        view.startCartActivity()
    }

    override fun updateRecentViewedProducts(id: Int) {
        recentViewedRepository.add(id)
    }
}
