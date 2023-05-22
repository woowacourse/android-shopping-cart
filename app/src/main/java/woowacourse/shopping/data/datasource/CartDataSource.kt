package woowacourse.shopping.data.datasource

import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Shop

interface CartDataSource {
    fun addCartProduct(cartProduct: CartProduct)
    fun plusCartProduct(product: Product)
    fun minusCartProduct(product: Product)
    fun deleteCartProduct(product: Product)
    fun selectAllCount(): Int
    fun selectAll(products: List<Product>): Shop
}
