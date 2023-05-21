package woowacourse.shopping.data.datasource.cart

import woowacourse.shopping.data.model.DataCart
import woowacourse.shopping.data.model.DataPageNumber
import woowacourse.shopping.data.model.Product

interface CartDataSource {
    interface Local {
        fun getProductByPage(page: DataPageNumber): DataCart
        fun getProductInCartByPage(page: DataPageNumber): DataCart
        fun increaseCartCount(product: Product, count: Int)
        fun decreaseCartCount(product: Product, count: Int)
        fun deleteByProductId(productId: Int)
        fun getProductInCartSize(): Int
        fun update(cart: DataCart)
        fun getTotalPrice(): Int
        fun getCheckedProductCount(): Int
        fun getProductInRange(start: DataPageNumber, end: DataPageNumber): DataCart
        fun removeCheckedProducts()
    }

    interface Remote
}
