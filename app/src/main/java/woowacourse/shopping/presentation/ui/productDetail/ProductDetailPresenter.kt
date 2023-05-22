package woowacourse.shopping.presentation.ui.productDetail

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.model.RecentlyViewedProduct
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentlyViewedRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.domain.util.WoowaResult
import woowacourse.shopping.domain.util.WoowaResult.FAIL
import woowacourse.shopping.domain.util.WoowaResult.SUCCESS

class ProductDetailPresenter(
    view: ProductDetailContract.View,
    productId: Long,
    productRepository: ProductRepository,
    recentlyViewedRepository: RecentlyViewedRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ProductDetailContract.Presenter {
    private lateinit var product: Product

    init {
        val woowaResult = productRepository.getProduct(productId)
        when (woowaResult) {
            is SUCCESS -> product = woowaResult.data
            is FAIL -> view.handleNoSuchProductError()
        }
        fetchLastViewedProduct(view, recentlyViewedRepository)
        recentlyViewedRepository.addRecentlyViewedProduct(productId)
    }

    private fun fetchLastViewedProduct(
        view: ProductDetailContract.View,
        recentlyViewedRepository: RecentlyViewedRepository,
    ) {
        val result: WoowaResult<RecentlyViewedProduct> =
            recentlyViewedRepository.getLastViewedProduct()
        when (result) {
            is SUCCESS -> view.setBindingData(product, result.data)
            is FAIL -> view.handleNoSuchProductError()
        }
    }

    override fun addProductInCart() {
        shoppingCartRepository.insertProductInCart(ProductInCart(product, 1))
    }
}
