package woowacourse.shopping.database.cart

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.domain.model.CartProduct
import com.domain.model.CartRepository
import com.domain.model.Product
import woowacourse.shopping.database.cart.CartConstant.TABLE_COLUMN_PRODUCT_CHECKED
import woowacourse.shopping.database.cart.CartConstant.TABLE_COLUMN_PRODUCT_COUNT
import woowacourse.shopping.database.cart.CartConstant.TABLE_COLUMN_PRODUCT_ID
import woowacourse.shopping.database.cart.CartConstant.TABLE_COLUMN_PRODUCT_IMAGE_URL
import woowacourse.shopping.database.cart.CartConstant.TABLE_COLUMN_PRODUCT_NAME
import woowacourse.shopping.database.cart.CartConstant.TABLE_COLUMN_PRODUCT_PRICE
import woowacourse.shopping.database.cart.CartConstant.TABLE_COLUMN_PRODUCT_SAVE_TIME
import woowacourse.shopping.database.cart.CartConstant.TABLE_NAME

class CartDatabase(
    private val shoppingDb: SQLiteDatabase,
) : CartRepository {
    override fun getAll(): List<CartProduct> {
        val cartProducts = mutableListOf<CartProduct>()
        getCartCursor().use {
            while (it.moveToNext()) {
                cartProducts.add(getCartProduct(it))
            }
        }
        return cartProducts
    }

    @SuppressLint("Range")
    private fun getCartProduct(cursor: Cursor): CartProduct {
        val productId = cursor.getInt(cursor.getColumnIndex(TABLE_COLUMN_PRODUCT_ID))
        val productTitle =
            cursor.getString(cursor.getColumnIndex(TABLE_COLUMN_PRODUCT_NAME))
        val productPrice =
            cursor.getInt(cursor.getColumnIndex(TABLE_COLUMN_PRODUCT_PRICE))
        val productImgUrl =
            cursor.getString(cursor.getColumnIndex(TABLE_COLUMN_PRODUCT_IMAGE_URL))
        val product = Product(productId, productTitle, productPrice, productImgUrl)
        val count = cursor.getInt(cursor.getColumnIndex(TABLE_COLUMN_PRODUCT_COUNT))
        val checked = cursor.getInt(cursor.getColumnIndex(TABLE_COLUMN_PRODUCT_CHECKED))
        return CartProduct(product, count, checked == 1)
    }

    override fun insert(product: Product, count: Int) {
        val values = ContentValues().apply {
            put(TABLE_COLUMN_PRODUCT_ID, product.id)
            put(TABLE_COLUMN_PRODUCT_NAME, product.name)
            put(TABLE_COLUMN_PRODUCT_PRICE, product.price)
            put(TABLE_COLUMN_PRODUCT_IMAGE_URL, product.imageUrl)
            put(TABLE_COLUMN_PRODUCT_COUNT, count)
            put(TABLE_COLUMN_PRODUCT_CHECKED, false)
            put(TABLE_COLUMN_PRODUCT_SAVE_TIME, System.currentTimeMillis())
        }
        shoppingDb.insertWithOnConflict(
            TABLE_NAME,
            null,
            values,
            SQLiteDatabase.CONFLICT_REPLACE,
        )
    }

    override fun getSubList(offset: Int, size: Int): List<CartProduct> {
        val lastIndex = getAll().lastIndex
        val endIndex = (lastIndex + 1).coerceAtMost(offset + size)
        return if (offset <= lastIndex) getAll().subList(offset, endIndex) else emptyList()
    }

    override fun remove(id: Int) {
        val query =
            "DELETE FROM $TABLE_NAME WHERE $TABLE_COLUMN_PRODUCT_ID = $id"
        shoppingDb.execSQL(query)
    }

    override fun updateCount(id: Int, count: Int) {
        val query =
            "UPDATE $TABLE_NAME SET $TABLE_COLUMN_PRODUCT_COUNT = $count WHERE $TABLE_COLUMN_PRODUCT_ID = $id"
        shoppingDb.execSQL(query)
    }

    override fun updateChecked(id: Int, checked: Boolean) {
        val query =
            "UPDATE $TABLE_NAME SET $TABLE_COLUMN_PRODUCT_CHECKED = $checked WHERE $TABLE_COLUMN_PRODUCT_ID = $id"
        shoppingDb.execSQL(query)
    }

    override fun getChecked(): List<CartProduct> {
        val cartProducts = mutableListOf<CartProduct>()
        val query =
            "SELECT * FROM $TABLE_NAME WHERE $TABLE_COLUMN_PRODUCT_CHECKED = 1"
        val cursor: Cursor = shoppingDb.rawQuery(query, null)
        while (cursor.moveToNext()) {
            cartProducts.add(getCartProduct(cursor))
        }
        return cartProducts
    }

    override fun findById(id: Int): CartProduct? {
        val query =
            "SELECT * FROM $TABLE_NAME WHERE $TABLE_COLUMN_PRODUCT_ID = $id"
        val cursor: Cursor = shoppingDb.rawQuery(query, null)
        var cartProduct: CartProduct? = null
        if (cursor.count > 0) {
            cursor.moveToNext()
            cartProduct = getCartProduct(cursor)
        }
        return cartProduct
    }

    private fun getCartCursor(): Cursor {
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $TABLE_COLUMN_PRODUCT_SAVE_TIME"
        return shoppingDb.rawQuery(query, null)
    }
}
