package woowacourse.shopping.model

data class Cart(
    val items: List<CartItem>,
) {
    fun getPagedItems(
        fromIndex: Int,
        pageSize: Int,
    ): List<CartItem> {
        require(pageSize >= 0) { "pageSize는 0 이상의 수여야 합니다." }
        require(fromIndex in 0..items.size) { "fromIndex는 0 이상의 정수이자, 사이즈를 벗어나는 index일 수 없습니다." }

        val toIndex = minOf(fromIndex + pageSize, items.size)
        return items.drop(fromIndex).take(pageSize)
    }
}
