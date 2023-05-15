package woowacourse.shopping.ui.productdetail.contract.presenter

import com.example.domain.repository.CartRepository
import com.example.domain.repository.RecentRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.productdetail.contract.ProductDetailContract

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val product: ProductUIModel,
    private val cartRepository: CartRepository,
    private val recentRepository: RecentRepository,
) : ProductDetailContract.Presenter {

    init {
        setUpProductDetail()
        addProductToCart()
    }

    override fun setUpProductDetail() {
        view.setProductDetail(product)
    }

    override fun addProductToCart() {
        cartRepository.insert(product.toDomain())
    }

    override fun addProductToRecent() {
        recentRepository.findById(product.id)?.let {
            recentRepository.delete(it.id)
        }
        recentRepository.insert(product.toDomain())
    }
}
