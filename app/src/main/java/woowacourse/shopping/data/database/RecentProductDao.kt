package woowacourse.shopping.data.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import woowacourse.shopping.data.model.RecentProductEntity
import java.time.LocalDateTime

class RecentProductDao(context: Context) {
    private val db = RecentProductHelper(context).writableDatabase

    fun insertRecentProduct(productId: Long) {
        val value = ContentValues().apply {
            put(RecentProductContract.RecentProduct.PRODUCT_ID, productId)
            put(RecentProductContract.RecentProduct.CREATE_DATE, LocalDateTime.now().toString())
        }
        db.insert(RecentProductContract.RecentProduct.TABLE_NAME, null, value)
    }

    fun deleteNotToday(today: String) {
        val sql =
            "DELETE FROM ${RecentProductContract.RecentProduct.TABLE_NAME} WHERE ${RecentProductContract.RecentProduct.CREATE_DATE} NOT LIKE '$today%'"

        db.execSQL(sql)
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
            RecentProductContract.RecentProduct.CREATE_DATE + " DESC",
            "20"
        )
    }
}
