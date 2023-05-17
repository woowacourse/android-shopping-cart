package woowacourse.shopping.presentation.ui.home.adapter

enum class HomeViewType {
    PRODUCT,
    RECENTLY_VIEWED,
    SHOW_MORE,
    ;

    companion object {
        fun valueOf(value: Int): HomeViewType =
            HomeViewType.values().find { viewHolder ->
                viewHolder.ordinal == value
            } ?: throw IllegalArgumentException()
    }
}
