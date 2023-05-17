package woowacourse.shopping.data.cart

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class CartDbAdapter(db: CartDbHelper) : CartRepository {

    private val writableDb: SQLiteDatabase = db.writableDatabase
    override fun addCartProduct(productId: Int) {
        val existingRecordCount = getCartProductCount(productId)

        if (existingRecordCount == 0) {
            val values = ContentValues().apply {
                put(CartDbContract.PRODUCT_ID, productId)
                put(CartDbContract.TIMESTAMP, System.currentTimeMillis())
                put(CartDbContract.PRODUCT_COUNT, PRODUCT_INIT_COUNT)
            }

            writableDb.insert(CartDbContract.TABLE_NAME, null, values)
        } else {
            val updatedCount = existingRecordCount + PRODUCT_INIT_COUNT
            updateCartProductCount(productId, updatedCount)
        }
    }

    private fun getCartProductCount(productId: Int): Int {
        val query =
            "SELECT ${CartDbContract.PRODUCT_COUNT} FROM ${CartDbContract.TABLE_NAME} WHERE ${CartDbContract.PRODUCT_ID} = $productId"
        val cursor = writableDb.rawQuery(query, null)

        var count = 0
        if (cursor.moveToFirst()) {
            val cursorProductCount = cursor.getColumnIndex(CartDbContract.PRODUCT_COUNT)
            if (cursorProductCount >= 0) {
                count += cursor.getInt(cursorProductCount)
            }
        }

        cursor.close()
        return count
    }

    private fun updateCartProductCount(productId: Int, updatedCount: Int) {
        val values = ContentValues().apply {
            put(CartDbContract.PRODUCT_COUNT, updatedCount)
        }

        val whereClause = "${CartDbContract.PRODUCT_ID} = $productId"
        writableDb.update(CartDbContract.TABLE_NAME, values, whereClause, null)
    }

    override fun deleteCartProduct(productId: Int) {
        writableDb.delete(
            CartDbContract.TABLE_NAME,
            "${CartDbContract.PRODUCT_ID}=?",
            arrayOf(productId.toString()),
        )
    }

    override fun subProductCount(productId: Int) {
        val currentCount = getCartProductCount(productId)

        if (currentCount >= 2) {
            val updatedCount = currentCount - 1
            updateCartProductCount(productId, updatedCount)
        } else if (currentCount == 1) {
            deleteCartProduct(productId)
        }
    }

    override fun getCartProducts(): List<CartEntity> {
        val cartEntities = mutableListOf<CartEntity>()

        val cursor = writableDb.query(
            CartDbContract.TABLE_NAME,
            arrayOf(
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
            val cartEntity = cursor.getCartEntity()

            cartEntities.add(cartEntity)
        }

        cursor.close()

        return cartEntities
    }

    private fun Cursor.getCartEntity(): CartEntity =
        CartEntity(
            getInt(getColumnIndexOrThrow(CartDbContract.PRODUCT_ID)),
            getInt(getColumnIndexOrThrow(CartDbContract.PRODUCT_COUNT)),
        )

    companion object {
        private const val PRODUCT_INIT_COUNT = 1
    }
}
