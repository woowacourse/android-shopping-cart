package woowacourse.shopping.data.cart

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class CartDbDao(db: CartDbHelper) : CartDataSource {

    private val writableDb: SQLiteDatabase = db.writableDatabase
    override fun insertCartItem(productId: Long) {
        val values = ContentValues().apply {
            put(CartDbContract.PRODUCT_ID, productId)
            put(CartDbContract.TIMESTAMP, System.currentTimeMillis())
            put(CartDbContract.PRODUCT_COUNT, 1)
        }
        writableDb.insert(CartDbContract.TABLE_NAME, null, values)
    }

    override fun getAllCartItems(): List<CartItem> {
        val cartItems = mutableListOf<CartItem>()

        val cursor = writableDb.query(
            CartDbContract.TABLE_NAME,
            arrayOf(
                CartDbContract.CART_ID,
                CartDbContract.PRODUCT_ID,
                CartDbContract.TIMESTAMP,
                CartDbContract.PRODUCT_COUNT,
            ),
            null,
            null,
            null,
            null,
            "${CartDbContract.TIMESTAMP} DESC",
            null,
        )
        while (cursor.moveToNext()) {
            val cartInfo = cursor.getCartItem()

            cartItems.add(cartInfo)
        }

        cursor.close()

        return cartItems
    }

    override fun getCartItem(productId: Long): CartItem {
        val selection = "${CartDbContract.PRODUCT_ID} = ?"
        val selectionArgs = arrayOf(productId.toString())
        val cursor = writableDb.query(
            CartDbContract.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null,
        )

        return if (cursor.moveToFirst()) {
            cursor.getCartItem()
        } else {
            CartItem(0, 0, productId)
        }
    }

    override fun updateCartItem(cartId: Long, quantity: Int) {
        val values = ContentValues().apply {
            put(CartDbContract.PRODUCT_COUNT, quantity)
        }

        if (quantity <= 0) {
            deleteCartItem(cartId)
            return
        }

        val whereClause = "${CartDbContract.CART_ID} = $cartId"
        writableDb.update(CartDbContract.TABLE_NAME, values, whereClause, null)
    }

    override fun deleteCartItem(cartId: Long) {
        writableDb.delete(
            CartDbContract.TABLE_NAME,
            "${CartDbContract.CART_ID}=?",
            arrayOf(cartId.toString()),
        )
    }

    private fun Cursor.getCartItem(): CartItem =
        CartItem(
            id = getLong(getColumnIndexOrThrow(CartDbContract.CART_ID)),
            quantity = getInt(getColumnIndexOrThrow(CartDbContract.PRODUCT_COUNT)),
            productId = getLong(getColumnIndexOrThrow(CartDbContract.PRODUCT_ID)),
        )
}
