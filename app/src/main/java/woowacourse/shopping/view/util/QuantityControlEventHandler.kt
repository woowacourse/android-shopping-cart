package woowacourse.shopping.view.util

interface QuantityControlEventHandler<T> {
    fun onQuantityIncreaseClick(item: T)

    fun onQuantityDecreaseClick(item: T)
}
