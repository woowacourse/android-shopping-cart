package woowacourse.shopping.model

import com.shopping.domain.CartProduct

class Paging(private var cartProducts: List<CartProduct>) {
    private val presentPageProducts: List<CartProduct>
        get() = loadPageProducts()

    private var index: PageIndex = PageIndex()

    fun getPageCount() = (index.fromIndex / PAGE_PRODUCT_UNIT) + PAGE_STEP

    fun loadPageProducts(): List<CartProduct> {
        if (cartProducts.isEmpty()) {
            return emptyList()
        }
        return cartProducts.subList(index.fromIndex, index.getToIndex(cartProducts.size))
    }

    fun updateCartProducts(updatedCartProducts: List<CartProduct>) {
        cartProducts = updatedCartProducts
    }

    fun isPossiblePageUp() = index.isLastIndex(cartProducts.size)

    fun isPossiblePageDown() = index.isFirstIndex()

    fun isLastIndexOfCurrentPage() = (index.fromIndex == cartProducts.size)

    fun subPage() {
        index = index.sub()
    }

    fun addPage() {
        index = index.add()
    }

    fun isAllItemProductSelected(): Boolean {
        if (presentPageProducts.isEmpty()) {
            return false
        }
        return presentPageProducts.all { product -> product.isSelected }
    }

    companion object {
        private const val PAGE_STEP = 1
        private const val PAGE_PRODUCT_UNIT = 3
    }
}
