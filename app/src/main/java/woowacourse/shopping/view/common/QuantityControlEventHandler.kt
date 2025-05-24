package woowacourse.shopping.view.common

interface QuantityControlEventHandler<T> {
    fun onQuantityIncreaseClick(item: T)

    fun onQuantityDecreaseClick(item: T)
}
