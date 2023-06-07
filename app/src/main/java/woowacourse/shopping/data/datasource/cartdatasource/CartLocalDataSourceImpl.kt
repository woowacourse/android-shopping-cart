package woowacourse.shopping.data.datasource.cartdatasource

import android.content.ContentValues
import android.database.Cursor
import com.example.domain.CartProduct
import com.example.domain.Product
import woowacourse.shopping.data.db.cart.CartContract
import woowacourse.shopping.data.db.cart.CartDbHelper

class CartLocalDataSourceImpl(
    dbHelper: CartDbHelper,
) : CartDataSource {
    private val db = dbHelper.writableDatabase

    override fun getAll(): List<CartProduct> {
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

    override fun getCartProducts(limit: Int, offset: Int): List<CartProduct> {
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

    private fun getCursor(limit: Int, offset: Int): Cursor {
        return db.rawQuery(
            """
            SELECT * FROM ${CartContract.TABLE_NAME} LIMIT $limit OFFSET $offset
            """.trimIndent(),
            null,
        )
    }

    private fun getCartProduct(cursor: Cursor): CartProduct {
        with(cursor) {
            val productId = getInt(getColumnIndexOrThrow(CartContract.TABLE_COLUMN_ID))
            val productImageUrl = getString(getColumnIndexOrThrow(CartContract.TABLE_COLUMN_IMAGE_URL))
            val productName = getString(getColumnIndexOrThrow(CartContract.TABLE_COLUMN_NAME))
            val productPrice = getInt(getColumnIndexOrThrow(CartContract.TABLE_COLUMN_PRICE))
            val productCount = getInt(getColumnIndexOrThrow(CartContract.TABLE_COLUMN_COUNT))

            return CartProduct(
                productId = productId,
                productImageUrl = productImageUrl,
                productName = productName,
                productPrice = productPrice,
                count = productCount,
            )
        }
    }

    override fun addColumn(product: Product, count: Int) {
        val values = ContentValues().apply {
            put(CartContract.TABLE_COLUMN_ID, product.id)
            put(CartContract.TABLE_COLUMN_IMAGE_URL, product.imageUrl)
            put(CartContract.TABLE_COLUMN_NAME, product.name)
            put(CartContract.TABLE_COLUMN_PRICE, product.price)
            put(CartContract.TABLE_COLUMN_COUNT, count)
        }

        db.insert(CartContract.TABLE_NAME, null, values)
    }

    override fun updateColumn(item: CartProduct) {
        val values = ContentValues().apply {
            put(CartContract.TABLE_COLUMN_ID, item.productId)
            put(CartContract.TABLE_COLUMN_IMAGE_URL, item.productImageUrl)
            put(CartContract.TABLE_COLUMN_NAME, item.productName)
            put(CartContract.TABLE_COLUMN_PRICE, item.productPrice)
            put(CartContract.TABLE_COLUMN_COUNT, item.count)
        }

        db.update(
            CartContract.TABLE_NAME,
            values,
            "${CartContract.TABLE_COLUMN_ID} = ${item.productId}",
            null,
        )
    }

    override fun deleteColumn(cartProduct: CartProduct) {
        db.delete(
            CartContract.TABLE_NAME,
            CartContract.TABLE_COLUMN_ID + "=" + cartProduct.productId,
            null,
        )
    }

    override fun findProductById(id: Int): CartProduct? {
        val cursor = db.rawQuery(
            "SELECT * FROM ${CartContract.TABLE_NAME} WHERE id = $id",
            null,
        )

        if (cursor.moveToFirst().not()) return null
        return getCartProduct(cursor)
    }
}
