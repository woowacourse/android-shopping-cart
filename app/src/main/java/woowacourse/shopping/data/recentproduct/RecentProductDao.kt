package woowacourse.shopping.data.recentproduct

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.repository.RecentProductRepository
import java.lang.String.valueOf

class RecentProductDao(db: RecentProductDbHelper) : RecentProductRepository {

    private val writableDb: SQLiteDatabase = db.writableDatabase

    override fun getRecentProductIds(size: Int): List<Int> {
        val productIds = mutableListOf<Int>()

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

    private fun Cursor.getRecentProductId(): Int =
        getInt(getColumnIndexOrThrow(RecentProductDbContract.PRODUCT_ID))

    override fun addRecentProductId(recentProductId: Int) {
        val values = ContentValues().apply {
            put(RecentProductDbContract.PRODUCT_ID, recentProductId)
            put(RecentProductDbContract.TIMESTAMP, System.currentTimeMillis())
        }

        writableDb.insert(RecentProductDbContract.TABLE_NAME, null, values)
    }

    override fun deleteRecentProductId(recentProductId: Int) {
        writableDb.delete(
            RecentProductDbContract.TABLE_NAME,
            "${RecentProductDbContract.PRODUCT_ID}=?",
            arrayOf<String>(valueOf(recentProductId)),
        )
    }
}
