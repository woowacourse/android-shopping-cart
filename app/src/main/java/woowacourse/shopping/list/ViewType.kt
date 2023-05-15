package woowacourse.shopping.list

enum class ViewType {
    RECENT_PRODUCT,
    PRODUCT,
    LOAD_MORE;

    companion object {
        fun getType(position: Int): ViewType {
            if (position == 0) return RECENT_PRODUCT
            if ((position + 1) % 20 == 0) return LOAD_MORE
            return PRODUCT
        }

        fun get(type: Int): ViewType {
            return values().first { it.ordinal == type }
        }
    }
}
