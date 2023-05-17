package com.example.domain.model

class CartProducts(
    private val list: List<CartProduct>
) {
    val all: List<CartProduct>
        get() = list.map { it.copy() }

    val size: Int
        get() = list.size

    init {
        require(list.all { it.count > 0 }) { }
    }

    fun changeCount(cartId: Long, count: Int): CartProducts {
        if (count < 1) return this
        val newList = all
        val find = newList.find { it.cartId == cartId } ?: return this
        find.count = count
        return CartProducts(newList)
    }

    fun remove(cartId: Long): CartProducts {
        return CartProducts(all.filterNot { it.cartId == cartId })
    }

    fun removeAllChecked(): CartProducts {
        return CartProducts(all.filterNot { it.checked })
    }

    fun changeChecked(cartId: Long, checked: Boolean): CartProducts {
        val newList = all
        val find = newList.find { it.cartId == cartId } ?: return this
        find.checked = checked
        return CartProducts(newList)
    }

    fun changeAllChecked(checked: Boolean): CartProducts {
        return CartProducts(all.map { it.copy(checked = checked) })
    }
}