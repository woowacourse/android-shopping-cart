package com.example.domain.model

data class Pagination(
    private val cartProducts: CartProducts,
    val currentPage: Int
) {
    val allSize: Int
        get() = cartProducts.size

    val pageCount: Int
        get() {
            return calculateTotalPageCount(allSize)
        }

    val allList: List<CartProduct>
        get() = cartProducts.all

    val currentPageCartProducts: List<CartProduct>
        get() {
            val fromIndex = (currentPage - 1) * PAGE_LOAD_SIZE
            var toIndex = currentPage * PAGE_LOAD_SIZE
            if (toIndex > allSize) toIndex = allSize
            if (toIndex == fromIndex) return emptyList()
            return allList.subList(fromIndex, toIndex)
        }

    val isCurrentPageAllChecked: Boolean
        get() {
            if (currentPageCartProducts.isEmpty()) return false
            return currentPageCartProducts.all { it.checked }
        }

    val isAnyChecked: Boolean
        get() = allList.any { it.checked }

    val checkedCount: Int
        get() = allList.count { it.checked }

    val totalCheckedMoney: Int
        get() = cartProducts.totalCheckedMoney

    fun hasPreviousPage(): Boolean {
        return currentPage > 1
    }

    fun previousPage(): Pagination {
        if (hasPreviousPage().not()) return this
        return copy(currentPage = currentPage - 1)
    }

    fun hasNextPage(): Boolean {
        return currentPage < pageCount
    }

    fun nextPage(): Pagination {
        if (hasNextPage().not()) return this
        return copy(currentPage = currentPage + 1)
    }

    fun changeCountState(cartId: Long, count: Int): Pagination {
        val newCartProducts = cartProducts.changeCount(cartId, count)
        if (newCartProducts === cartProducts) return this
        return copy(cartProducts = newCartProducts)
    }

    fun remove(cartId: Long): Pagination {
        val newCartProducts = cartProducts.remove(cartId)
        val newSize = newCartProducts.size
        val newPageTotalCount = calculateTotalPageCount(newSize)
        if (currentPage > newPageTotalCount) return Pagination(newCartProducts, currentPage - 1)
        return copy(cartProducts = newCartProducts)
    }

    fun removeAllChecked(): Pagination {
        val newCartProducts = cartProducts.removeAllChecked()
        val newSize = newCartProducts.size
        val newPageTotalCount = calculateTotalPageCount(newSize)
        if (currentPage > newPageTotalCount) return Pagination(newCartProducts, 1)
        return copy(cartProducts = newCartProducts)
    }

    fun setCurrentPageAllChecked(newChecked: Boolean): Pagination {
        val currentPageCartIds = currentPageCartProducts.map { it.cartId }
        return copy(cartProducts = cartProducts.changeAllChecked(currentPageCartIds, newChecked))
    }

    fun changeChecked(cartId: Long, checked: Boolean): Pagination {
        return copy(cartProducts = cartProducts.changeChecked(cartId, checked))
    }

    private fun calculateTotalPageCount(size: Int): Int {
        if (size == 0) return 1
        return kotlin.math.ceil((size.toDouble() / PAGE_LOAD_SIZE)).toInt()
    }

    companion object {
        private const val PAGE_LOAD_SIZE = 5
    }
}
