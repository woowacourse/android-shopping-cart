package woowacourse.shopping.productdetail.contract.presenter

import com.example.domain.model.CartRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.productdetail.contract.ProductDetailContract

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val product: ProductUIModel,
    private val cartRepository: CartRepository
) : ProductDetailContract.Presenter {

    override fun setUpProductDetail() {
        view.setProductDetail(product)
    }

    override fun addProductToBasket() {
        cartRepository.add(product.toDomain())
    }
}
