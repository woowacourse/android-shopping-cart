package woowacourse.shopping.domain.model

data class CatalogProducts(
    val products: List<CatalogProduct>,
    val hasMore: Boolean,
) {
    val catalogProductsQuantity: Int get() = products.sumOf { it.quantity }

    operator fun plus(other: CatalogProducts): CatalogProducts {
        val mergedProducts = products + other.products
        return CatalogProducts(
            products = mergedProducts,
            hasMore = other.hasMore,
        )
    }

    fun updateCatalogProductQuantity(
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

    fun updateCatalogProduct(newProduct: CatalogProduct): CatalogProducts {
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

    fun updateCatalogProducts(newProducts: List<CatalogProduct>): CatalogProducts {
        val updatedProducts =
            products.map { product ->
                newProducts.find { it.product.id == product.product.id } ?: product
            }
        return copy(products = updatedProducts)
    }

    companion object {
        val EMPTY_CATALOG_PRODUCTS =
            CatalogProducts(emptyList(), false)
    }
}
