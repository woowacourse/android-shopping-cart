package woowacourse.shopping.data.database.dao.product

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import woowacourse.shopping.data.database.contract.ProductContract
import woowacourse.shopping.data.model.DataPrice
import woowacourse.shopping.data.model.DataProduct

class ProductDaoImpl(private val database: SQLiteOpenHelper) : ProductDao {
    @SuppressLint("Range")
    override fun getPartially(size: Int, lastId: Int): List<DataProduct> {
        val products = mutableListOf<DataProduct>()
        val db = database.writableDatabase
        val cursor =
            db.rawQuery(GET_PARTIALLY_QUERY, arrayOf(lastId.toString(), size.toString()))
        while (cursor.moveToNext()) {
            val id: Int = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            val name: String =
                cursor.getString(cursor.getColumnIndex(ProductContract.COLUMN_NAME))
            val price: DataPrice =
                DataPrice(cursor.getInt(cursor.getColumnIndex(ProductContract.COLUMN_PRICE)))
            val imageUrl: String =
                cursor.getString(cursor.getColumnIndex(ProductContract.COLUMN_IMAGE_URL))
            val selectedCount: Int =
                cursor.getInt(cursor.getColumnIndex(ProductContract.COLUMN_COUNT))
            products.add(DataProduct(id, name, price, imageUrl, selectedCount))
        }
        cursor.close()
        return products
    }

    fun add(product: DataProduct) {
        val contentValues = ContentValues().apply {
            put(ProductContract.COLUMN_NAME, product.name)
            put(ProductContract.COLUMN_PRICE, product.price.value)
            put(ProductContract.COLUMN_IMAGE_URL, product.imageUrl)
            put(ProductContract.COLUMN_COUNT, product.selectedCount)
        }

        database.writableDatabase.insert(ProductContract.TABLE_NAME, null, contentValues)
    }

    companion object {
        private val GET_PARTIALLY_QUERY = """
            SELECT * FROM ${ProductContract.TABLE_NAME}
            WHERE ${BaseColumns._ID} > ? 
            ORDER BY ${BaseColumns._ID} LIMIT ?        
        """.trimIndent()
    }
}
