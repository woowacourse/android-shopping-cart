package woowacourse.shopping.data.datasource.cart

import woowacourse.shopping.data.database.dao.cart.CartDao
import woowacourse.shopping.data.model.DataCart
import woowacourse.shopping.data.model.DataCartProduct
import woowacourse.shopping.data.model.DataPage
import woowacourse.shopping.data.model.Product

class LocalCartDataSource(private val dao: CartDao) : CartDataSource.Local {
    override fun getProductByPage(page: DataPage): DataCart =
        dao.getProductByPage(page)

    override fun getProductInCartByPage(page: DataPage): DataCart =
        dao.getProductInCartByPage(page)

    override fun increaseCartCount(product: Product, count: Int) {
        dao.addProductCount(product, count)
    }

    override fun getProductInCartSize(): Int = dao.getProductInCartSize()

    override fun update(cartProducts: List<DataCartProduct>) {
        cartProducts.forEach(dao::update)
    }

    override fun getTotalPrice(): Int = dao.getTotalPrice()

    override fun getCheckedProductCount(): Int = dao.getCheckedProductCount()

    override fun getProductInRange(start: DataPage, end: DataPage): DataCart {
        return dao.getProductInRange(start, end)
    }

    override fun removeCheckedProducts() {
        dao.deleteCheckedProducts()
    }

    override fun decreaseCartCount(product: Product, count: Int) {
        val productCount = dao.count(product)
        when {
            !dao.contains(product) -> return
            productCount > count -> dao.minusProductCount(product, count)
            else -> deleteByProductId(product.id)
        }
    }

    override fun deleteByProductId(productId: Int) {
        dao.deleteByProductId(productId)
    }
}
