package woowacourse.shopping.data.cart

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.domain.CartProduct
import com.example.domain.Product
import woowacourse.shopping.model.CartProductState.Companion.MIN_COUNT_VALUE

class CartDao(context: Context) {
    private val cartDb: SQLiteDatabase = CartDbHelper(context).writableDatabase

    private fun getCursor(selection: String? = ""): Cursor {
        return cartDb.query(
            CartContract.TABLE_NAME,
            arrayOf(
                CartContract.TABLE_COLUMN_PRODUCT_ID,
                CartContract.TABLE_COLUMN_PRODUCT_IMAGE_URL,
                CartContract.TABLE_COLUMN_PRODUCT_NAME,
                CartContract.TABLE_COLUMN_PRODUCT_PRICE,
                CartContract.TABLE_COLUMN_COUNT,
                CartContract.TABLE_COLUMN_CHECKED
            ),
            selection, arrayOf(), null, null, ""
        )
    }

    fun getCartProduct(productId: Int): CartProduct? {
        val cursor = getCursor("${CartContract.TABLE_COLUMN_PRODUCT_ID} = $productId")
        var cartProduct: CartProduct? = null

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRODUCT_ID))
                val imageUrl =
                    getString(getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRODUCT_IMAGE_URL))
                val name = getString(getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRODUCT_NAME))
                val price = getInt(getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRODUCT_PRICE))
                val count = getInt(getColumnIndexOrThrow(CartContract.TABLE_COLUMN_COUNT))
                val checked =
                    getInt(getColumnIndexOrThrow(CartContract.TABLE_COLUMN_CHECKED)) == CHECKED_TRUE
                cartProduct = CartProduct(id, imageUrl, name, price, count, checked)
            }
        }
        cursor.close()
        return cartProduct
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

    fun addColumn(product: Product, count: Int = MIN_COUNT_VALUE) {
        val findCartProduct: CartProduct? = getCartProduct(product.id)

        if (findCartProduct == null) {
            val values = ContentValues().apply {
                put(CartContract.TABLE_COLUMN_PRODUCT_ID, product.id)
                put(CartContract.TABLE_COLUMN_PRODUCT_IMAGE_URL, product.imageUrl)
                put(CartContract.TABLE_COLUMN_PRODUCT_NAME, product.name)
                put(CartContract.TABLE_COLUMN_PRODUCT_PRICE, product.price)
                put(CartContract.TABLE_COLUMN_COUNT, count) // 담았을 때 기준 기본 1
                put(CartContract.TABLE_COLUMN_CHECKED, CHECKED_FALSE)
            }
            cartDb.insert(CartContract.TABLE_NAME, null, values)
            return
        }
        updateCartProductCount(findCartProduct.productId, count + findCartProduct.count)
    }

    fun deleteColumn(productId: Int) {
        cartDb.delete(
            CartContract.TABLE_NAME,
            CartContract.TABLE_COLUMN_PRODUCT_ID + "=" + productId, null
        )
    }

    fun updateCartProductCount(productId: Int, count: Int) {
        val findCartProduct: CartProduct = getCartProduct(productId) ?: return
        if (count <= 0) return deleteColumn(findCartProduct.productId)

        val updateSql =
            """
                UPDATE ${CartContract.TABLE_NAME}
                SET ${CartContract.TABLE_COLUMN_COUNT} = $count
                WHERE ${CartContract.TABLE_COLUMN_PRODUCT_ID} = $productId;
            """.trimIndent()

        cartDb.execSQL(updateSql)
    }

    fun updateCartProductChecked(productId: Int, checked: Boolean) {
        val findCartProduct: CartProduct = getCartProduct(productId) ?: return
        val checkedState = if (checked) CHECKED_TRUE else CHECKED_FALSE
        val updateSql = "UPDATE ${CartContract.TABLE_NAME} " +
            "SET ${CartContract.TABLE_COLUMN_CHECKED}=$checkedState " +
            "WHERE ${CartContract.TABLE_COLUMN_PRODUCT_ID}=${findCartProduct.productId}"

        cartDb.execSQL(updateSql)
    }

    fun createTable() {
        cartDb.execSQL(
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
        cartDb.execSQL(
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
