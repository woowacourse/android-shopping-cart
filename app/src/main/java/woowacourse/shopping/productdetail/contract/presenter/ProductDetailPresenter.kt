package woowacourse.shopping.productdetail.contract.presenter

import com.domain.model.CartRepository
import com.domain.model.RecentRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.productdetail.contract.ProductDetailContract

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val product: ProductUIModel,
    private val cartRepository: CartRepository,
    private val recentRepository: RecentRepository,
) : ProductDetailContract.Presenter {
    override fun setUpProduct() {
        view.setProductDetail(product)
        recentRepository.findById(product.id)?.let {
            recentRepository.delete(it.id)
        }
        recentRepository.insert(product.toDomain())
    }

    override fun addCart() {
        cartRepository.insert(product.toDomain())
        view.navigateToCart()
    }
}
