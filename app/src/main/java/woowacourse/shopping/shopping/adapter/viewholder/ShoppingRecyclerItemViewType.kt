package woowacourse.shopping.shopping.adapter.viewholder

enum class ShoppingRecyclerItemViewType {

    RECENT_VIEWED, PRODUCT, READ_MORE;

    companion object {
        private const val INITIAL_POSITION = 0
        private const val VIEW_TYPE_ERROR = "해당 타입의 뷰는 존재하지 않습니다."

        fun valueOf(position: Int, shoppingItemsSize: Int): ShoppingRecyclerItemViewType {
            return when (position) {
                INITIAL_POSITION -> RECENT_VIEWED
                shoppingItemsSize -> READ_MORE
                else -> PRODUCT
            }
        }

        fun find(viewType: Int): ShoppingRecyclerItemViewType {

            return values().find { it.ordinal == viewType }
                ?: throw NoSuchElementException(VIEW_TYPE_ERROR)
        }
    }
}
