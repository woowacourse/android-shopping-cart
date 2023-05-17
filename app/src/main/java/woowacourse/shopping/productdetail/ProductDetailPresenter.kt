package woowacourse.shopping.productdetail

import com.shopping.domain.CartProduct
import com.shopping.domain.RecentProduct
import woowacourse.shopping.datas.CartRepository
import woowacourse.shopping.datas.RecentRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.uimodel.ProductUIModel

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    productUIModel: ProductUIModel,
    private val recentRepository: RecentRepository,
    private val cartRepository: CartRepository,
) : ProductDetailContract.Presenter {
    override var product = productUIModel
        private set

    override fun insertRecentRepository(currentTime: Long) {
        recentRepository.insert(RecentProduct(currentTime, product.toDomain()))
    }

    override fun addToCart() {
        cartRepository.insert(CartProduct(1, product.toDomain()))
        view.showCartPage()
    }
}
