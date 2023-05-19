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
}
