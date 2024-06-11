package woowacourse.shopping.presentation.shopping.product

interface ProductAction {
    fun onClickItem(id: Long)
    fun moreItems()
    fun increaseCount(id: Long)
    fun decreaseCount(id: Long)
}
