package woowacourse.shopping.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.shopping.domain.CartProduct
import com.shopping.domain.Count
import com.shopping.domain.Product

class CartProductDao(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE_QUERY)
    }

    fun getAll(): List<CartProduct> {
        val cartProducts = mutableListOf<CartProduct>()
        readableDatabase.rawQuery("SELECT * FROM $TABLE_NAME", null).use {
            while (it.moveToNext()) {
                val cartProduct = CartProduct(
                    Product(
                        id = it.getInt(it.getColumnIndexOrThrow(KEY_ID)),
                        name = it.getString(it.getColumnIndexOrThrow(KEY_NAME)),
                        url = it.getString(it.getColumnIndexOrThrow(KEY_IMAGE)),
                        price = it.getInt(it.getColumnIndexOrThrow(KEY_PRICE)),
                    ),
                    count = Count(it.getInt(it.getColumnIndexOrThrow(KEY_COUNT)))
                )
                cartProducts.add(cartProduct)
            }
        }
        return cartProducts
    }

    fun insert(cartProduct: CartProduct) {
        val record = ContentValues().apply {
            put(KEY_ID, cartProduct.product.id)
            put(KEY_NAME, cartProduct.product.name)
            put(KEY_IMAGE, cartProduct.product.url)
            put(KEY_PRICE, cartProduct.product.price)
            put(KEY_COUNT, cartProduct.count.value)
        }
        writableDatabase.insert(TABLE_NAME, null, record)
    }

    fun remove(cartProduct: CartProduct) {
        writableDatabase.execSQL("DELETE FROM ${RecentProductDao.TABLE_NAME} WHERE ${RecentProductDao.KEY_ID}='${cartProduct.product.id}';")
    }

    fun update(cartProduct: CartProduct) {
        if(exist(cartProduct)) {
            val existCount = getCountById(cartProduct.product.id)

            val contextValues = ContentValues().apply {
                put(KEY_COUNT, existCount + cartProduct.count.value)
            }
            val whereClause = "$KEY_ID=?"
            val whereArgs = arrayOf(cartProduct.product.id.toString())

            writableDatabase.update(TABLE_NAME, contextValues, whereClause, whereArgs)

        } else {
            insert(cartProduct)
        }
    }

    fun exist(cartProduct: CartProduct): Boolean {
        val query = "SELECT $KEY_ID FROM $TABLE_NAME WHERE $KEY_ID = ?"
        val selectionArgs = arrayOf(cartProduct.product.id.toString())

        val cursor: Cursor = writableDatabase.rawQuery(query, selectionArgs)
        val exists = cursor.moveToFirst()
        cursor.close()

        return exists
    }

    fun getCountById(id: Int): Int {
        val query = "SELECT $KEY_COUNT FROM $TABLE_NAME WHERE $KEY_ID = ?"
        val selectionArgs = arrayOf(id.toString())

        val cursor: Cursor = writableDatabase.rawQuery(query, selectionArgs)
        var count = 0

        if (cursor.moveToFirst()) {
            count = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_COUNT))
        }

        cursor.close()
        return count
    }

    companion object {
        const val DB_NAME = "CartProductDB"
        private const val DB_VERSION: Int = 1
        const val TABLE_NAME = "products"
        const val KEY_ID = "id"
        const val KEY_NAME = "name"
        const val KEY_IMAGE = "image"
        const val KEY_PRICE = "price"
        const val KEY_COUNT = "count"

        private const val CREATE_TABLE_QUERY = "CREATE TABLE $TABLE_NAME (" +
            "$KEY_ID int," +
            "$KEY_NAME text," +
            "$KEY_IMAGE text," +
            "$KEY_PRICE text," +
            "$KEY_COUNT text" +
            ");"
        private const val DROP_TABLE_QUERY = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}
