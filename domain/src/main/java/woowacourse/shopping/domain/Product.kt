package woowacourse.shopping.domain

data class Product(
    val id: Int,
    val name: String,
    val price: Price,
    val imageUrl: String,
//    val selectedCount: ProductCount = ProductCount(0),
) {
//    fun plusCount(): Product =
//        copy(selectedCount = selectedCount + 1)
//
//    fun minusCount(): Product =
//        copy(selectedCount = selectedCount - 1)
//
//    fun isEmpty(): Boolean = selectedCount.isZero()
}
