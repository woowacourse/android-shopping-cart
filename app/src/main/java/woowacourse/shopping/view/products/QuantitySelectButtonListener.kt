package woowacourse.shopping.view.products

interface QuantitySelectButtonListener {
    fun increase(productId: Long)

    fun decrease(productId: Long)
}
