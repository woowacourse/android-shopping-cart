package woowacourse.shopping.domain

data class Basket(val products: List<BasketProduct>) {
    fun add(basketProduct: BasketProduct): Basket =
        if (basketProduct in products) Basket(
            products.map {
                if (it == basketProduct) BasketProduct(
                    id = it.id,
                    count = it.count + basketProduct.count,
                    product = it.product
                ) else it
            }
        )
        else Basket(products + basketProduct)

    fun delete(basketProduct: BasketProduct): Basket =
        if (basketProduct in products) Basket(
            products.map {
                if (it == basketProduct) BasketProduct(
                    id = it.id,
                    count = it.count - basketProduct.count,
                    product = it.product
                ) else it
            }.filter { !it.count.isZero() }
        )
        else Basket((products - basketProduct).filter { !it.count.isZero() })

    fun remove(basketProduct: BasketProduct): Basket =
        Basket(products.toMutableList().apply { remove(basketProduct) })

    fun getCountByProductId(productId: Int): Int =
        products.find { it.product.id == productId }?.count?.value ?: 0

    fun getTotalPrice(): Int =
        products.fold(0) { acc, basketProduct -> acc + basketProduct.getTotalPrice().value }

    fun getCheckedProductsTotalPrice(): Int =
        products.fold(0) { acc, basketProduct -> if (basketProduct.isChecked) acc + basketProduct.getTotalPrice().value else acc }

    fun getSubBasketByStartId(startId: Int, subBasketSize: Int): Basket {
        val endIndex =
            if (startId + subBasketSize > products.lastIndex) products.lastIndex + 1 else startId + subBasketSize
        return Basket(products.subList(startId, endIndex))
    }

    fun updateCheck(basketProduct: BasketProduct) {
        products.find { it.product.id == basketProduct.product.id }?.isChecked = basketProduct.isChecked
    }

    fun getCheckedProductsCount(): Int =
        products.filter { it.isChecked }.size

    fun toggleAllCheck(isChecked: Boolean) {
        products.map { it.isChecked = isChecked }
    }

    fun allItemIsChecked(): Boolean =
        products.none { !it.isChecked }
}
