package com.example.domain.model

class CartProducts(
    private val list: List<CartProduct>
) {
    val all: List<CartProduct>
        get() = list.map { it.copy() }

    val size: Int
        get() = list.size

    val totalCheckedMoney: Int
        get() = list.filter { it.checked }.sumOf { it.count * it.product.price.value }

    init {
        require(list.all { it.count >= 1 }) { ERROR_CART_PRODUCT_COUNT_MIN_OVER_ZERO }
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

    fun changeAllChecked(cartIds: List<Long>, checked: Boolean): CartProducts {
        val newList = all.map {
            if (it.cartId in cartIds) it.copy(checked = checked)
            else it
        }
        return CartProducts(newList)
    }

    companion object {
        private const val ERROR_CART_PRODUCT_COUNT_MIN_OVER_ZERO =
            "[ERROR] 장바구니 상품의 갯수는 종류별로 각 1개 이상이여야 합니다"
    }
}
