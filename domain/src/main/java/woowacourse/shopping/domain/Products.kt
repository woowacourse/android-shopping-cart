package woowacourse.shopping.domain

class Products(val value: List<ShoppingProduct>) {
    operator fun plus(products: Products): Products {
        return Products(value + products.value)
    }
}
