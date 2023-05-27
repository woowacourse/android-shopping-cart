package woowacourse.shopping.data.sql.cart

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.domain.datasource.productsDatasource
import com.example.domain.model.CartProduct
import com.example.domain.model.Product
import woowacourse.shopping.data.model.CartEntity

class CartDao(
    context: Context
) : SQLiteOpenHelper(context, DB_NAME, null, VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CartContract.createSQL())
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${CartContract.TABLE_NAME}")
        onCreate(db)
    }

    fun selectAll(): List<CartProduct> {
        val cursor = readableDatabase.query(
            CartContract.TABLE_NAME,
            arrayOf(
                CartContract.TABLE_COLUMN_PRODUCT_ID,
                CartContract.TABLE_COLUMN_PRODUCT_COUNT,
                CartContract.TABLE_COLUMN_PRODUCT_IS_SELECTED
            ),
            null, null, null, null, null
        )

        val cart = mutableListOf<CartProduct>()
        while (cursor.moveToNext()) {
            val data = CartEntity(
                cursor.getLong(cursor.getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRODUCT_ID)),
                cursor.getInt(cursor.getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRODUCT_COUNT)),
                cursor.getInt(cursor.getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRODUCT_IS_SELECTED))
            )
            val product: Product = productsDatasource.find { it.id == data.productId } ?: continue
            cart.add(CartProduct(product, data.count, data.isSelected == 1))
        }

        cursor.close()
        return cart
    }

    fun putProduct(product: Product, count: Int) {
        val findProduct = selectAll().find { it.product.id == product.id }
        if (findProduct == null) {
            insertNewProduct(product, count)
        } else {
            addProduct(product, count)
        }
    }

    private fun insertNewProduct(product: Product, count: Int) {
        val values = ContentValues().apply {
            put(CartContract.TABLE_COLUMN_PRODUCT_ID, product.id)
            put(CartContract.TABLE_COLUMN_PRODUCT_COUNT, count)
            put(CartContract.TABLE_COLUMN_PRODUCT_IS_SELECTED, 1)
        }
        writableDatabase.insert(CartContract.TABLE_NAME, null, values)
    }

    private fun addProduct(product: Product, count: Int) {
        val updateSql = "UPDATE ${CartContract.TABLE_NAME} " +
            "SET ${CartContract.TABLE_COLUMN_PRODUCT_COUNT}=$count " +
            "WHERE ${CartContract.TABLE_COLUMN_PRODUCT_ID}=${product.id}"

        writableDatabase.execSQL(updateSql)
    }

    fun deleteCartProduct(product: Product) {
        val selection = "${CartContract.TABLE_COLUMN_PRODUCT_ID} = ?"
        val selectionArgs = arrayOf("${product.id}")
        writableDatabase.delete(CartContract.TABLE_NAME, selection, selectionArgs)
    }

    fun updateSelection(product: Product, isSelected: Int) {
        val updateSql = "UPDATE ${CartContract.TABLE_NAME} " +
            "SET ${CartContract.TABLE_COLUMN_PRODUCT_IS_SELECTED}=$isSelected " +
            "WHERE ${CartContract.TABLE_COLUMN_PRODUCT_ID}=${product.id}"

        writableDatabase.execSQL(updateSql)
    }

    companion object {
        private const val DB_NAME = "cart_db"
        private const val VERSION = 3
    }
}
