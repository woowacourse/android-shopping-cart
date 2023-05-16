package woowacourse.shopping.productdetail

import woowacourse.shopping.datas.CartRepository
import woowacourse.shopping.datas.RecentRepository
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val productUIModel: ProductUIModel,
    private val recentRepository: RecentRepository,
    private val cartRepository: CartRepository,
) : ProductDetailContract.Presenter {

    override fun initPage() {
        view.setViews(productUIModel)
    }

    override fun insertRecentRepository(currentTime: Long) {
        recentRepository.insert(RecentProductUIModel(currentTime, productUIModel))
    }

    override fun onClickAddToCart() {
        cartRepository.insert(CartProductUIModel(1, productUIModel))
        view.showCartPage()
    }
}
