package woowacourse.shopping.data.cart

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.domain.CartProduct
import com.example.domain.Product

class CartDbHandler(
    private val db: SQLiteDatabase,
) {

    private fun getCursor(limit: Int, offset: Int): Cursor {
        return db.rawQuery(
            """
            SELECT * FROM ${CartContract.TABLE_NAME} LIMIT $limit OFFSET $offset
            """.trimIndent(),
            null,
        )
    }

    fun getAll(): List<CartProduct> {
        val cursor = db.rawQuery(
            "SELECT * FROM ${CartContract.TABLE_NAME}",
            null,
        )
        val cartProducts = mutableListOf<CartProduct>()

        with(cursor) {
            while (moveToNext()) {
                cartProducts.add(getCartProduct(cursor))
            }
        }
        cursor.close()
        return cartProducts
    }

    fun getCartProducts(limit: Int, offset: Int): List<CartProduct> {
        val cursor = getCursor(limit, offset)
        val cartProducts = mutableListOf<CartProduct>()

        with(cursor) {
            while (moveToNext()) {
                cartProducts.add(getCartProduct(cursor))
            }
        }
        cursor.close()
        return cartProducts
    }

    private fun getCartProduct(cursor: Cursor): CartProduct {
        with(cursor) {
            val productId = getInt(getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRODUCT_ID))
            val productImageUrl = getString(getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRODUCT_IMAGE_URL))
            val productName = getString(getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRODUCT_NAME))
            val productPrice = getInt(getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRODUCT_PRICE))

            return CartProduct(
                productId = productId,
                productImageUrl = productImageUrl,
                productName = productName,
                productPrice = productPrice,
            )
        }
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
            CartContract.TABLE_COLUMN_PRODUCT_ID + "=" + cartProduct.productId,
            null,
        )
    }
}
