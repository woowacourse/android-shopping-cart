package woowacourse.shopping.domain.product

data class Product(
    val id: Int,
    val imageUrl: ImageUrl,
    val name: ProductName,
    val price: Price,
){
    fun isSameProduct(product: Product?): Boolean = id == product?.id
}
