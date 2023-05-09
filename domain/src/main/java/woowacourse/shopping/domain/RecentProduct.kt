package woowacourse.shopping.domain

data class RecentProduct(val limit: Int, val products: List<Product>) {

    fun add(product: Product): RecentProduct {
        val recentProduct = getRecentProduct()
        return RecentProduct(limit, recentProduct.products + product)
    }

    private fun getRecentProduct(): RecentProduct {
        if (isOverLimit()) {
            return removeFirst()
        }
        return this
    }

    private fun isOverLimit() = products.size >= limit

    private fun removeFirst(): RecentProduct {
        return RecentProduct(limit, products.drop(1))
    }
}
