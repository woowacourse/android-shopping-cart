package woowacourse.shopping.shoppingcart

import woowacourse.shopping.model.Page
import woowacourse.shopping.model.ProductUiModel

object ShowingCartProductsPageRule : PageRule {

    override val itemCountOnEachPage: Int = 3

    override fun getProductsOfPage(products: List<ProductUiModel>, page: Page): List<ProductUiModel> {
        val shoppingCartProducts = mutableListOf<ProductUiModel>()
        var currentIndex = page.value * itemCountOnEachPage

        if (currentIndex >= products.size) {
            return listOf()
        }
        repeat(itemCountOnEachPage) {
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
        return Page((totalProductsSize - 1) / itemCountOnEachPage)
    }
}
