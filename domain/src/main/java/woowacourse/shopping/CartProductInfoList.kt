package woowacourse.shopping

class CartProductInfoList(cartProductInfos: List<CartProductInfo>) {
    private val _items = cartProductInfos.toMutableList()
    val items get() = _items.toList()

    val size: Int get() = items.size
    val count: Int get() = items.sumOf { it.count }
    val totalPrice: Int get() = items.sumOf { it.totalPrice }
    val orders: CartProductInfoList
        get() {
            return CartProductInfoList(items.filter { it.isOrdered })
        }
    val isAllOrdered: Boolean get() = items.all { it.isOrdered }

    fun add(item: CartProductInfo): CartProductInfoList {
        if (items.none { it.product.id == item.product.id }) {
            _items.add(item)
        }
        return CartProductInfoList(items)
    }

    fun addAll(newItems: List<CartProductInfo>): CartProductInfoList {
        newItems.forEach { add(it) }
        return CartProductInfoList(items)
    }

    fun delete(item: CartProductInfo): CartProductInfoList {
        _items.removeAll { it.product.id == item.product.id }
        return CartProductInfoList(items)
    }

    fun updateItemCount(cartProductInfo: CartProductInfo, count: Int): CartProductInfoList {
        val index = findIndexByProductId(cartProductInfo.product.id) ?: return this
        _items[index] = _items[index].setCount(count)
        return CartProductInfoList(items)
    }

    fun updateItemOrdered(
        cartProductInfo: CartProductInfo,
        isOrdered: Boolean,
    ): CartProductInfoList {
        val index = findIndexByProductId(cartProductInfo.product.id) ?: return this
        _items[index] = items[index].setOrderState(isOrdered)
        return CartProductInfoList(items)
    }

    fun updateAllItemOrdered(isOrdered: Boolean): CartProductInfoList {
        items.forEach { updateItemOrdered(it, isOrdered) }
        return CartProductInfoList(items)
    }

    fun getItemsInRange(startIndex: Int, size: Int): CartProductInfoList {
        return when {
            startIndex > items.size -> this
            startIndex + size > items.size -> CartProductInfoList(
                items.subList(
                    startIndex,
                    items.size,
                ),
            )
            else -> CartProductInfoList(items.subList(startIndex, startIndex + size))
        }
    }

    fun replaceItem(newItem: CartProductInfo): CartProductInfoList {
        _items.forEachIndexed { index, item ->
            if (item.product.id == newItem.product.id) _items[index] = newItem
        }
        return CartProductInfoList(items)
    }

    fun replaceItemList(newItemList: CartProductInfoList): CartProductInfoList {
        newItemList.items.forEach { replaceItem(it) }
        return CartProductInfoList(items)
    }

    private fun findIndexByProductId(id: Int): Int? {
        items.forEachIndexed { index, cartProductInfo ->
            if (cartProductInfo.product.id == id) return index
        }
        return null
    }
}
