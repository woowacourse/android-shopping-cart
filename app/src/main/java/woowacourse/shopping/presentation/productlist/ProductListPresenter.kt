package woowacourse.shopping.presentation.productlist

import android.util.Log
import woowacourse.shopping.CartProductInfo
import woowacourse.shopping.CartProductInfoList
import woowacourse.shopping.Product
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.model.CartProductInfoModel
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentProductRepository

class ProductListPresenter(
    private val view: ProductListContract.View,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository,
    private val cartRepository: CartRepository,
) : ProductListContract.Presenter {

    private var size = PRODUCTS_SIZE
    override fun refreshProductItems() {
        productRepository.getProductsWithRange(0, size) { products ->
            val cartProducts = mutableListOf<CartProductInfoModel>()
            getCartProducts(0, cartProducts, products)
        }
    }

    private fun getCartProducts(
        index: Int,
        cartProducts: MutableList<CartProductInfoModel>,
        receivedProducts: List<Product>,
    ) {
        cartRepository.getCartProductInfoById(receivedProducts[index].id) {
            if (it != null) {
                cartProducts.add(it.toPresentation())
            } else {
                cartProducts.add(CartProductInfo(receivedProducts[index], 0).toPresentation())
            }
            if (index == receivedProducts.lastIndex) view.loadProductItems(cartProducts)
            if (index + 1 <= receivedProducts.lastIndex) {
                getCartProducts(
                    index + 1,
                    cartProducts,
                    receivedProducts,
                )
            }
        }
    }

    override fun loadMoreProductItems() {
        size += PRODUCTS_SIZE
        refreshProductItems()
    }

    override fun loadRecentProductItems() {
        recentProductRepository.getRecentProducts(RECENT_PRODUCTS_SIZE) { products ->
            view.loadRecentProductItems(products.map { it.toPresentation() })
        }
    }

    override fun putProductInCart(cartProductModel: CartProductInfoModel) {
        cartRepository.putProductInCart(cartProductModel.productModel.id)
    }

    override fun updateCartProductCount(cartProductModel: CartProductInfoModel, count: Int) {
        if (count == 0) {
            cartRepository.deleteCartProductId(cartProductModel.productModel.id)
        } else {
            cartRepository.updateCartProductCount(
                cartProductModel.productModel.id,
                count,
            )
        }
    }

    override fun updateCartCount() {
        cartRepository.getAllCartProductsInfo {
            view.showCartCount(CartProductInfoList(it).count)
        }
    }

    override fun showMyCart() {
        cartRepository.getAllCartProductsInfo { cartProducts ->
            Log.d("wooseok", cartProducts.toString())
            view.navigateToCart(cartProducts.map { it.toPresentation() })
        }
    }

    companion object {
        private const val PRODUCTS_SIZE = 20
        private const val RECENT_PRODUCTS_SIZE = 10
    }
}
