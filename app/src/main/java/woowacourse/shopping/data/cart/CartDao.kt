package woowacourse.shopping.data.cart

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.domain.CartProduct
import com.example.domain.Product

class CartDao(context: Context) {

    private val db: SQLiteDatabase = CartDbHelper(context).writableDatabase

    private fun getCursor(): Cursor {
        return db.query(
            CartContract.TABLE_NAME,
            arrayOf(
                CartContract.TABLE_COLUMN_PRODUCT_ID,
                CartContract.TABLE_COLUMN_PRODUCT_IMAGE_URL,
                CartContract.TABLE_COLUMN_PRODUCT_NAME,
                CartContract.TABLE_COLUMN_PRODUCT_PRICE,
                CartContract.TABLE_COLUMN_COUNT,
                CartContract.TABLE_COLUMN_CHECKED
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
                val productImageUrl =
                    getString(getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRODUCT_IMAGE_URL))
                val productName =
                    getString(getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRODUCT_NAME))
                val productPrice =
                    getInt(getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRODUCT_PRICE))
                val count = getInt(getColumnIndexOrThrow(CartContract.TABLE_COLUMN_COUNT))
                val checked: Boolean =
                    getInt(getColumnIndexOrThrow(CartContract.TABLE_COLUMN_CHECKED)) == CHECKED_TRUE

                list.add(
                    CartProduct(
                        productId, productImageUrl, productName, productPrice, count, checked
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
            put(CartContract.TABLE_COLUMN_COUNT, 1) // 담았을 때 기준 기본 1
            put(CartContract.TABLE_COLUMN_CHECKED, CHECKED_FALSE)
        }

        db.insert(CartContract.TABLE_NAME, null, values)
    }

    fun addColumn(cartProduct: CartProduct) {
        val values = ContentValues().apply {
            put(CartContract.TABLE_COLUMN_PRODUCT_ID, cartProduct.productId)
            put(CartContract.TABLE_COLUMN_PRODUCT_IMAGE_URL, cartProduct.productImageUrl)
            put(CartContract.TABLE_COLUMN_PRODUCT_NAME, cartProduct.productName)
            put(CartContract.TABLE_COLUMN_PRODUCT_PRICE, cartProduct.productPrice)
            put(CartContract.TABLE_COLUMN_COUNT, 1) // 담았을 때 기준 기본 1
            put(CartContract.TABLE_COLUMN_CHECKED, CHECKED_FALSE)
        }

        db.insert(CartContract.TABLE_NAME, null, values)
    }

    fun deleteColumn(cartProduct: CartProduct) {
        db.delete(
            CartContract.TABLE_NAME,
            CartContract.TABLE_COLUMN_PRODUCT_ID + "=" + cartProduct.productId, null
        )
    }

    fun updateCartProductCount(cartProduct: CartProduct, count: Int) {
        val findCartProduct =
            getAll().find { it.productId == cartProduct.productId } ?: return addColumn(cartProduct)

        if (count <= 0) return deleteColumn(findCartProduct)

        val updateSql =
            """
                UPDATE ${CartContract.TABLE_NAME}
                SET ${CartContract.TABLE_COLUMN_COUNT} = $count
                WHERE ${CartContract.TABLE_COLUMN_PRODUCT_ID} = ${cartProduct.productId};
            """.trimIndent()

        db.execSQL(updateSql)
    }

    fun updateCartProductChecked(product: Product, checked: Boolean) {
        val findCartProduct =
            getAll().find { it.productId == product.id } ?: return

        val checkedState = if (checked) CHECKED_TRUE else CHECKED_FALSE

        val updateSql = "UPDATE ${CartContract.TABLE_NAME} " +
            "SET ${CartContract.TABLE_COLUMN_CHECKED}=$checkedState " +
            "WHERE ${CartContract.TABLE_COLUMN_PRODUCT_ID}=${findCartProduct.productId}"

        db.execSQL(updateSql)
    }

    fun createTable() {
        db.execSQL(
            """
                CREATE TABLE ${CartContract.TABLE_NAME} (
                    ${CartContract.TABLE_COLUMN_PRODUCT_ID} INTEGER,
                    ${CartContract.TABLE_COLUMN_PRODUCT_IMAGE_URL} TEXT,
                    ${CartContract.TABLE_COLUMN_PRODUCT_NAME} TEXT,
                    ${CartContract.TABLE_COLUMN_PRODUCT_PRICE} INTEGER,
                    ${CartContract.TABLE_COLUMN_COUNT} INTEGER,
                    ${CartContract.TABLE_COLUMN_CHECKED} INTEGER
                )
            """.trimIndent()
        )
    }

    fun deleteTable() {
        db.execSQL(
            """
                DROP TABLE IF EXISTS ${CartContract.TABLE_NAME};
            """.trimIndent()
        )
    }

    companion object {
        private const val CHECKED_TRUE = 1
        private const val CHECKED_FALSE = 0
    }
}
