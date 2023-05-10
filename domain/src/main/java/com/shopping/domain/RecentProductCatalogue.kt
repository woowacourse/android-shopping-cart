package com.shopping.domain

class RecentProductCatalogue(var productCatalogue: ProductCatalogue) {

    fun add(product: Product) {
        if (productCatalogue.contains(product)) {
            shiftToLast(product)
            return
        }
        addWithCapacityLimit(product)
    }

    private fun shiftToLast(product: Product) {
        val removed = productCatalogue.remove(product)
        productCatalogue = removed.add(product)
    }

    private fun addWithCapacityLimit(product: Product) {
        var removed: ProductCatalogue = productCatalogue
        if (productCatalogue.size >= 10) {
            removed = productCatalogue.removeFirst()
        }
        productCatalogue = removed.add(product)
    }
}
