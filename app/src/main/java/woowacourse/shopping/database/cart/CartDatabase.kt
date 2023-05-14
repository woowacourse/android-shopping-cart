package woowacourse.shopping.database.cart

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import woowacourse.shopping.database.ShoppingDBHelper
import woowacourse.shopping.model.CartProduct
import woowacourse.shopping.model.CartProducts
import woowacourse.shopping.repository.CartRepository

class CartDatabase(context: Context) : CartRepository {
    private val db = ShoppingDBHelper(context).writableDatabase

    private var cartProducts: CartProducts = getAll()

    private fun getAll(): CartProducts {
        val cartProducts = mutableListOf<CartProduct>()
        getCartCursor().use {
            while (it.moveToNext()) {
                cartProducts.add(getCartProduct(it))
            }
        }
        return CartProducts(cartProducts)
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

    private fun getCartCursor(): Cursor {
        return db.rawQuery(CartConstant.getGetAllQuery(), null)
    }

    override fun getPage(index: Int, size: Int): CartProducts {
        return cartProducts.subList(index * size, size)
    }

    override fun hasNextPage(index: Int, size: Int): Boolean {
        return cartProducts.hasNextPage(index, size)
    }

    override fun hasPrevPage(index: Int, size: Int): Boolean {
        return cartProducts.hasPrevPage(index, size)
    }

    override fun getTotalCount(): Int {
        return cartProducts.size
    }

    override fun insert(productId: Int) {
        val product = getProductById(productId)
        db.execSQL(CartConstant.getInsertQuery(product))
        cartProducts = getAll()
    }

    override fun remove(id: Int) {
        db.execSQL(CartConstant.getDeleteQuery(id))
        cartProducts = getAll()
    }
}
