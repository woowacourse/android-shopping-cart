package woowacourse.shopping.data

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.example.domain.CartProduct
import com.example.domain.Product

class CartDbHandler(
    private val db: SQLiteDatabase
) {

    fun addColumn(product: Product) {
        val values = ContentValues().apply {
            put(CartContract.TABLE_COLUMN_PRODUCT_ID, product.id)
            put(CartContract.TABLE_COLUMN_PRODUCT_IMAGE_URL, product.imageUrl)
            put(CartContract.TABLE_COLUMN_PRODUCT_NAME, product.name)
            put(CartContract.TABLE_COLUMN_PRODUCT_PRICE, product.price)
        }

        db.insert(CartContract.TABLE_NAME, null, values)
    }

    fun deleteColumn(cartProduct: CartProduct) {
        db.execSQL(
            """
            DELETE FROM ${CartDbHelper.DATABASE_NAME}
            WHERE TABLE_COLUMN_PRODUCT_ID = ${cartProduct.productId}
            """.trimIndent()
        )
    }
}
