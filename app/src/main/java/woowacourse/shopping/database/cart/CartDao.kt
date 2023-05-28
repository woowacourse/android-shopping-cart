package woowacourse.shopping.database.cart

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.domain.model.CartProduct
import com.domain.model.Product
import woowacourse.shopping.database.cart.CartContract.TABLE_COLUMN_PRODUCT_SAVE_TIME
import woowacourse.shopping.database.cart.CartContract.TABLE_NAME

class CartDao(
    context: Context?,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            CartContract.createSQL(),
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun getAll(): List<CartProduct> {
        val cartProducts = mutableListOf<CartProduct>()
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $TABLE_COLUMN_PRODUCT_SAVE_TIME"
        val cursor: Cursor = readableDatabase.rawQuery(query, null)

        while (cursor.moveToNext()) {
            cartProducts.add(getCartProduct(cursor))
        }
        return cartProducts
    }

    fun getCartProduct(cursor: Cursor): CartProduct {
        val productId =
            cursor.getInt(cursor.getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRODUCT_ID))
        val productTitle =
            cursor.getString(cursor.getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRODUCT_NAME))
        val productPrice =
            cursor.getInt(cursor.getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRODUCT_PRICE))
        val productImgUrl =
            cursor.getString(cursor.getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRODUCT_IMAGE_URL))
        val product = Product(productId, productTitle, productPrice, productImgUrl)
        val count =
            cursor.getInt(cursor.getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRODUCT_COUNT))
        val checked =
            cursor.getInt(cursor.getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRODUCT_CHECKED))
        return CartProduct(product, count, checked == 1)
    }

    fun insert(product: Product, count: Int) {
        val existProduct = findById(product.id)
        if (existProduct != null) {
            updateCount(product.id, existProduct.count + count)
        } else {
            val values = ContentValues().apply {
                put(CartContract.TABLE_COLUMN_PRODUCT_ID, product.id)
                put(CartContract.TABLE_COLUMN_PRODUCT_NAME, product.name)
                put(CartContract.TABLE_COLUMN_PRODUCT_PRICE, product.price)
                put(CartContract.TABLE_COLUMN_PRODUCT_IMAGE_URL, product.imageUrl)
                put(CartContract.TABLE_COLUMN_PRODUCT_COUNT, count)
                put(CartContract.TABLE_COLUMN_PRODUCT_CHECKED, false)
                put(TABLE_COLUMN_PRODUCT_SAVE_TIME, System.currentTimeMillis())
            }
            writableDatabase.insert(TABLE_NAME, null, values)
        }
    }

    fun remove(id: Int) {
        val query =
            "DELETE FROM $TABLE_NAME WHERE ${CartContract.TABLE_COLUMN_PRODUCT_ID} = $id"
        writableDatabase.execSQL(query)
    }

    fun updateCount(id: Int, count: Int) {
        val query =
            "UPDATE $TABLE_NAME SET ${CartContract.TABLE_COLUMN_PRODUCT_COUNT} = $count WHERE ${CartContract.TABLE_COLUMN_PRODUCT_ID} = $id"
        writableDatabase.execSQL(query)
    }

    fun updateChecked(id: Int, checked: Boolean) {
        val query =
            "UPDATE $TABLE_NAME SET ${CartContract.TABLE_COLUMN_PRODUCT_CHECKED} = $checked WHERE ${CartContract.TABLE_COLUMN_PRODUCT_ID} = $id"
        writableDatabase.execSQL(query)
    }

    private fun findById(id: Int): CartProduct? {
        val query =
            "SELECT * FROM $TABLE_NAME WHERE ${CartContract.TABLE_COLUMN_PRODUCT_ID} = $id"
        val cursor: Cursor = readableDatabase.rawQuery(query, null)
        var cartProduct: CartProduct? = null
        if (cursor.count > 0) {
            cursor.moveToNext()
            cartProduct = getCartProduct(cursor)
        }
        return cartProduct
    }

    companion object {
        const val DATABASE_VERSION = 7
        const val DATABASE_NAME = "shopping_db"
    }
}
