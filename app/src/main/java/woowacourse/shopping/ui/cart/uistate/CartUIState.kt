package woowacourse.shopping.ui.cart.uistate

import woowacourse.shopping.domain.CartProduct

data class CartUIState(
    val imageUrl: String,
    val name: String,
    val price: Int,
    val id: Long,
    val count: Int,
) {
    var isChecked: Boolean = false
        private set

    fun updateCheckedState(state: Boolean) {
        isChecked = state
    }

    companion object {
        fun from(product: CartProduct): CartUIState =
            CartUIState(product.imageUrl, product.name, product.price, product.id, product.count)
    }
}

/*
* presenter has a list of Checked Items
*
* send event to adapter
* - presenter.addChecked -> add item to the list
* - presenter.removeChecked -> delete item from the list
* getCount: size of the list
* totalPrice: get price of the list (using fold)
*
* how to handle "total checkbox" ?
* - total checkbox checked -> {
*   set price of all products in the cart
*   set count as size of the cart
*   add all data to the list from the cart
* }
* - total checkbox unchecked -> {
*   set price of all products in the cart
*   set count as size of the cart
*   but clear the data of the list
* }
*
* when the page changed -> {
*   when ui state mapping ->
*       send whether if the list contains the item
* }
*
* when each checkbox changed -> {
*   check if all items checked (size of the list) ->
*       update total checkbox
* }
* */
