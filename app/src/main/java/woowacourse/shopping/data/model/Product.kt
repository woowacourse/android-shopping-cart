package woowacourse.shopping.data.model

typealias DataProduct = Product

data class Product(
    val id: Int,
    val name: String,
    val price: DataPrice,
    val imageUrl: String,
//    val selectedCount: DataProductCount = DataProductCount(0),
)
