package woowacourse.shopping.data.database.dao.product

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import woowacourse.shopping.data.database.contract.RecentProductContract
import woowacourse.shopping.data.model.DataPrice
import woowacourse.shopping.data.model.DataProduct

class RecentProductDaoImpl(private val database: SQLiteOpenHelper) : RecentProductDao {

    @SuppressLint("Range")
    override fun getAll(): List<DataProduct> {
        val products = mutableListOf<DataProduct>()
        database.writableDatabase.use { db ->
            val cursor = db.rawQuery(GET_ALL_QUERY, null)
            while (cursor.moveToNext()) {
                val id: Int = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
                val name: String =
                    cursor.getString(cursor.getColumnIndex(RecentProductContract.COLUMN_NAME))
                val price: DataPrice =
                    DataPrice(cursor.getInt(cursor.getColumnIndex(RecentProductContract.COLUMN_PRICE)))
                val imageUrl: String =
                    cursor.getString(cursor.getColumnIndex(RecentProductContract.COLUMN_IMAGE_URL))
                products.add(DataProduct(id, name, price, imageUrl))
            }
            cursor.close()
        }
        return products
    }

    @SuppressLint("Range")
    override fun getPartially(size: Int): List<DataProduct> {
        val products = mutableListOf<DataProduct>()
        database.writableDatabase.use { db ->
            val cursor = db.rawQuery(GET_PARTIALLY_QUERY, arrayOf(size.toString()))
            while (cursor.moveToNext()) {
                val id: Int = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
                val name: String =
                    cursor.getString(cursor.getColumnIndex(RecentProductContract.COLUMN_NAME))
                val price: DataPrice =
                    DataPrice(cursor.getInt(cursor.getColumnIndex(RecentProductContract.COLUMN_PRICE)))
                val imageUrl: String =
                    cursor.getString(cursor.getColumnIndex(RecentProductContract.COLUMN_IMAGE_URL))
                products.add(DataProduct(id, name, price, imageUrl))
            }
            cursor.close()
        }
        return products
    }

    override fun add(product: DataProduct) {
        val contentValues = ContentValues().apply {
            put(RecentProductContract.COLUMN_NAME, product.name)
            put(RecentProductContract.COLUMN_PRICE, product.price.value)
            put(RecentProductContract.COLUMN_IMAGE_URL, product.imageUrl)
        }

        database.writableDatabase.use { db ->
            db.insert(RecentProductContract.TABLE_NAME, null, contentValues)
        }
    }

    override fun removeLast() {
        database.writableDatabase.use { db ->
            db.rawQuery(REMOVE_LAST_QUERY, null)
        }
    }

    companion object {
        private val GET_ALL_QUERY = """
            SELECT * FROM ${RecentProductContract.TABLE_NAME} ORDER BY ${BaseColumns._ID} DESC
        """.trimIndent()

        private val GET_PARTIALLY_QUERY = """
            SELECT * FROM ${RecentProductContract.TABLE_NAME} ORDER BY ${BaseColumns._ID} DESC LIMIT ?        
        """.trimIndent()

        private val REMOVE_LAST_QUERY = """
            DELETE FROM ${RecentProductContract.TABLE_NAME}
            WHERE ${BaseColumns._ID} = (SELECT MAX(${BaseColumns._ID})
            FROM ${RecentProductContract.TABLE_NAME})
        """.trimIndent()
    }
}
