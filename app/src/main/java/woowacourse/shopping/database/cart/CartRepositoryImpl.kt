package woowacourse.shopping.database.cart

import com.domain.model.CartProduct
import com.domain.model.CartRepository
import com.domain.model.Product

class CartRepositoryImpl(
    private val cartDao: CartDao,
) : CartRepository {
    override fun getAll(): List<CartProduct> {
        return cartDao.getAll()
    }

    override fun insert(product: Product, count: Int) {
        cartDao.insert(product, count)
    }

    override fun getSubList(offset: Int, size: Int): List<CartProduct> {
        val lastIndex = getAll().lastIndex
        val endIndex = (lastIndex + 1).coerceAtMost(offset + size)
        return if (offset <= lastIndex) getAll().subList(offset, endIndex) else emptyList()
    }

    override fun remove(id: Int) {
        cartDao.remove(id)
    }

    override fun updateCount(id: Int, count: Int) {
        cartDao.updateCount(id, count)
    }

    override fun updateChecked(id: Int, checked: Boolean) {
        cartDao.updateChecked(id, checked)
    }

    override fun getChecked(): List<CartProduct> {
        return cartDao.getChecked()
    }

    override fun findById(id: Int): CartProduct? {
        return cartDao.findById(id)
    }
}
