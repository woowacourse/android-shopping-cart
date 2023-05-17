package woowacourse.shopping.data.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import woowacourse.shopping.data.model.CartProductEntity

class CartDao(context: Context) {
    private val db = CartHelper(context).writableDatabase

    fun insertProduct(productId: Long) {
        val value = ContentValues().apply {
            put(CartContract.Cart.PRODUCT_ID, productId)
        }
        db.insert(CartContract.Cart.TABLE_NAME, null, value)
    }

    fun deleteAllProduct(productId: Long) {
        db.delete(
            CartContract.Cart.TABLE_NAME,
            "${CartContract.Cart.PRODUCT_ID} = ?",
            arrayOf(productId.toString())
        )
    }

    fun getItems(startPosition: Int, cartItemCount: Int): List<CartProductEntity> {
        val result = mutableListOf<CartProductEntity>()
        val cursor = getCursor(startPosition, cartItemCount)
        with(cursor) {
            while (moveToNext()) {
                val productId =
                    getLong(getColumnIndexOrThrow(CartContract.Cart.PRODUCT_ID))
                val productCount =
                    getInt(getColumnIndexOrThrow(CartContract.Cart.PRODUCT_COUNT))
                result.add(CartProductEntity(productId, productCount))
            }
        }
        cursor.close()

        return result.toList()
    }

    private fun getCursor(startPosition: Int, cartItemCount: Int): Cursor {
        return db.rawQuery(CartContract.getCartSql(startPosition, cartItemCount), null)
    }

    companion object
}

// https://hongal.tistory.com/50