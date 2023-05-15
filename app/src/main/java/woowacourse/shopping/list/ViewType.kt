package woowacourse.shopping.list

enum class ViewType() {
    HORIZONTAL,
    PRODUCT,
    ADD;

    companion object {
        fun getType(position: Int): ViewType {
            if (position == 0) return HORIZONTAL
            if ((position + 1) % 20 == 0) return ADD
            return PRODUCT
        }

        fun get(type: Int): ViewType {
            return values().first { it.ordinal == type }
        }
    }
}
