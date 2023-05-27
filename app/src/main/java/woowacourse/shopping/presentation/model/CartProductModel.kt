package woowacourse.shopping.presentation.model

interface CartProductModel {
    val cartId: Long
    val productModel: ProductModel
    val count: Int
}
