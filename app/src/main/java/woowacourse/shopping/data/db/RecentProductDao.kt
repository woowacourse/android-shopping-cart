package woowacourse.shopping.data.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.shopping.domain.Product
import com.shopping.domain.RecentProduct

class RecentProductDao(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE_QUERY)
    }

    fun getAll(): List<RecentProduct> {
        val products = mutableListOf<RecentProduct>()
        readableDatabase.rawQuery("SELECT * FROM $TABLE_NAME", null).use {
            while (it.moveToNext()) {
                val recentProduct = RecentProduct(
                    Product(
                        id = it.getInt(it.getColumnIndexOrThrow(KEY_ID)),
                        name = it.getString(it.getColumnIndexOrThrow(KEY_NAME)),
                        url = it.getString(it.getColumnIndexOrThrow(KEY_IMAGE)),
                        price = it.getInt(it.getColumnIndexOrThrow(KEY_PRICE)),
                    )
                )
                products.add(recentProduct)
            }
        }

        if (products.isEmpty()) {
            return emptyList()
        }
        return products.reversed().subList(0, minOf(products.size, 10))
    }

    fun getFirst(): RecentProduct {
        return getAll().first()
    }

    fun insert(recentProduct: RecentProduct) {
        if (exist(recentProduct)) {
            remove(recentProduct)
        }

        val record = ContentValues().apply {
            put(KEY_ID, recentProduct.product.id)
            put(KEY_NAME, recentProduct.product.name)
            put(KEY_IMAGE, recentProduct.product.url)
            put(KEY_PRICE, recentProduct.product.price)
        }
        writableDatabase.insert(TABLE_NAME, null, record)
    }

    private fun exist(recentProduct: RecentProduct): Boolean {
        val arrayToFind = arrayOf(recentProduct.product.id.toString())
        val cursor = readableDatabase.query(
            TABLE_NAME, arrayOf("COUNT(*)"), "$KEY_ID=?", arrayToFind, null, null, null
        )
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        return count >= 1
    }

    fun remove(recentProduct: RecentProduct) {
        writableDatabase.execSQL("DELETE FROM $TABLE_NAME WHERE $KEY_ID='${recentProduct.product.id}';")
    }

    fun isEmpty(): Boolean =
        writableDatabase.rawQuery("SELECT * FROM $TABLE_NAME", null).use { it.count == 0 }

    companion object {
        const val DB_NAME = "RecentProductDB"
        private const val DB_VERSION: Int = 1
        const val TABLE_NAME = "products"
        const val KEY_ID = "id"
        const val KEY_NAME = "name"
        const val KEY_IMAGE = "image"
        const val KEY_PRICE = "price"

        private const val CREATE_TABLE_QUERY = "CREATE TABLE $TABLE_NAME (" +
            "$KEY_ID int," +
            "$KEY_NAME text," +
            "$KEY_IMAGE text," +
            "$KEY_PRICE text" +
            ");"
        private const val DROP_TABLE_QUERY = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}
