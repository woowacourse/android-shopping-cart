package woowacourse.shopping.ui

interface OnItemQuantityChangeListener {
    fun onAdd(productId: Long){
        // TODO: 상품을 추가하는 기능을 구현합니다.
    }

    fun onIncrease(productId: Long)

    fun onDecrease(productId: Long)
}
