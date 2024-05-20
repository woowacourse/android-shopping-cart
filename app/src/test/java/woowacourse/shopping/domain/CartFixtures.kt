package woowacourse.shopping.domain

fun cartProduct(
    product: Product = product(),
    count: Int = 1,
) = CartProduct(product, count)
