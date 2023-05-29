package woowacourse.shopping.data.cart

import woowacourse.shopping.CartProductInfo
import woowacourse.shopping.data.product.ProductRemoteDataSource
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository

class CartRepositoryImpl constructor(
    private val cartLocalDataSource: CartLocalDataSource,
    productRemoteDataSource: ProductRemoteDataSource,
) : CartRepository {
    private val productRepository: ProductRepository =
        ProductRepositoryImpl(productRemoteDataSource)

    override fun putProductInCart(productId: Int) {
        cartLocalDataSource.addProduct(productId)
    }

    override fun deleteCartProductId(productId: Int) {
        cartLocalDataSource.deleteCartProduct(productId)
    }

    override fun getCartProductInfoById(productId: Int, onSuccess: (CartProductInfo?) -> Unit) {
        val cartDataModel = cartLocalDataSource.getProductInfoById(productId)
        if (cartDataModel == null) {
            onSuccess(null)
            return
        }
        productRepository.findProductById(id = cartDataModel.productId) { product ->
            if (product != null) {
                onSuccess(CartProductInfo(product, cartDataModel.count))
            } else {
                onSuccess(null)
            }
        }
    }

    override fun getCartProductsInfo(
        limit: Int,
        offset: Int,
        onSuccess: (List<CartProductInfo>) -> Unit,
    ) {
        val cartDataModels = cartLocalDataSource.getProductsInfo(limit, offset)
        if (cartDataModels.isEmpty()) {
            onSuccess(emptyList())
            return
        }
        val cartProductInfoList = mutableListOf<CartProductInfo>()
        getCartProducts(0, cartProductInfoList, cartDataModels, onSuccess)
    }

    private fun getCartProducts(
        index: Int,
        cartProductInfoList: MutableList<CartProductInfo>,
        cartDataModels: List<CartDataModel>,
        onSuccess: (List<CartProductInfo>) -> Unit,
    ) {
        getCartProductInfoById(productId = cartDataModels[index].productId) {
            if (it != null) cartProductInfoList.add(it)
            if (index == cartDataModels.lastIndex) onSuccess(cartProductInfoList)
            if (index + 1 <= cartDataModels.lastIndex) {
                getCartProducts(index + 1, cartProductInfoList, cartDataModels, onSuccess)
            }
        }
    }

    override fun getAllCartProductsInfo(onSuccess: (List<CartProductInfo>) -> Unit) {
        val cartDataModels = cartLocalDataSource.getAllProductsInfo()
        if (cartDataModels.isEmpty()) {
            onSuccess(emptyList())
            return
        }
        val cartProductInfoList = mutableListOf<CartProductInfo>()
        getCartProducts(0, cartProductInfoList, cartDataModels, onSuccess)
    }

    override fun updateCartProductCount(productId: Int, count: Int) {
        val cartDataModel = CartDataModel(productId, count)
        cartLocalDataSource.updateProductCount(cartDataModel)
    }
}
