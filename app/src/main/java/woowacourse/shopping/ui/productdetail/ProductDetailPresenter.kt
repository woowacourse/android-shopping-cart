package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.RecentRepository

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val product: ProductUIModel,
    private val cartRepository: CartRepository,
    private val recentRepository: RecentRepository
) : ProductDetailContract.Presenter {
    init {
        setUpProductDetail()
        addProductToRecent()
    }

    override fun setUpProductDetail() {
        view.setProductDetail(product)
    }

    override fun addProductToCart() {
        cartRepository.insert(product.id)
        view.navigateToCart()
    }

    override fun addProductToRecent() {
        recentRepository.findById(product.id)?.let {
            recentRepository.delete(it.id)
        }
        recentRepository.insert(product.toDomain())
    }
}
