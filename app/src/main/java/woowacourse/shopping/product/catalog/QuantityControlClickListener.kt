package woowacourse.shopping.product.catalog

fun interface QuantityControlClickListener {
    fun onClick(
        event: Int,
        product: ProductUiModel,
    )
}
