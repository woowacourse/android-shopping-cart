package woowacourse.shopping.data.cart

import woowacourse.shopping.CartProductInfo
import woowacourse.shopping.CartProductInfoList
import woowacourse.shopping.data.product.ProductRemoteDataSource
import woowacourse.shopping.repository.CartRepository

class CartRepositoryImpl constructor(
    private val cartLocalDataSource: CartLocalDataSource,
    private val productRemoteDataSource: ProductRemoteDataSource,
) : CartRepository {
    override fun putProductInCart(productId: Int) {
        cartLocalDataSource.addProduct(productId)
    }

    override fun deleteCartProductId(productId: Int) {
        cartLocalDataSource.deleteCartProduct(productId)
    }

    override fun getCartProductInfoById(productId: Int): CartProductInfo? {
        val cartDataModel = cartLocalDataSource.getProductInfoById(productId) ?: return null
        val product = productRemoteDataSource.findProductById(cartDataModel.productId)
        return CartProductInfo(product, cartDataModel.count)
    }

    override fun getCartProductsInfo(limit: Int, offset: Int): CartProductInfoList {
        val cartDataModels = cartLocalDataSource.getProductsInfo(limit, offset)
        return CartProductInfoList(
            cartDataModels.makeInfoList(),
        )
    }

    override fun updateCartProductCount(productId: Int, count: Int) {
        val cartDataModel = CartDataModel(productId, count)
        cartLocalDataSource.updateProductCount(cartDataModel)
    }

    override fun getAllCartProductsInfo(): CartProductInfoList {
        return CartProductInfoList(cartLocalDataSource.getAllProductsInfo().makeInfoList())
    }

    private fun List<CartDataModel>.makeInfoList() = this.map {
        CartProductInfo(
            productRemoteDataSource.findProductById(it.productId),
            it.count,
        )
    }
}
