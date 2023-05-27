package woowacourse.shopping.data.recentproduct

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.lang.String.valueOf

class RecentProductIdDbAdapter(db: RecentProductDbHelper) : RecentProductIdRepository {

    private val writableDb: SQLiteDatabase = db.writableDatabase

    override fun getRecentProductIds(size: Int): List<Long> {
        val productIds = mutableListOf<Long>()

        val cursor = writableDb.query(
            RecentProductDbContract.TABLE_NAME,
            arrayOf(
                RecentProductDbContract.PRODUCT_ID,
                RecentProductDbContract.TIMESTAMP,
            ),
            null,
            null,
            null,
            null,
            "${RecentProductDbContract.TIMESTAMP} DESC",
            null,
        )
        while (cursor.moveToNext() && (productIds.size < size)) {
            val productId = cursor.getRecentProductId()
            productIds.add(productId)
        }

        cursor.close()

        return productIds
    }

    private fun Cursor.getRecentProductId(): Long =
        getLong(getColumnIndexOrThrow(RecentProductDbContract.PRODUCT_ID))

    override fun addRecentProductId(recentProductId: Long) {
        val values = ContentValues().apply {
            put(RecentProductDbContract.PRODUCT_ID, recentProductId)
            put(RecentProductDbContract.TIMESTAMP, System.currentTimeMillis())
        }

        writableDb.insert(RecentProductDbContract.TABLE_NAME, null, values)
    }

    override fun deleteRecentProductId(recentProductId: Long) {
        writableDb.delete(
            RecentProductDbContract.TABLE_NAME,
            "${RecentProductDbContract.PRODUCT_ID}=?",
            arrayOf<String>(valueOf(recentProductId)),
        )
    }
}
