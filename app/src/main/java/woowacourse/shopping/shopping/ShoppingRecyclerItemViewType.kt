package woowacourse.shopping.shopping

enum class ShoppingRecyclerItemViewType {

    RECENT_VIEWED, PRODUCT;

    companion object {
        private const val INITIAL_POSITION = 0
        private const val VIEW_TYPE_ERROR = "해당 타입의 뷰는 존재하지 않습니다."

        fun valueOf(position: Int): ShoppingRecyclerItemViewType {

            return when (position) {
                INITIAL_POSITION -> RECENT_VIEWED
                else -> PRODUCT
            }
        }

        fun find(viewType: Int): ShoppingRecyclerItemViewType {

            return values().find { it.ordinal == viewType }
                ?: throw IllegalArgumentException(VIEW_TYPE_ERROR)
        }
    }
}
