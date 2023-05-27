package woowacourse.shopping.data.respository.cart

import woowacourse.shopping.data.local.database.CartDao
import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.data.respository.product.ProductsDao
import woowacourse.shopping.presentation.model.CartProductModel

class CartRepositoryImpl(private val cartDao: CartDao) : CartRepository {
    override fun getCarts(startPosition: Int, cartItemCount: Int): List<CartProductModel> {
        return cartDao.getItems(startPosition, cartItemCount).map { it.toUIModel() }
    }

    override fun updateCartSelected(productId: Long, isSelected: Boolean) {
        cartDao.updateCartSelected(
            productId,
            if (isSelected) CartDao.IS_SELECTED_FLAG else CartDao.IS_NOT_SELECTED_FLAG
        )
    }

    override fun updateCartsSelected(productsId: List<Long>, isSelected: Boolean) {
        productsId.forEach {
            cartDao.updateCartSelected(
                it,
                if (isSelected) CartDao.IS_SELECTED_FLAG else CartDao.IS_NOT_SELECTED_FLAG
            )
        }
    }

    override fun deleteCartByProductId(productId: Long) {
        cartDao.deleteAllProduct(productId)
    }

    override fun insertCart(productId: Long, productCount: Int) {
        cartDao.insertProduct(productId, productCount)
    }

    override fun getTotalPrice(): Int {
        return cartDao.getSelectedData().sumOf {
            (ProductsDao.getDataById(it.productId) ?: ProductsDao.getErrorData())
                .price * it.count
        }
    }
}
