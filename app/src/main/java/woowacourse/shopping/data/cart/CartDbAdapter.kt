package woowacourse.shopping.data.cart

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.lang.String.valueOf

class CartDbAdapter(db: CartDbHelper) : CartRepository {

    private val writableDb: SQLiteDatabase = db.writableDatabase
    override fun addCartProductId(productId: Int) {
        val values = ContentValues().apply {
            put(CartDbContract.PRODUCT_ID, productId)
            put(CartDbContract.TIMESTAMP, System.currentTimeMillis())
        }

        writableDb.insert(CartDbContract.TABLE_NAME, null, values)
    }

    override fun deleteCartProductId(productId: Int) {
        writableDb.delete(
            CartDbContract.TABLE_NAME,
            "${CartDbContract.PRODUCT_ID}=?",
            arrayOf<String>(valueOf(productId)),
        )
    }

    override fun getCartProductIds(): List<Int> {
        val productIds = mutableListOf<Int>()

        val cursor = writableDb.query(
            CartDbContract.TABLE_NAME,
            arrayOf(
                CartDbContract.PRODUCT_ID,
                CartDbContract.TIMESTAMP,
            ),
            null,
            null,
            null,
            null,
            "${CartDbContract.TIMESTAMP} DESC",
            null,
        )
        while (cursor.moveToNext()) {
            val productId = cursor.getCartProductId()
            productIds.add(productId)
        }

        cursor.close()

        return productIds
    }

    private fun Cursor.getCartProductId(): Int =
        getInt(getColumnIndexOrThrow(CartDbContract.PRODUCT_ID))
}
