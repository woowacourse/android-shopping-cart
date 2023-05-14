package woowacourse.shopping.shoppingcart

import woowacourse.shopping.model.Page
import woowacourse.shopping.model.ShoppingCartProductUiModel

object ShowingCartProductsPageRule : PageRule {

    private const val ITEM_COUNT_ON_EACH_PAGE = 3

    override fun getProductsOfPage(products: List<ShoppingCartProductUiModel>, page: Page): List<ShoppingCartProductUiModel> {
        val shoppingCartProducts = mutableListOf<ShoppingCartProductUiModel>()
        var currentIndex = page.value * ITEM_COUNT_ON_EACH_PAGE

        if (currentIndex >= products.size) {
            return listOf()
        }
        repeat(ITEM_COUNT_ON_EACH_PAGE) {
            shoppingCartProducts.add(products[currentIndex])
            if (products.lastIndex == currentIndex) {
                return shoppingCartProducts.toList()
            }
            currentIndex++
        }
        return shoppingCartProducts.toList()
    }

    override fun getPageOfEnd(totalProductsSize: Int): Page {
        if (totalProductsSize == 0) {
            return Page()
        }
        return Page((totalProductsSize - 1) / ITEM_COUNT_ON_EACH_PAGE)
    }
}
