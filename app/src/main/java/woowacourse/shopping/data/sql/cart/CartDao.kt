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
        db?.execSQL(CartTableContract.createSQL())
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${CartTableContract.TABLE_NAME}")
        onCreate(db)
    }

    fun selectAll(): List<CartProduct> {
        val cursor = readableDatabase.query(
            CartTableContract.TABLE_NAME,
            arrayOf(
                CartTableContract.TABLE_COLUMN_CART_ID,
                CartTableContract.TABLE_COLUMN_PRODUCT_ID,
                CartTableContract.TABLE_COLUMN_PRODUCT_COUNT
            ),
            null, null, null, null, null
        )

        val cart = mutableListOf<CartProduct>()
        while (cursor.moveToNext()) {
            val data = CartEntity(
                cursor.getLong(cursor.getColumnIndexOrThrow(CartTableContract.TABLE_COLUMN_CART_ID)),
                cursor.getLong(cursor.getColumnIndexOrThrow(CartTableContract.TABLE_COLUMN_PRODUCT_ID)),
                cursor.getInt(cursor.getColumnIndexOrThrow(CartTableContract.TABLE_COLUMN_PRODUCT_COUNT))
            )
            val product: Product = productsDatasource.find { it.id == data.productId } ?: continue
            cart.add(CartProduct(data.cartId, product))
        }

        cursor.close()
        return cart
    }

    fun insertProduct(product: Product) {
        val values = ContentValues().apply {
            put(CartTableContract.TABLE_COLUMN_PRODUCT_ID, product.id)
            put(CartTableContract.TABLE_COLUMN_PRODUCT_COUNT, 1) // 일단 1로 고정
        }
        writableDatabase.insert(CartTableContract.TABLE_NAME, null, values)
    }

    fun deleteCartProduct(cartProduct: CartProduct) {
        val selection = "${CartTableContract.TABLE_COLUMN_CART_ID} = ?"
        val selectionArgs = arrayOf("${cartProduct.cartId}")
        writableDatabase.delete(CartTableContract.TABLE_NAME, selection, selectionArgs)
    }

    companion object {
        private const val DB_NAME = "cart_db"
        private const val VERSION = 1
    }
}
