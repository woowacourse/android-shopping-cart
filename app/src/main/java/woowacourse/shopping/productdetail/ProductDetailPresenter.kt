package woowacourse.shopping.productdetail

import com.shopping.domain.CartProduct
import com.shopping.domain.CartRepository
import com.shopping.domain.RecentProduct
import com.shopping.domain.RecentRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val product: ProductUIModel,
    private val recentRepository: RecentRepository,
    private val cartRepository: CartRepository,
) : ProductDetailContract.Presenter {

    override fun insertRecentRepository(currentTime: Long) {
        recentRepository.insert(RecentProduct(currentTime, product.toDomain()))
    }

    override fun fetchMostRecentProduct() {
        val recentProduct = recentRepository.getLatestProduct().toUIModel()
        if (product.id != recentProduct.product.id) {
            view.setLatestProductVisibility()
        }
        view.showRecentProduct(recentProduct)
    }

    override fun attachCartProductData() {
        view.setCartProductData(
            CartProductUIModel(
                isPicked = true,
                cartRepository.getProductCount(product.id),
                product
            )
        )
    }

    override fun addToCart(count: Int) {
        val productCount = cartRepository.getProductCount(product.id)
        if (productCount == 0) {
            cartRepository.insert(CartProduct(count = count, product = product.toDomain()))
            view.showCartPage()
            return
        }
        cartRepository.updateProductCount(product.id, productCount + count)
        view.showCartPage()
    }
}
