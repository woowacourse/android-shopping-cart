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

    override fun getAll(): CartProducts {
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
        CartConstant.fromCursor(cursor).let {
            return CartProduct(
                id = it.id,
                name = it.name,
                count = it.count,
                checked = it.checked,
                price = it.price,
                imageUrl = it.imageUrl
            )
        }
    }

    private fun getProductById(id: Int): CartProduct {
        val query = ProductConstant.getGetQuery(id)
        db.rawQuery(query, null).use { cursor ->
            cursor.moveToNext()
            return ProductConstant.fromCursor(cursor).let {
                CartProduct(
                    id = it.id,
                    name = it.name,
                    count = 1,
                    checked = true,
                    price = it.price,
                    imageUrl = it.imageUrl
                )
            }
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

    override fun getTotalSelectedCount(): Int {
        return cartProducts.all().count { it.checked }
    }

    override fun getTotalPrice(): Int {
        return cartProducts.all().filter { it.checked }.sumOf { it.price * it.count }
    }

    override fun insert(productId: Int) {
        val product = getProductById(productId)
        db.execSQL(CartConstant.getInsertQuery(product))
        cartProducts = getAll()
    }

    override fun updateCount(id: Int, count: Int): Int {
        db.execSQL(CartConstant.getUpdateCountQuery(id, count)).let {
            cartProducts = getAll()
            return count
        }
    }

    override fun updateSelected(id: Int, selected: Boolean) {
        db.execSQL(CartConstant.getUpdateSelectedQuery(id, selected))
        cartProducts = getAll()
    }

    override fun remove(id: Int) {
        db.execSQL(CartConstant.getDeleteQuery(id))
        cartProducts = getAll()
    }
}
