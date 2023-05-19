package woowacourse.shopping.database.cart

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import woowacourse.shopping.database.ShoppingDBHelper
import woowacourse.shopping.model.CartProduct
import woowacourse.shopping.model.CartProducts
import woowacourse.shopping.model.Product
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

    private fun toCartProduct(product: Product): CartProduct {
        return CartProduct(
            id = product.id,
            name = product.name,
            count = 1,
            checked = true,
            price = product.price,
            imageUrl = product.imageUrl
        )
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
        return cartProducts.all().filter { it.checked }.sumOf { it.count }
    }

    override fun getTotalPrice(): Int {
        return cartProducts.all().filter { it.checked }.sumOf { it.price * it.count }
    }

    override fun insert(product: Product) {
        db.execSQL(CartConstant.getInsertQuery(toCartProduct(product)))
        cartProducts = getAll()
    }

    override fun updateCount(id: Int, count: Int): Int {
        val sql = when {
            count < 1 -> CartConstant.getDeleteQuery(id)
            else -> CartConstant.getUpdateCountQuery(id, count)
        }
        db.execSQL(sql).let {
            cartProducts = getAll()
            return count
        }
    }

    override fun updateChecked(id: Int, checked: Boolean) {
        db.execSQL(CartConstant.getUpdateCheckedQuery(id, checked))
        cartProducts = getAll()
    }

    override fun remove(id: Int) {
        db.execSQL(CartConstant.getDeleteQuery(id))
        cartProducts = getAll()
    }
}
