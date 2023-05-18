package woowacourse.shopping.data.cart

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class CartDbAdapter(db: CartDbHelper) : CartRepository {

    private val writableDb: SQLiteDatabase = db.writableDatabase
    override fun insertCartProduct(productId: Int, count: Int) {
        val existingRecordCount = getCartProductCount(productId)

        if (existingRecordCount == 0) {
            val values = ContentValues().apply {
                put(CartDbContract.PRODUCT_ID, productId)
                put(CartDbContract.TIMESTAMP, System.currentTimeMillis())
                put(CartDbContract.PRODUCT_COUNT, count)
            }

            writableDb.insert(CartDbContract.TABLE_NAME, null, values)
        } else {
            val updatedCount = existingRecordCount + count
            updateCartProductCount(productId, updatedCount)
        }
    }

    override fun getCartEntity(productId: Int): CartEntity {
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
            val count = cursor.getInt(cursor.getColumnIndexOrThrow(CartDbContract.PRODUCT_COUNT))
            CartEntity(productId, count)
        } else {
            CartEntity(productId, 0)
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

    override fun updateCartProductCount(productId: Int, count: Int) {
        val values = ContentValues().apply {
            put(CartDbContract.PRODUCT_COUNT, count)
        }

        if (count <= 0) {
            deleteCartProduct(productId)
            return
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

    override fun getCartEntities(): List<CartEntity> {
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
}
