package woowacourse.shopping.domain.model

data class CatalogProducts(
    val products: List<CartProduct>,
    val hasMore: Boolean,
) {
    val cartProductsQuantity: Int get() = products.sumOf { it.quantity }

    operator fun plus(other: CatalogProducts): CatalogProducts {
        val mergedProducts = products + other.products
        return CatalogProducts(
            products = mergedProducts,
            hasMore = hasMore || other.hasMore,
        )
    }

    fun updateCartProductQuantity(
        productId: Int,
        quantity: Int,
    ): CatalogProducts {
        val updatedProducts =
            products.map { product ->
                if (product.product.id == productId) {
                    product.copy(quantity = quantity)
                } else {
                    product
                }
            }
        return copy(products = updatedProducts)
    }

    fun updateCartProduct(newProduct: CartProduct): CatalogProducts {
        val updatedProducts =
            products.map { product ->
                if (product.product.id == newProduct.product.id) {
                    newProduct
                } else {
                    product
                }
            }
        return copy(products = updatedProducts)
    }

    fun updateCartProducts(newProducts: List<CartProduct>): CatalogProducts {
        val updatedProducts =
            products.map { product ->
                newProducts.find { it.product.id == product.product.id } ?: product
            }
        return copy(products = updatedProducts)
    }

    companion object {
        val EMPTY_CATALOG_PRODUCTS =
            CatalogProducts(
                products = emptyList(),
                hasMore = false,
            )
    }
}
