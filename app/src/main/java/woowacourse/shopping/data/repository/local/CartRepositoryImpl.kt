package woowacourse.shopping.data.repository.local

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

    override fun changeCurrentPageAllCheckedState(cartIds: List<Long>, checked: Boolean) {
        cartDao.updateAllChecked(cartIds, checked)
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
