package woowacourse.shopping.ui.productdetail

import com.example.domain.model.CartRepository
import com.example.domain.model.RecentRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.model.ProductUIModel

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
        cartRepository.insert(product.toDomain())
        view.navigateToCart()
    }

    override fun addProductToRecent() {
        recentRepository.findById(product.id)?.let {
            recentRepository.delete(it.id)
        }
        recentRepository.insert(product.toDomain())
    }
}
