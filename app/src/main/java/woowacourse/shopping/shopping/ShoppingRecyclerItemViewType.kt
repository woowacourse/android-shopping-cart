package woowacourse.shopping.shopping

enum class ShoppingRecyclerItemViewType {

    RECENT_VIEWED, PRODUCT;

    companion object {
        private const val INITIAL_POSITION = 0

        fun valueOf(position: Int): ShoppingRecyclerItemViewType {
            return when (position) {
                INITIAL_POSITION -> RECENT_VIEWED
                else -> PRODUCT
            }
        }
    }
}
