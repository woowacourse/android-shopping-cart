package woowacourse.shopping.model

class CartPages(
    private val cartProducts: CartProducts,
    pageNumber: Counter = Counter(FIRST_PAGE),
) {

    var pageNumber = pageNumber
        private set

    fun goNextPageProducts() {
        pageNumber += PAGE_UNIT
    }

    fun goPreviousPageProducts() {
        pageNumber -= PAGE_UNIT
    }

    fun deleteProducts(product: Product) {
        cartProducts.deleteProduct(product)
    }

    fun subCountProducts(product: Product) {
        cartProducts.subProductByCount(product, COUNT_UNIT)
    }

    fun addCountProducts(product: Product) {
        cartProducts.addProductByCount(product, COUNT_UNIT)
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
        private const val FIRST_PAGE = 1
        private const val PAGE_UNIT = 1
        private const val PRODUCT_CART_SIZE = 5
        private const val COUNT_UNIT = 1
    }
}
