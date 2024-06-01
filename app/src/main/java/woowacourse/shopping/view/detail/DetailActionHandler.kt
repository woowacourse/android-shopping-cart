package woowacourse.shopping.view.detail

interface DetailActionHandler {
    fun onCloseButtonClicked()

    fun onAddCartButtonClicked()

    fun onLastViewedItemClicked(productId: Long)
}
