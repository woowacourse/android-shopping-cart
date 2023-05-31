package woowacourse.shopping.view.shoppingmain

import woowacourse.shopping.model.uimodel.ProductUIModel

interface ShoppingMainClickListener {
    fun productOnClick(productUIModel: ProductUIModel)
    fun findCartCountById(productUIModel: ProductUIModel): Int
    fun addToCartOnClick(productUIModel: ProductUIModel)
    fun saveCartProductCount(productUIModel: ProductUIModel, count: Int)
}
