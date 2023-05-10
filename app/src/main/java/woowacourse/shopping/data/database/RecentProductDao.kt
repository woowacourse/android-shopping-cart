package woowacourse.shopping.data.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import woowacourse.shopping.data.model.RecentProductEntity

class RecentProductDao(context: Context) {
    private val db = RecentProductHelper(context).writableDatabase

    fun insertRecentProduct(productId: Long) {
        val value = ContentValues().apply {
            put(RecentProductContract.RecentProduct.PRODUCT_ID, productId)
        }
        db.insert(RecentProductContract.RecentProduct.TABLE_NAME, null, value)
    }

    fun deleteAll() {
        db.delete(
            CartContract.Cart.TABLE_NAME,
            "",
            arrayOf()
        )
    }

    fun getAll(): List<RecentProductEntity> {
        val result = mutableListOf<RecentProductEntity>()
        val cursor = getCursor()
        with(cursor) {
            while (moveToNext()) {
                val recentProductId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val productId =
                    getLong(getColumnIndexOrThrow(RecentProductContract.RecentProduct.PRODUCT_ID))
                result.add(RecentProductEntity(recentProductId, productId))
            }
        }
        cursor.close()

        return result.toList()
    }

    private fun getCursor(): Cursor {
        return db.query(
            RecentProductContract.RecentProduct.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
    }
}
