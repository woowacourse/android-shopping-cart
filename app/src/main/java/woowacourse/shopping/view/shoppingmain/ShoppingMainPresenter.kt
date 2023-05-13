package woowacourse.shopping.view.shoppingmain

import com.shopping.repository.ProductRepository
import com.shopping.repository.RecentProductsRepository
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel
import woowacourse.shopping.uimodel.mapper.toUIModel

class ShoppingMainPresenter(
    private val view: ShoppingMainContract.View,
    private val productsRepository: ProductRepository,
    private val recentProductsRepository: RecentProductsRepository
) : ShoppingMainContract.Presenter {
    override fun getMainProducts(): List<ProductUIModel> {
        return productsRepository.products.toUIModel()
    }

    override fun getRecentProducts(): List<RecentProductUIModel> {
        return recentProductsRepository.getAll().map { it.toUIModel() }
    }

    override fun setProductOnClick() {
        view.showProductDetailPage()
    }
}
