package woowacourse.shopping.data.cart

interface CartLocalDataSource {
    fun addProduct(productId: Int)
    fun deleteCartProduct(productId: Int)
    fun updateProductCount(cartProductInfo: CartDataModel)
    fun getProductsInfo(limit: Int, offset: Int): List<CartDataModel>
    fun getAllProductsInfo(): List<CartDataModel>
    fun getProductInfoById(id: Int): CartDataModel?
}
