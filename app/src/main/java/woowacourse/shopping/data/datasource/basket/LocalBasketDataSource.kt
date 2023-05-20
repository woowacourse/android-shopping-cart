package woowacourse.shopping.data.datasource.basket

import woowacourse.shopping.data.database.dao.basket.BasketDao
import woowacourse.shopping.data.model.DataBasket
import woowacourse.shopping.data.model.DataPageNumber
import woowacourse.shopping.data.model.Product

class LocalBasketDataSource(private val dao: BasketDao) : BasketDataSource.Local {
    override fun getProductByPage(page: DataPageNumber): DataBasket =
        dao.getProductByPage(page)

    override fun getProductInBasketByPage(page: DataPageNumber): DataBasket =
        dao.getProductInBasketByPage(page)

    override fun plusProductCount(product: Product, count: Int) {
        dao.addProductCount(product, count)
    }

    override fun getProductInBasketSize(): Int = dao.getProductInBasketSize()

    override fun update(basket: DataBasket) {
        basket.basketProducts.forEach(dao::update)
    }

    override fun getTotalPrice(): Int = dao.getTotalPrice()

    override fun getCheckedProductCount(): Int = dao.getCheckedProductCount()

    override fun minusProductCount(product: Product) {
        val productCount = dao.count(product)
        when {
            !dao.contains(product) -> return
            productCount > 1 -> dao.minusProductCount(product, 1)
            else -> deleteByProductId(product.id)
        }
    }

    override fun deleteByProductId(productId: Int) {
        dao.deleteByProductId(productId)
    }
}
