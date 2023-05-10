package woowacourse.shopping.feature.main

enum class ViewType() {
    HORIZONTAL,
    PRODUCT;

    companion object {
        fun getType(position: Int): ViewType {
            if (position == 0) return HORIZONTAL
            return PRODUCT
        }

        fun get(type: Int): ViewType {
            return values().first { it.ordinal == type }
        }
    }
}
