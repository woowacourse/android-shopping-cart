package woowacourse.shopping.domain.product

class Products(
    private val value: List<Product>,
){
    fun getPage(page: Int, pageSize: Int): List<Product> {
        require(page >= 0) { "page는 0 이상이어야 합니다. page=$page" }
        require(pageSize > 0) { "pageSize는 1 이상이어야 합니다. pageSize=$pageSize" }

        val fromIndex = page * pageSize
        if (fromIndex >= value.size) return emptyList()

        val toIndex = minOf(fromIndex + pageSize, value.size)
        return value.subList(fromIndex, toIndex)
    }
}
