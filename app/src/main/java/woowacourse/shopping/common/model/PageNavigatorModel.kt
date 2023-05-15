package woowacourse.shopping.common.model

data class PageNavigatorModel(
    val visibility: Int,
    val previousPageAvailable: Boolean,
    val nextPageAvailable: Boolean,
    val currentPage: Int
)
