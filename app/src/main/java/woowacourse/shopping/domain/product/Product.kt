package woowacourse.shopping.domain.product

import java.util.UUID

data class Product(
    val id: String = UUID.randomUUID().toString(),
    val imageUrl: ImageUrl,
    val name: ProductName,
    val price: Price,
){
    fun isSameProduct(product: Product): Boolean{
        return this.id == product.id
    }
}
