package woowacourse.shopping.data.recentproduct

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.domain.Product
import com.example.domain.RecentProducts

class RecentProductDbHandler(
    private val db: SQLiteDatabase
) {

    private fun getCursor(): Cursor {
        return db.query(
            RecentProductContract.TABLE_NAME,
            arrayOf(
                RecentProductContract.TABLE_COLUMN_ID,
                RecentProductContract.TABLE_COLUMN_IMAGE_URL,
                RecentProductContract.TABLE_COLUMN_NAME,
                RecentProductContract.TABLE_COLUMN_PRICE
            ),
            "", arrayOf(), null, null, ""
        )
    }

    fun getRecentProducts(): RecentProducts {
        val cursor = getCursor()
        val recentProducts = RecentProducts()

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_ID))
                val imageUrl = getString(getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_IMAGE_URL))
                val name = getString(getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_NAME))
                val price = getInt(getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_PRICE))

                recentProducts.addProduct(
                    Product(id, imageUrl, name, price)
                )
            }
        }

        cursor.close()
        return recentProducts
    }

    fun addColumn(product: Product) {
        val values = ContentValues().apply {
            put(RecentProductContract.TABLE_COLUMN_ID, product.id)
            put(RecentProductContract.TABLE_COLUMN_IMAGE_URL, product.imageUrl)
            put(RecentProductContract.TABLE_COLUMN_NAME, product.name)
            put(RecentProductContract.TABLE_COLUMN_PRICE, product.price)
        }

        db.insert(RecentProductContract.TABLE_NAME, null, values)
    }

    fun deleteColumn(product: Product) {
        db.delete(
            RecentProductContract.TABLE_NAME,
            RecentProductContract.TABLE_COLUMN_ID + "=" + product.id, null
        )
    }
}
