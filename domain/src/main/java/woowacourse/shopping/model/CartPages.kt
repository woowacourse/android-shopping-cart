package woowacourse.shopping.model

class CartPages(
    private val cartProducts: CartProducts,
    pageNumber: Counter = Counter(INITIAL_PAGE),
) {

    var pageNumber = pageNumber
        private set

    fun getNextPageProducts(): CartProducts {
        pageNumber += PAGE_UNIT
        return getCurrentProducts()
    }

    fun getPreviousPageProducts(): CartProducts {
        pageNumber -= PAGE_UNIT
        return getCurrentProducts()
    }

    fun getDeletedProducts(product: Product): CartProducts {
        cartProducts.deleteProduct(product)
        return getCurrentProducts()
    }

    fun getSubCountProducts(product: Product): CartProducts {
        cartProducts.subProductByCount(product, COUNT_UNIT)
        return getCurrentProducts()
    }

    fun getAddCountProducts(product: Product): CartProducts {
        cartProducts.addProductByCount(product, COUNT_UNIT)
        return getCurrentProducts()
    }

    fun getCurrentProducts() = cartProducts.getProductsInRange(getStartIndex(), PRODUCT_CART_SIZE)

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

    fun changeSelectedProduct(product: Product) {
        cartProducts.changeSelectedProduct(product)
    }

    fun selectPageProducts() {
        cartProducts.selectProductsRange(getStartIndex(), PRODUCT_CART_SIZE)
    }

    fun unselectPageProducts() {
        cartProducts.unselectProductsRange(getStartIndex(), PRODUCT_CART_SIZE)
    }

    fun isAllProductSelected(): Boolean =
        cartProducts.isProductSelectedByRange(getStartIndex(), PRODUCT_CART_SIZE)

    private fun getStartIndex() = (pageNumber.value - FIRST_PAGE) * PRODUCT_CART_SIZE

    fun getSelectedProductsPrice() = cartProducts.getSelectedProductsPrice()

    fun getSelectedProductsCount() = cartProducts.getSelectedProductsTotalCount()

    companion object {
        private const val INITIAL_PAGE = 0
        private const val FIRST_PAGE = 1
        private const val PAGE_UNIT = 1
        private const val PRODUCT_CART_SIZE = 5
        private const val COUNT_UNIT = 1
    }
}
