package woowacourse.shopping.presentation.home

interface HomeItemClickListener {
    fun navigateToProductDetail(id: Long)

    fun loadNextPage()
}
