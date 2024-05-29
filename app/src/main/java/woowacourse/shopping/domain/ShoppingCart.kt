package woowacourse.shopping.domain

data class ShoppingCart(val items: List<ShoppingCartItem>) {
    fun totalItemQuantity(): Int {
        return items.sumOf { it.quantity }
    }

    fun updateItem(updatedItem: ShoppingCartItem): ShoppingCart {
        return items.map {
            if (it.product.id == updatedItem.product.id) updatedItem else it
        }.let(::ShoppingCart)
    }

    fun addItem(newItem: ShoppingCartItem): ShoppingCart {
        val isAlreadyExist = items.any { it.product == newItem.product }
        return if (isAlreadyExist) {
            items.map { item ->
                if (item.product.id == newItem.product.id) {
                    item.copy(quantity = item.quantity + newItem.quantity)
                } else {
                    item
                }
            }.let(::ShoppingCart)
        } else {
            copy(items = items + newItem)
        }
    }

    fun deleteItem(selectedItem: ShoppingCartItem): ShoppingCart =
        items.filterNot {
            it.product.id == selectedItem.product.id
        }.let(::ShoppingCart)

    fun deleteItemById(productId: Long): ShoppingCart = items.filterNot { it.product.id == productId }.let(::ShoppingCart)
}
