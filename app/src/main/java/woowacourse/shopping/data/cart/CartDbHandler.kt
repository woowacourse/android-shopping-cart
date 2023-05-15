package woowacourse.shopping.data.cart

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.domain.CartProduct
import com.example.domain.Product

class CartDbHandler(
    private val db: SQLiteDatabase
) {

    private fun getCursor(): Cursor {
        return db.query(
            CartContract.TABLE_NAME,
            arrayOf(
                CartContract.TABLE_COLUMN_PRODUCT_ID,
                CartContract.TABLE_COLUMN_PRODUCT_IMAGE_URL,
                CartContract.TABLE_COLUMN_PRODUCT_NAME,
                CartContract.TABLE_COLUMN_PRODUCT_PRICE
            ),
            "", arrayOf(), null, null, ""
        )
    }

    fun getAll(): List<CartProduct> {
        val cursor = getCursor()
        val list = mutableListOf<CartProduct>()

        with(cursor) {
            while (moveToNext()) {
                val productId = getInt(getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRODUCT_ID))
                val productImageUrl = getString(getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRODUCT_IMAGE_URL))
                val productName = getString(getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRODUCT_NAME))
                val productPrice = getInt(getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRODUCT_PRICE))

                list.add(
                    CartProduct(
                        productId, productImageUrl, productName, productPrice
                    )
                )
            }
        }

        cursor.close()
        return list
    }

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
        db.delete(
            CartContract.TABLE_NAME,
            CartContract.TABLE_COLUMN_PRODUCT_ID + "=" + cartProduct.productId, null
        )
    }
}
