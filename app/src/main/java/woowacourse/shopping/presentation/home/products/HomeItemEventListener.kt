package woowacourse.shopping.presentation.home.products

interface HomeItemEventListener {
    fun navigateToProductDetail(id: Long)

    fun loadNextPage()

    fun onNavigatedBack(changedIds: LongArray)
}
