package woowacourse.shopping.data.database.dao.recentproduct

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import woowacourse.shopping.data.database.contract.ProductContract
import woowacourse.shopping.data.database.contract.RecentProductContract
import woowacourse.shopping.data.model.DataPrice
import woowacourse.shopping.data.model.DataProduct
import woowacourse.shopping.data.model.DataRecentProduct

class RecentProductDaoImpl(private val database: SQLiteOpenHelper) : RecentProductDao {

    @SuppressLint("Range")
    override fun getSize(): Int {
        val size: Int
        database.writableDatabase.use { db ->
            db.rawQuery(GET_ALL_QUERY, null).use {
                it.moveToFirst()
                size = it.getInt(0)
            }
        }
        return size
    }

    @SuppressLint("Range")
    override fun getPartially(size: Int): List<DataRecentProduct> {
        val products = mutableListOf<DataRecentProduct>()
        database.writableDatabase.use { db ->
            val cursor = db.rawQuery(GET_PARTIALLY_QUERY, arrayOf(size.toString()))
            while (cursor.moveToNext()) {
                val id: Int = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
                val productId: Int =
                    cursor.getInt(cursor.getColumnIndex("${ProductContract.TABLE_NAME}${BaseColumns._ID}"))
                val name: String =
                    cursor.getString(cursor.getColumnIndex(ProductContract.COLUMN_NAME))
                val price: DataPrice =
                    DataPrice(cursor.getInt(cursor.getColumnIndex(ProductContract.COLUMN_PRICE)))
                val imageUrl: String =
                    cursor.getString(cursor.getColumnIndex(ProductContract.COLUMN_IMAGE_URL))
                products.add(DataRecentProduct(id, DataProduct(productId, name, price, imageUrl)))
            }
            cursor.close()
        }
        return products
    }

    override fun add(recentProduct: DataProduct) {
        removeByProductId(recentProduct.id)

        val contentValues = ContentValues().apply {
            put("${ProductContract.TABLE_NAME}${BaseColumns._ID}", recentProduct.id)
        }

        database.writableDatabase.use { db ->
            db.insert(RecentProductContract.TABLE_NAME, null, contentValues)
        }
    }

    private fun removeByProductId(productId: Int) {
        database.writableDatabase.use { db ->
            db.delete(
                RecentProductContract.TABLE_NAME,
                " ${ProductContract.TABLE_NAME}${BaseColumns._ID} = ?",
                arrayOf(productId.toString())
            )
        }
    }

    override fun removeLast() {
        database.writableDatabase.use { db ->
            db.rawQuery(REMOVE_LAST_QUERY, null)
        }
    }

    companion object {
        private val GET_ALL_QUERY = """
            SELECT COUNT(*) FROM ${RecentProductContract.TABLE_NAME} 
        """.trimIndent()

        private val GET_PARTIALLY_QUERY = """
            SELECT ${RecentProductContract.TABLE_NAME}.*, ${ProductContract.TABLE_NAME}.${ProductContract.COLUMN_NAME}, ${ProductContract.TABLE_NAME}.${ProductContract.COLUMN_PRICE}, ${ProductContract.TABLE_NAME}.${ProductContract.COLUMN_IMAGE_URL}
            FROM ${RecentProductContract.TABLE_NAME}
            INNER JOIN ${ProductContract.TABLE_NAME} ON ${RecentProductContract.TABLE_NAME}.${ProductContract.TABLE_NAME}${BaseColumns._ID} = ${ProductContract.TABLE_NAME}.${BaseColumns._ID}
            ORDER BY ${RecentProductContract.TABLE_NAME}.${BaseColumns._ID} DESC LIMIT ?
        """.trimIndent()

        private val REMOVE_LAST_QUERY = """
            DELETE FROM ${RecentProductContract.TABLE_NAME}
            WHERE ${BaseColumns._ID} = (SELECT MAX(${BaseColumns._ID}) FROM ${RecentProductContract.TABLE_NAME})
        """.trimIndent()
    }
}
