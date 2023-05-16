package woowacourse.shopping.view.productdetail

import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.domain.RecentViewedRepository
import woowacourse.shopping.model.ProductModel

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val cartRepository: CartRepository,
    private val recentViewedRepository: RecentViewedRepository,
) : ProductDetailContract.Presenter {
    private var count = 1
    override fun putInCart(product: ProductModel) {
        cartRepository.add(product.id, count)
        view.startCartActivity()
    }

    override fun updateRecentViewedProducts(id: Int) {
        recentViewedRepository.add(id)
    }

    override fun plusCount() {
        if (count < COUNT_MAX) count++
        view.updateCount(count)
    }

    override fun minusCount() {
        if (count > COUNT_MIN) count--
        view.updateCount(count)
    }

    companion object {
        private const val COUNT_MAX = 100
        private const val COUNT_MIN = 1
    }
}
