package woowacourse.shopping.shoppingcart

import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.model.Page
import woowacourse.shopping.util.CART_PRODUCT_TO_READ

class ShowingShoppingCartProducts : ShowingRule {

    override fun of(products: List<CartProductUiModel>, page: Page): List<CartProductUiModel> {
        val shoppingCartProducts = mutableListOf<CartProductUiModel>()
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
