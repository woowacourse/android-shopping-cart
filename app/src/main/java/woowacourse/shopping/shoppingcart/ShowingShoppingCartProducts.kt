package woowacourse.shopping.shoppingcart

import woowacourse.shopping.model.Page
import woowacourse.shopping.model.ProductUiModel

class ShowingShoppingCartProducts : ShowingRule {

    override fun of(products: List<ProductUiModel>, page: Page): List<ProductUiModel> {
        val shoppingCartProducts = mutableListOf<ProductUiModel>()
        var currentIndex = page.value * COUNT_TO_READ

        if (currentIndex >= products.size) {
            return listOf()
        }
        repeat(COUNT_TO_READ) {
            shoppingCartProducts.add(products[currentIndex])
            if (products.lastIndex == currentIndex) {
                return shoppingCartProducts.toList()
            }
            currentIndex++
        }
        return shoppingCartProducts.toList()
    }

    companion object {
        private const val COUNT_TO_READ = 3
    }
}
