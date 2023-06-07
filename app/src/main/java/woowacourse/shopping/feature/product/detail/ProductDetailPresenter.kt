package woowacourse.shopping.feature.product.detail

import com.example.data.repository.CartRepository
import com.example.data.repository.RecentProductRepository
import woowacourse.shopping.feature.list.item.ProductView.CartProductItem
import woowacourse.shopping.feature.model.mapper.toProductDomain

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val recentProductRepository: RecentProductRepository,
    private val cartRepository: CartRepository,
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
        cartRepository.addColumn(product.toProductDomain(), DEFAULT_COUNT)
    }

    companion object {
        private const val DEFAULT_COUNT = 1
    }
}
