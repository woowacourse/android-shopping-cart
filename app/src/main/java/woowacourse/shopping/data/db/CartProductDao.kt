package woowacourse.shopping.data.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.shopping.domain.CartProduct
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
                    )
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
            put(
                KEY_PRICE,
                cartProduct.product
                    .price
            )
        }
        writableDatabase.insert(TABLE_NAME, null, record)
    }

    fun remove(cartProduct: CartProduct) {
        writableDatabase.execSQL("DELETE FROM ${RecentProductDao.TABLE_NAME} WHERE ${RecentProductDao.KEY_ID}='${cartProduct.product.id}';")
    }

    companion object {
        const val DB_NAME = "CartProductDB"
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
