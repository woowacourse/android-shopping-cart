package woowacourse.shopping.presentation.productdetail

import woowacourse.shopping.CartProductInfo
import woowacourse.shopping.Product
import woowacourse.shopping.presentation.mapper.toDomain
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.RecentProductRepository
import woowacourse.shopping.util.SafeLiveData
import woowacourse.shopping.util.SafeMutableLiveData

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val recentProductRepository: RecentProductRepository,
    private val cartRepository: CartRepository,
    productModel: ProductModel,
) : ProductDetailContract.Presenter {

    private val _productInfo =
        SafeMutableLiveData(CartProductInfo(productModel.toDomain(), 1))
    override val productInfo: SafeLiveData<CartProductInfo> get() = _productInfo

    private val _mostRecentProduct: SafeMutableLiveData<Product> =
        SafeMutableLiveData(Product.defaultProduct)
    override val mostRecentProduct: SafeLiveData<Product> get() = _mostRecentProduct

    init {
        _productInfo.value =
            cartRepository.getCartProductInfoById(productModel.id) ?: _productInfo.value
    }

    override fun checkCurrentProductIsMostRecent() {
        _mostRecentProduct.value = recentProductRepository.getMostRecentProduct()
        if (mostRecentProduct.value == productInfo.value.product) view.hideMostRecentProduct()
    }

    override fun saveRecentProduct() {
        recentProductRepository.deleteRecentProductId(productInfo.value.product.id)
        recentProductRepository.addRecentProductId(productInfo.value.product.id)
    }

    override fun saveProductInRepository(count: Int) {
        if (count == 0) return
        cartRepository.putProductInCart(productInfo.value.product.id)
        cartRepository.updateCartProductCount(productInfo.value.product.id, count)
        view.showCompleteMessage(productInfo.value.product.name)
    }

    override fun updateProductCount(count: Int) {
        _productInfo.value = _productInfo.value.setCount(count)
    }
}
