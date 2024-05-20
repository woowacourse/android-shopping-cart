package woowacourse.shopping.presentation.home

interface HomeItemEventListener {
    fun navigateToProductDetail(id: Long)

    fun loadNextPage()
}
