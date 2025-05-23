package woowacourse.shopping.product.catalog

fun interface QuantityControlListener {
    fun onClick(
        event: Int,
        product: ProductUiModel,
    )
}
