package woowacourse.shopping.model

data class PageIndex(val fromIndex: Int = INIT_INDEX) {

    init {
        require(fromIndex >= INIT_INDEX) { INDEX_ERROR_MESSAGE }
    }

    fun add(): PageIndex =
        PageIndex(fromIndex + PAGE_PRODUCT_UNIT)

    fun sub(): PageIndex =
        PageIndex(maxOf((fromIndex - PAGE_PRODUCT_UNIT), INIT_INDEX))

    fun getToIndex(size: Int): Int =
        minOf(fromIndex + PAGE_PRODUCT_UNIT, size)

    fun isFirstIndex(): Boolean = fromIndex != INIT_INDEX

    fun isLastIndex(size: Int): Boolean = fromIndex + PAGE_PRODUCT_UNIT < size

    companion object {
        private const val INIT_INDEX = 0
        private const val PAGE_PRODUCT_UNIT = 3
        private const val INDEX_ERROR_MESSAGE = "페이지 index는 0 이상이어야 합니다."
    }
}
