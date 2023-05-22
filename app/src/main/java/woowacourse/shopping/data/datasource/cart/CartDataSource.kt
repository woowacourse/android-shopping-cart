package woowacourse.shopping.data.datasource.cart

import woowacourse.shopping.data.entity.CartEntity
import woowacourse.shopping.data.model.DataCart
import woowacourse.shopping.data.model.DataCartProduct
import woowacourse.shopping.data.model.DataPage
import woowacourse.shopping.data.model.Product

interface CartDataSource {
    interface Local {
        fun getAllCartEntity(): List<CartEntity>
        fun getCartEntity(productId: Int): CartEntity
        fun getProductByPage(page: DataPage): DataCart
        fun getProductInCartByPage(page: DataPage): List<CartEntity>
        fun increaseCartCount(product: Product, count: Int)
        fun decreaseCartCount(product: Product, count: Int)
        fun deleteByProductId(productId: Int)
        fun getProductInCartSize(): Int
        fun update(cartProducts: List<DataCartProduct>)
        fun getTotalPrice(): Int
        fun getCheckedProductCount(): Int
        fun getProductInRange(start: DataPage, end: DataPage): DataCart
        fun removeCheckedProducts()
    }

    interface Remote
}
