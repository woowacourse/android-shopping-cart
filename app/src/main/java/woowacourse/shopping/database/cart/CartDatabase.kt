package woowacourse.shopping.database.cart

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import woowacourse.shopping.database.ShoppingDBHelper
import woowacourse.shopping.model.CartProduct
import woowacourse.shopping.repository.CartRepository

class CartDatabase(context: Context) : CartRepository {
    private val db = ShoppingDBHelper(context).writableDatabase

    override fun getAll(): List<CartProduct> {
        val cartProducts = mutableListOf<CartProduct>()
        getCartCursor().use {
            while (it.moveToNext()) {
                cartProducts.add(getCartProduct(it))
            }
        }
        return cartProducts
    }

    @SuppressLint("Range")
    private fun getCartProduct(cursor: Cursor): CartProduct {
        return CartConstant.fromCursor(cursor)
    }

    private fun getProductById(id: Int): CartProduct {
        val query = ProductConstant.getGetQuery(id)
        db.rawQuery(query, null).use {
            it.moveToNext()
            return getCartProduct(it)
        }
    }

    override fun insert(productId: Int) {
        val product = getProductById(productId)
        db.execSQL(CartConstant.getInsertQuery(product))
    }

    override fun getSubList(offset: Int, size: Int): List<CartProduct> {
        val lastIndex = getAll().lastIndex
        val endIndex = (lastIndex + 1).coerceAtMost(offset + size)
        return if (offset <= lastIndex) getAll().subList(offset, endIndex) else emptyList()
    }

    override fun remove(id: Int) {
        db.execSQL(CartConstant.getDeleteQuery(id))
    }

    private fun getCartCursor(): Cursor {
        return db.rawQuery(CartConstant.getGetAllQuery(), null)
    }
}
