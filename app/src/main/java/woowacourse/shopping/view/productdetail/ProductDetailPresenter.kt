package woowacourse.shopping.view.productdetail

import com.shopping.repository.CartProductRepository
import com.shopping.repository.RecentProductsRepository
import woowacourse.shopping.model.uimodel.ProductUIModel
import woowacourse.shopping.model.uimodel.mapper.toDomain


class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    override val product: ProductUIModel,
    private val cartProductRepository: CartProductRepository,
    private val recentProductsRepository: RecentProductsRepository
) : ProductDetailContract.Presenter {

    override fun saveRecentProduct() {
        return recentProductsRepository.insert(RecentProductUIModel(product).toDomain())
    }

    override fun saveCartProduct() {
        cartProductRepository.insert(CartProductUIModel(product).toDomain())
        view.showCartPage()
    }
}
