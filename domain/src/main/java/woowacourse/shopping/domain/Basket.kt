package woowacourse.shopping.domain

data class Basket(val products: List<BasketProduct>) {
    fun add(basketProduct: BasketProduct): Basket =
        if (basketProduct in products) Basket(
            products.map { plusCountTargetBasketProduct(it, basketProduct) }
        )
        else Basket(products + basketProduct)

    private fun plusCountTargetBasketProduct(
        existingBasketProduct: BasketProduct,
        target: BasketProduct
    ): BasketProduct {
        val processedCount = existingBasketProduct.count + target.count
        return processTargetBasketProduct(existingBasketProduct, target, processedCount)
    }

    fun delete(basketProduct: BasketProduct): Basket =
        if (basketProduct in products) Basket(
            products.map { minusCountTargetBasketProduct(it, basketProduct) }
                .filter { !it.count.isZero() }
        )
        else Basket((products - basketProduct).filter { !it.count.isZero() })

    private fun minusCountTargetBasketProduct(
        existingBasketProduct: BasketProduct,
        target: BasketProduct
    ): BasketProduct {
        val processedCount = existingBasketProduct.count - target.count
        return processTargetBasketProduct(existingBasketProduct, target, processedCount)
    }

    private fun processTargetBasketProduct(
        existingBasketProduct: BasketProduct,
        target: BasketProduct,
        processedCount: Count
    ): BasketProduct = if (existingBasketProduct == target) BasketProduct(
        id = existingBasketProduct.id,
        count = processedCount,
        product = existingBasketProduct.product
    ) else existingBasketProduct

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
        products.find { it.product.id == basketProduct.product.id }?.isChecked =
            basketProduct.isChecked
    }

    fun getCheckedProductsCount(): Int =
        products.filter { it.isChecked }.size

    fun toggleAllCheck(isChecked: Boolean) {
        products.map { it.isChecked = isChecked }
    }

    fun allItemIsChecked(): Boolean =
        products.none { !it.isChecked }
}
