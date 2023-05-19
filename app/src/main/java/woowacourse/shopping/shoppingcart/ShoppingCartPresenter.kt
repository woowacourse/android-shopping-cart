package woowacourse.shopping.shoppingcart

import model.Price
import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.util.CART_PRODUCT_TO_READ
import woowacourse.shopping.util.toUiModel

class ShoppingCartPresenter(
    private val view: ShoppingCartContract.View,
    private val repository: ShoppingRepository,
) : ShoppingCartContract.Presenter {

    private var numberOfReadShoppingCartProduct: Int = 1

    private fun loadRangeOfShoppingCartProducts(): List<CartProductUiModel> {
        val cartProducts = repository.selectShoppingCartProducts(
            0,
            numberOfReadShoppingCartProduct * CART_PRODUCT_TO_READ,
        ).map { it.toUiModel() }
        numberOfReadShoppingCartProduct++

        return cartProducts
    }

    override fun loadShoppingCartProducts() {
        val products = loadRangeOfShoppingCartProducts()
        val productsSize = repository.getShoppingCartProductsSize()

        updateOrderInfo()
        view.setUpShoppingCartView(products, productsSize)
        view.checkAllBtnOrNot()
    }

    override fun removeShoppingCartProduct(id: Int) {
        repository.deleteFromShoppingCart(id)
        updateOrderInfo()
    }

    override fun readMoreShoppingCartProducts() {
        val products = loadRangeOfShoppingCartProducts()
        view.showMoreShoppingCartProducts(products)
    }

    override fun changeShoppingCartProductCount(id: Int, count: Int) {
        repository.updateShoppingCartCount(id, count)
    }

    override fun changeShoppingCartProductSelection(id: Int, isSelected: Boolean) {
        repository.updateShoppingCartSelection(id, isSelected)
        updateOrderInfo()
        view.checkAllBtnOrNot()
    }

    override fun checkAllBox(products: List<CartProductUiModel>, isSelected: Boolean) {
        products.forEach {
            repository.updateShoppingCartSelection(it.product.id, isSelected)
        }
        updateOrderInfo()
    }

    private fun updateOrderInfo() {
        var totalPrice = Price(0)
        val selectedProducts = repository.getSelectedShoppingCartProducts()
        selectedProducts.forEach { totalPrice += (it.product.price * it.count) }
        view.updateTotalInfo(totalPrice.value, selectedProducts.size)
    }
}
