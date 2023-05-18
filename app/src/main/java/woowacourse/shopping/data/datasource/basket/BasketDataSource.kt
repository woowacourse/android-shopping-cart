package woowacourse.shopping.data.datasource.basket

import woowacourse.shopping.data.model.DataBasket
import woowacourse.shopping.data.model.DataPageNumber
import woowacourse.shopping.data.model.Product

interface BasketDataSource {
    interface Local {
        fun getProductByPage(page: DataPageNumber): DataBasket
        fun getProductInBasketByPage(page: DataPageNumber): DataBasket
        fun plusProductCount(product: Product)
        fun minusProductCount(product: Product)
        fun deleteByProductId(productId: Int)
    }

    interface Remote
}
