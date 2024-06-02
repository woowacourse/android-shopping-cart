package woowacourse.shopping.productdetail.uimodel

sealed interface RecentProductState {
    data class Show(
        val name: String,
        val id: Long,
    ) : RecentProductState

    data object Same : RecentProductState

    data object NoRecentProduct : RecentProductState
}
