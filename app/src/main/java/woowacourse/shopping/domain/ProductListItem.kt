package woowacourse.shopping.domain

import java.time.LocalDateTime

sealed interface ProductListItem {
    data class RecentProductItems(val items: List<RecentProductItem>) : ProductListItem

    data class ShoppingProductItem(
        val id: Long,
        val name: String,
        val imgUrl: String,
        val price: Long,
        var quantity: Int = 0,
    ) : ProductListItem {
        fun toProduct() =
            Product(
                this.id,
                this.name,
                this.imgUrl,
                this.price,
            )

        companion object {
            fun fromProductsAndCarts(
                products: List<Product>,
                carts: List<Cart>,
            ): List<ShoppingProductItem> {
                return products.map { product ->
                    ShoppingProductItem(
                        product.id,
                        product.name,
                        product.imgUrl,
                        product.price,
                        carts.firstOrNull { product == it.product }?.quantity ?: 0,
                    )
                }
            }

            fun joinProductAndCart(
                product: Product,
                cart: Cart,
            ): ShoppingProductItem =
                ShoppingProductItem(
                    product.id,
                    product.name,
                    product.imgUrl,
                    product.price,
                    cart.quantity,
                )
        }
    }
}

data class RecentProductItem(
    val productId: Long,
    val name: String,
    val imgUrl: String,
    val dateTime: LocalDateTime,
)
