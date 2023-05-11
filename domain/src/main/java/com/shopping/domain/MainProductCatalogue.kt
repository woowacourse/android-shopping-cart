package com.shopping.domain

class MainProductCatalogue(val items: List<Product> = emptyList()) : ProductCatalogue {
    override val size: Int = items.size

    override fun addAll(products: List<Product>): ProductCatalogue =
        MainProductCatalogue(items + products)

    override fun contains(product: Product): Boolean =
        items.contains(product)

    override fun add(product: Product): ProductCatalogue =
        MainProductCatalogue(items + product)

    override fun remove(product: Product): ProductCatalogue =
        MainProductCatalogue(items - product)

    override fun removeFirst(): ProductCatalogue {
        val first = items.firstOrNull() ?: return this
        return MainProductCatalogue(items - first)
    }
}
