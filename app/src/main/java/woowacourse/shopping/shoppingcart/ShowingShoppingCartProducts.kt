package woowacourse.shopping.shoppingcart

import woowacourse.shopping.model.Page
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.util.CART_PRODUCT_TO_READ

class ShowingShoppingCartProducts : ShowingRule {

    override fun of(products: List<ProductUiModel>, page: Page): List<ProductUiModel> {
        val shoppingCartProducts = mutableListOf<ProductUiModel>()
        var currentIndex = page.value * CART_PRODUCT_TO_READ

        if (currentIndex >= products.size) {
            return listOf()
        }
        repeat(CART_PRODUCT_TO_READ) {
            shoppingCartProducts.add(products[currentIndex])
            if (products.lastIndex == currentIndex) {
                return shoppingCartProducts.toList()
            }
            currentIndex++
        }
        return shoppingCartProducts.toList()
    }
}
