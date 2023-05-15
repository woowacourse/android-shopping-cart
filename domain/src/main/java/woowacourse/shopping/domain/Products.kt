package woowacourse.shopping.domain

class Products(val value: List<Product>) {
    operator fun plus(products: Products): Products {
        return Products(value + products.value)
    }
}
