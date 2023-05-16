package woowacourse.shopping.data.recentproduct

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.domain.RecentProduct
import java.time.LocalDateTime
import java.time.ZoneOffset

class RecentProductDao(context: Context) {

    private val db: SQLiteDatabase = RecentProductDbHelper(context).writableDatabase

    private fun getCursor(): Cursor {
        return db.query(
            RecentProductContract.TABLE_NAME,
            arrayOf(
                RecentProductContract.TABLE_COLUMN_PRODUCT_ID,
                RecentProductContract.TABLE_COLUMN_PRODUCT_IMAGE_URL,
                RecentProductContract.TABLE_COLUMN_PRODUCT_NAME,
                RecentProductContract.TABLE_COLUMN_VIEWED_DATE_TIME
            ),
            "", arrayOf(), null, null, ""
        )
    }

    fun getAll(): List<RecentProduct> {
        val cursor: Cursor = getCursor()
        val recentProducts: MutableList<RecentProduct> = mutableListOf()

        with(cursor) {
            while (moveToNext()) {
                val productId =
                    getInt(getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_PRODUCT_ID))
                val productImageUrl =
                    getString(getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_PRODUCT_IMAGE_URL))
                val productName =
                    getString(getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_PRODUCT_NAME))
                val viewedDateTime =
                    getLong(getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_VIEWED_DATE_TIME))

                recentProducts.add(
                    RecentProduct(
                        productId = productId,
                        productImageUrl = productImageUrl,
                        productName = productName,
                        viewedDateTime = LocalDateTime.ofEpochSecond(
                            viewedDateTime,
                            0,
                            ZoneOffset.UTC
                        )
                    )
                )
            }
        }

        cursor.close()
        return recentProducts.sortedBy { it.viewedDateTime }.reversed()
    }

    fun addColumn(recentProduct: RecentProduct) {
        val deleteQuery =
            """
                DELETE FROM ${RecentProductContract.TABLE_NAME}
                WHERE ${RecentProductContract.TABLE_COLUMN_PRODUCT_ID} = ${recentProduct.productId};
            """.trimIndent()

        db.execSQL(deleteQuery)

        val values = ContentValues().apply {
            put(RecentProductContract.TABLE_COLUMN_PRODUCT_ID, recentProduct.productId)
            put(RecentProductContract.TABLE_COLUMN_PRODUCT_IMAGE_URL, recentProduct.productImageUrl)
            put(RecentProductContract.TABLE_COLUMN_PRODUCT_NAME, recentProduct.productName)
            put(
                RecentProductContract.TABLE_COLUMN_VIEWED_DATE_TIME,
                recentProduct.viewedDateTime.toEpochSecond(ZoneOffset.UTC)
            )
        }
        db.insert(RecentProductContract.TABLE_NAME, null, values)
    }

    fun deleteColumn(productId: Int) {
        db.delete(
            RecentProductContract.TABLE_NAME,
            RecentProductContract.TABLE_COLUMN_PRODUCT_ID + "=" + productId, null
        )
    }

    fun createTable() {
        db.execSQL(
            """
                CREATE TABLE ${RecentProductContract.TABLE_NAME} (
                    ${RecentProductContract.TABLE_COLUMN_PRODUCT_ID} INTEGER,
                    ${RecentProductContract.TABLE_COLUMN_PRODUCT_IMAGE_URL} TEXT,
                    ${RecentProductContract.TABLE_COLUMN_PRODUCT_NAME} TEXT,
                    ${RecentProductContract.TABLE_COLUMN_VIEWED_DATE_TIME} INT
                )
            """.trimIndent()
        )
    }

    fun deleteTable() {
        db.execSQL(
            """
                DROP TABLE IF EXISTS ${RecentProductContract.TABLE_NAME};
            """.trimIndent()
        )
    }
}
