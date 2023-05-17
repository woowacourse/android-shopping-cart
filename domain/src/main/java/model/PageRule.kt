package model

interface PageRule {

    fun getProductsOfPage(
        products: List<ShoppingCartProduct>,
        page: Page
    ): List<ShoppingCartProduct>

    fun getPageOfEnd(
        totalProductsSize: Int,
    ): Page
}
