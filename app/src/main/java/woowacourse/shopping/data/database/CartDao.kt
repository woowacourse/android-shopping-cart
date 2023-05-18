package woowacourse.shopping.data.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import woowacourse.shopping.data.model.CartEntity

class CartDao(context: Context) {
    private val db = CartHelper(context).writableDatabase
    fun insertProduct(productId: Long, count: Int) {
        if (checkToCartHasProduct(productId, count)) return

        val value = ContentValues().apply {
            put(CartContract.Cart.PRODUCT_ID, productId)
            put(CartContract.Cart.COUNT, count)
        }
        db.insert(CartContract.Cart.TABLE_NAME, null, value)
    }
    private fun checkToCartHasProduct(productId: Long, count: Int): Boolean {
        selectCart(getCursorByProductId(productId))?.let {
            updateCartByProductId(productId, count + it.count)
            return true
        }
        return false
    }

    fun deleteAllProduct(productId: Long) {
        db.delete(
            CartContract.Cart.TABLE_NAME,
            "${CartContract.Cart.PRODUCT_ID} = ?",
            arrayOf(productId.toString())
        )
    }

    fun deleteProduct(productId: Long) {
        db.delete(
            CartContract.Cart.TABLE_NAME,
            "${CartContract.Cart.PRODUCT_ID} = ?",
            arrayOf(productId.toString())
        )
    }

    fun deleteCart(cartId: Long) {
        db.delete(
            CartContract.Cart.TABLE_NAME,
            "${BaseColumns._ID} = ?",
            arrayOf(cartId.toString())
        )
    }

    fun getAllItems(): List<CartEntity> {
        return getItems(getCursorAll())
    }

    fun getItemsFromStartPositionToTen(startPosition: Int): List<CartEntity> {
        return getItems(getCursorFromStartPositionToTen(startPosition))
    }

    private fun getItems(cursor: Cursor): List<CartEntity> {
        val result = mutableListOf<CartEntity>()
        with(cursor) {
            while (moveToNext()) {
                val cartId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val productId =
                    getLong(getColumnIndexOrThrow(CartContract.Cart.PRODUCT_ID))
                val count = getInt(getColumnIndexOrThrow(CartContract.Cart.COUNT))
                result.add(CartEntity(cartId, productId, count))
            }
        }
        cursor.close()

        return result.toList()
    }

    private fun getCursorFromStartPositionToTen(startPosition: Int): Cursor {
        return db.query(
            CartContract.Cart.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null,
            "$startPosition, $GET_CART_ITEM_COUNT"
        )
    }

    private fun getCursorAll(): Cursor =
        db.query(
            CartContract.Cart.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null,
        )

    fun updateCartByProductId(productId: Long, count: Int) {
        selectCart(getCursorByProductId(productId)) ?: insertProduct(productId, count)

        val value = ContentValues().apply {
            put(CartContract.Cart.PRODUCT_ID, productId)
            put(CartContract.Cart.COUNT, count)
        }

        db.update(
            CartContract.Cart.TABLE_NAME,
            value,
            "${CartContract.Cart.PRODUCT_ID} = ?",
            arrayOf(productId.toString())
        )
    }

    fun updateCartByCartId(cartId: Long, count: Int) {
        selectCart(getCursorByCartId(cartId))?.let {
            val value = ContentValues().apply {
                put(CartContract.Cart.PRODUCT_ID, it.productId)
                put(CartContract.Cart.COUNT, count)
            }

            db.update(
                CartContract.Cart.TABLE_NAME,
                value,
                "${BaseColumns._ID} = ?",
                arrayOf(it.id.toString())
            )
        }
    }

    private fun selectCart(cursor: Cursor): CartEntity? {
        var cart: CartEntity? = null

        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val productId = getLong(getColumnIndexOrThrow(CartContract.Cart.PRODUCT_ID))
                val count = getInt(getColumnIndexOrThrow(CartContract.Cart.COUNT))
                cart = CartEntity(id, productId, count)
            }
        }

        cursor.close()

        return cart
    }

    private fun getCursorByCartId(selectCartId: Long): Cursor =
        db.query(
            CartContract.Cart.TABLE_NAME,
            null,
            "${BaseColumns._ID} = ?",
            arrayOf(selectCartId.toString()),
            null,
            null,
            null
        )

    private fun getCursorByProductId(selectProductId: Long): Cursor =
        db.query(
            CartContract.Cart.TABLE_NAME,
            null,
            "${CartContract.Cart.PRODUCT_ID} = ?",
            arrayOf(selectProductId.toString()),
            null,
            null,
            null
        )

    companion object {
        private const val GET_CART_ITEM_COUNT = 4
    }
}
