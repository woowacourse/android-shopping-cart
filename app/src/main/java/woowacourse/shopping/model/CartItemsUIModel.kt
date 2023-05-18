package woowacourse.shopping.model

class CartItemsUIModel(products: List<CartProductUIModel>) {
    private var _products = products.toMutableList()
    val products = _products.toList()

    fun updateCartItems(data: List<CartProductUIModel>): CartItemsUIModel =
        CartItemsUIModel(data)

    fun caculatePrice(): Int {
        return _products.fold(0) { total, product -> total + (product.count.get() * product.product.price) }
    }
}
