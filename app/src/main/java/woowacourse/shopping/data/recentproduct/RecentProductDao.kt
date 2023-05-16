package woowacourse.shopping.data.recentproduct

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.domain.RecentProduct

class RecentProductDao(context: Context) {

    private val db: SQLiteDatabase = RecentProductDbHelper(context).writableDatabase

    private fun getCursor(): Cursor {
        return db.query(
            RecentProductContract.TABLE_NAME,
            arrayOf(
                RecentProductContract.TABLE_COLUMN_PRODUCT_ID,
                RecentProductContract.TABLE_COLUMN_PRODUCT_IMAGE_URL,
                RecentProductContract.TABLE_COLUMN_PRODUCT_NAME
            ),
            "", arrayOf(), null, null, ""
        )
    }

    fun getAll(): List<RecentProduct> {
        val cursor: Cursor = getCursor()
        val recentProducts: MutableList<RecentProduct> = mutableListOf()

        with(cursor) {
            while (moveToNext()) {
                val id =
                    getInt(getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_PRODUCT_ID))
                val imageUrl =
                    getString(getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_PRODUCT_IMAGE_URL))
                val name =
                    getString(getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_PRODUCT_NAME))

                recentProducts.add(RecentProduct(id, imageUrl, name))
            }
        }

        cursor.close()
        return recentProducts
    }

    fun addColumn(recentProduct: RecentProduct) {
        val values = ContentValues().apply {
            put(RecentProductContract.TABLE_COLUMN_PRODUCT_ID, recentProduct.productId)
            put(RecentProductContract.TABLE_COLUMN_PRODUCT_IMAGE_URL, recentProduct.productImageUrl)
            put(RecentProductContract.TABLE_COLUMN_PRODUCT_NAME, recentProduct.productName)
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
                    ${RecentProductContract.TABLE_COLUMN_PRODUCT_NAME} TEXT
                )
            """.trimIndent()
        )
    }
}
