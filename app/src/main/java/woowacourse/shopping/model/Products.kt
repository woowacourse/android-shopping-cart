package woowacourse.shopping.model

class Products(
    products: List<Product>,
) : Iterable<Product> {
    private val value = products.toList()

    override fun iterator(): Iterator<Product> = value.iterator()

    fun getPagedProducts(
        fromIndex: Int,
        loadSize: Int,
    ): Products {
        require(loadSize >= 0) { "loadSize는 0 이상의 수여야 합니다." }
        require(fromIndex in 0..value.size) { "fromIndex는 0 이상의 정수이자, 사이즈를 벗어나는 index일 수 없습니다." }

        val toIndex = minOf(fromIndex + loadSize, value.size)

        return Products(value.subList(fromIndex, toIndex))
    }
}
