package woowacourse.shopping.presentation.cart

import woowacourse.shopping.presentation.model.ProductModel

interface CartListener {
    fun onAddClick(productModel: ProductModel)
    fun onRemoveClick(productModel: ProductModel)
    fun onCloseClick(productModel: ProductModel)
    fun changeSelectionProduct(productModel: ProductModel)
}
