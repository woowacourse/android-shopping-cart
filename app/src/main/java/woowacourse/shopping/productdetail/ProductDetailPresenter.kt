package woowacourse.shopping.productdetail

import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toDomain
import woowacourse.shopping.common.model.mapper.ProductMapper.toView
import woowacourse.shopping.domain.Product

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    productModel: ProductModel,
    recentProductModel: ProductModel?
) : ProductDetailContract.Presenter {
    private val product: Product
    private val recentProduct: Product?

    init {
        product = productModel.toDomain()
        recentProduct = recentProductModel?.toDomain()
        view.setupProductDetail(product.toView())
        view.setupRecentProductDetail(recentProduct?.toView())
    }

    override fun setupCartProductDialog() {
        view.showCartProductDialog(product.toView())
    }
}
