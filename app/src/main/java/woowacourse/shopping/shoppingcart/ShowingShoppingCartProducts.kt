package woowacourse.shopping.shoppingcart

import woowacourse.shopping.model.ProductUiModel

class ShowingShoppingCartProducts : ShowingRule {

    override fun of(products: List<ProductUiModel>, page: Int): List<ProductUiModel> {
        val shoppingCartProducts = mutableListOf<ProductUiModel>()

        repeat(COUNT_TO_READ) {
            val index = page * COUNT_TO_READ + it

            shoppingCartProducts.add(products[index])
            if (products.lastIndex == index) {
                return@repeat
            }
        }
        return shoppingCartProducts.toList()
    }

    companion object {
        private const val COUNT_TO_READ = 3
    }
}
