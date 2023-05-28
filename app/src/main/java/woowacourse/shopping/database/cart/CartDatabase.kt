package woowacourse.shopping.database.cart

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.domain.model.CartProduct
import com.example.domain.model.Product
import com.example.domain.repository.CartRepository

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
        val productId = cursor.getLong(cursor.getColumnIndex(CartConstant.TABLE_COLUMN_PRODUCT_ID))
        val productTitle =
            cursor.getString(cursor.getColumnIndex(CartConstant.TABLE_COLUMN_PRODUCT_NAME))
        val productPrice =
            cursor.getInt(cursor.getColumnIndex(CartConstant.TABLE_COLUMN_PRODUCT_PRICE))
        val productImgUrl =
            cursor.getString(cursor.getColumnIndex(CartConstant.TABLE_COLUMN_PRODUCT_IMAGE_URL))
        val cartProductCount =
            cursor.getInt(cursor.getColumnIndex(CartConstant.TABLE_COLUMN_CART_PRODUCT_COUNT))
        val cartIsChecked =
            cursor.getInt(cursor.getColumnIndex(CartConstant.TABLE_COLUMN_CART_PRODUCT_IS_CHECKED))
        val product = Product(productId, productTitle, productPrice, productImgUrl)
        return CartProduct(product, cartProductCount, cartIsChecked != 0)
    }

    override fun insert(product: CartProduct) {
        val values = ContentValues().apply {
            put(CartConstant.TABLE_COLUMN_PRODUCT_ID, product.product.id)
            put(CartConstant.TABLE_COLUMN_PRODUCT_NAME, product.product.name)
            put(CartConstant.TABLE_COLUMN_PRODUCT_PRICE, product.product.price)
            put(CartConstant.TABLE_COLUMN_PRODUCT_IMAGE_URL, product.product.imageUrl)

            val existingProduct = findById(product.product.id)
            val count = if (existingProduct != null) {
                existingProduct.count + product.count
            } else {
                product.count
            }
            put(CartConstant.TABLE_COLUMN_CART_PRODUCT_COUNT, count)
            put(CartConstant.TABLE_COLUMN_CART_PRODUCT_IS_CHECKED, true)
            put(CartConstant.TABLE_COLUMN_PRODUCT_SAVE_TIME, System.currentTimeMillis())
        }
        shoppingDb.insertWithOnConflict(
            CartConstant.TABLE_NAME,
            null,
            values,
            SQLiteDatabase.CONFLICT_REPLACE,
        )
    }

    override fun getSubList(offset: Int, size: Int): List<CartProduct> {
        val allProducts = getAll().ifEmpty { emptyList() }
        val lastIndex = allProducts.lastIndex
        val endIndex = (lastIndex + 1).coerceAtMost(offset + size)
        return when {
            offset < 0 && size > 0 && size <= lastIndex + 1 -> allProducts.subList(0, size)
            offset < 0 -> allProducts
            offset <= lastIndex -> allProducts.subList(offset, endIndex)
            else -> emptyList()
        }
    }

    override fun remove(id: Long) {
        val query =
            "DELETE FROM ${CartConstant.TABLE_NAME} WHERE ${CartConstant.TABLE_COLUMN_PRODUCT_ID} = $id"
        shoppingDb.execSQL(query)
    }

    override fun updateCount(id: Long, count: Int) {
        val query =
            "UPDATE ${CartConstant.TABLE_NAME} SET ${CartConstant.TABLE_COLUMN_CART_PRODUCT_COUNT} = $count WHERE  ${CartConstant.TABLE_COLUMN_PRODUCT_ID} = $id"
        shoppingDb.execSQL(query)
    }

    override fun findById(id: Long): CartProduct? {
        var cartProduct: CartProduct? = null
        findByIdCursor(id).use {
            if (it.moveToFirst()) {
                cartProduct = getCartProduct(it)
            }
        }
        return cartProduct
    }

    private fun findByIdCursor(id: Long): Cursor {
        val query =
            "SELECT * FROM ${CartConstant.TABLE_NAME} WHERE ${CartConstant.TABLE_COLUMN_PRODUCT_ID} = $id"
        return shoppingDb.rawQuery(query, null)
    }

    private fun getCartCursor(): Cursor {
        val query =
            "SELECT * FROM ${CartConstant.TABLE_NAME} ORDER BY ${CartConstant.TABLE_COLUMN_PRODUCT_SAVE_TIME}"
        return shoppingDb.rawQuery(query, null)
    }
}
