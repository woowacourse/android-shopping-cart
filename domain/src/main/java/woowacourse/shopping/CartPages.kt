package woowacourse.shopping

class CartPages(
    private val cartProducts: Products,
    pageNumber: Counter = Counter(INITIAL_PAGE),
) {

    var pageNumber = pageNumber
        private set

    fun getNextPageProducts(): Products {
        pageNumber += PAGE_UNIT
        return getCurrentProducts()
    }

    fun getPreviousPageProducts(): Products {
        pageNumber -= PAGE_UNIT
        return getCurrentProducts()
    }

    fun getDeletedProducts(productId: Int): Products {
        cartProducts.deleteProduct(productId)
        return getCurrentProducts()
    }

    private fun getCurrentProducts() = cartProducts.getProductsInRange(
        (pageNumber.value - FIRST_PAGE) * PRODUCT_CART_SIZE,
        PRODUCT_CART_SIZE,
    )

    fun isNextPageAble(): Boolean {
        val lastPage = (cartProducts.size - FIRST_PAGE) / PRODUCT_CART_SIZE + FIRST_PAGE
        if (pageNumber.value == lastPage) {
            return false
        }
        return true
    }

    fun isPreviousPageAble(): Boolean {
        if (pageNumber.value == FIRST_PAGE) {
            return false
        }
        return true
    }

    companion object {
        private const val INITIAL_PAGE = 0
        private const val FIRST_PAGE = 1
        private const val PAGE_UNIT = 1
        private const val PRODUCT_CART_SIZE = 5
    }
}
