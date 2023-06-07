package woowacourse.shopping.feature.product.detail

import woowacourse.shopping.data.datasource.cartdatasource.CartDataSource
import woowacourse.shopping.data.repository.RecentProductRepositoryImpl
import woowacourse.shopping.feature.list.item.ProductView.CartProductItem
import woowacourse.shopping.feature.model.mapper.toProductDomain

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val recentProductRepository: RecentProductRepositoryImpl,
    private val cartDataSource: CartDataSource,
    product: CartProductItem,
    private val lastProduct: CartProductItem?,
) : ProductDetailContract.Presenter {

    override val sameProduct: Boolean

    init {
        sameProduct = lastProduct?.let {
            if (product == lastProduct) return@let true
            return@let false
        } ?: false
    }

    override fun initScreen() {
        if (sameProduct || lastProduct == null) return view.hideRecentProductInfoView()
        view.setRecentProductInfo(lastProduct.name, lastProduct.price)
    }

    override fun navigateRecentProductDetail() {
        if (lastProduct == null) return

        recentProductRepository.addColumn(lastProduct.toProductDomain())
        view.showRecentProductDetail(lastProduct)
    }

    override fun addProduct(product: CartProductItem) {
        cartDataSource.addColumn(product.toProductDomain())
    }
}
