package woowacourse.shopping.database.cart

import com.domain.model.CartProduct
import com.domain.model.CartRepository
import com.domain.model.Product

class CartRepositoryImpl(
    private val cartDao: CartDao,
) : CartRepository {

    private var cartProduct: List<CartProduct> = listOf()

    override fun getAll(): List<CartProduct> {
        cartProduct = cartDao.getAll()
        return cartProduct.map { it.copy() }
    }

    override fun insert(product: Product, count: Int) {
        val cartProduct = this.cartProduct.toMutableList()
        cartProduct.add(CartProduct(product, count, false))
        this.cartProduct = cartProduct.map { it.copy() }.toList()
        cartDao.insert(product, count)
    }

    override fun getSubList(offset: Int, size: Int): List<CartProduct> {
        val lastIndex = cartProduct.lastIndex
        val endIndex = (lastIndex + 1).coerceAtMost(offset + size)
        return if (offset <= lastIndex) cartProduct.subList(offset, endIndex) else emptyList()
    }

    override fun remove(id: Int) {
        val cartProduct = this.cartProduct.toMutableList()
        cartProduct.removeIf { it.product.id == id }
        this.cartProduct = cartProduct.map { it.copy() }.toList()
        cartDao.remove(id)
    }

    override fun updateCount(id: Int, count: Int) {
        val cartProduct = this.cartProduct.toMutableList()
        val product: CartProduct? = cartProduct.find { it.product.id == id }
        product?.count = count
        this.cartProduct = cartProduct.map { it.copy() }.toList()
        cartDao.updateCount(id, count)
    }

    override fun updateChecked(id: Int, checked: Boolean) {
        val cartProduct = this.cartProduct.toMutableList()
        val product: CartProduct? = cartProduct.find { it.product.id == id }
        product?.isChecked = checked
        this.cartProduct = cartProduct.map { it.copy() }.toList()
        cartDao.updateChecked(id, checked)
    }

    override fun getChecked(): List<CartProduct> {
        return cartProduct.filter { it.isChecked }
    }

    override fun findById(id: Int): CartProduct? {
        return cartProduct.find { it.product.id == id }
    }
}
