package woowacourse.shopping.data

import com.example.domain.model.CartProduct
import com.example.domain.model.Product
import com.example.domain.repository.CartRepository
import woowacourse.shopping.data.sql.cart.CartDao

class CartRepositoryImpl(
    private val cartDao: CartDao
) : CartRepository {
    override fun getAll(): List<CartProduct> {
        return cartDao.selectAll()
    }

    override fun getAllCartProductCategorySize(): Int {
        return cartDao.selectAll().size
    }

    override fun getAllCountSize(): Int {
        return cartDao.selectAll().sumOf { it.count }
    }

    override fun addProduct(product: Product) {
        cartDao.insertProduct(product)
    }

    override fun deleteProduct(cartProduct: CartProduct) {
        cartDao.deleteCartProduct(cartProduct)
    }

    override fun deleteAllCheckedCartProduct() {
        cartDao.deleteAllCheckedCartProduct()
    }

    override fun changeCartProductCount(product: Product, count: Int) {
        cartDao.updateCartProductCount(product, count)
    }

    override fun changeCartProductCheckedState(product: Product, checked: Boolean) {
        cartDao.updateCartProductChecked(product, checked)
    }

    override fun changeAllCheckedState(checked: Boolean) {
        cartDao.updateAllChecked(checked)
    }

    override fun getInitPageProducts(pageSize: Int): List<CartProduct> {
        return cartDao.selectAll().asSequence().take(pageSize).toList()
    }

    override fun getPreviousProducts(pageSize: Int, nextId: Long?): List<CartProduct> {
        val all = cartDao.selectAll()
        val lower = all.filter { it.cartId < (nextId ?: 0) }.takeLast(pageSize)
        if (lower.size != pageSize) return all.take(pageSize)
        return lower
    }

    override fun getNextProducts(pageSize: Int, previousId: Long?): List<CartProduct> {
        return cartDao.selectAll().asSequence().filter { it.cartId > (previousId ?: 0) }
            .take(pageSize)
            .toList()
    }

    override fun getPageCartProductsFromFirstId(pageSize: Int, firstId: Long?): List<CartProduct> {
        return cartDao.selectAll().asSequence().filter { it.cartId >= (firstId ?: 0) }
            .take(pageSize).toList()
    }

    override fun getCartProductsFromPage(pageSize: Int, page: Int): List<CartProduct> {
        val all = cartDao.selectAll()
        val firstIndexInPage = pageSize * (page - 1)
        if (all.size > firstIndexInPage) {
            return all.subList(firstIndexInPage, all.size).take(pageSize)
        }
        return emptyList()
    }
}
