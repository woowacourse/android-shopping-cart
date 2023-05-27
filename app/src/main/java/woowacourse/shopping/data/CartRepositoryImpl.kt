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

    override fun addProduct(product: Product, count: Int) {
        cartDao.putProduct(product, count)
    }

    override fun deleteProduct(product: Product) {
        cartDao.deleteCartProduct(product)
    }

    override fun updateSelection(product: Product, isSelected: Boolean) {
        if (isSelected) cartDao.updateSelection(product, 1)
        else cartDao.updateSelection(product, 0)
    }

    override fun getProductsByPage(page: Int, size: Int): List<CartProduct> {
        val allProducts = getAll()
        val startIndex = (page - 1) * size
        val endIndex =
            if (startIndex + size >= allProducts.size) allProducts.size
            else startIndex + size

        return allProducts.subList(startIndex, endIndex)
    }
}
