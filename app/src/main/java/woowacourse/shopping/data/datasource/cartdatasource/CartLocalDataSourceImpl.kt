package woowacourse.shopping.data.datasource.cartdatasource

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.domain.CartProduct
import com.example.domain.Product
import woowacourse.shopping.data.db.cart.CartContract
import woowacourse.shopping.data.db.cart.CartDbHelper
import woowacourse.shopping.feature.list.item.ProductView.CartProductItem
import woowacourse.shopping.feature.model.mapper.toUi

class CartLocalDataSourceImpl(
    context: Context,
) {
    private val db = CartDbHelper(context).writableDatabase

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

    fun addColumn(product: Product, count: Int = DEFAULT_COUNT) {
        val values = ContentValues().apply {
            put(CartContract.TABLE_COLUMN_ID, product.id)
            put(CartContract.TABLE_COLUMN_IMAGE_URL, product.imageUrl)
            put(CartContract.TABLE_COLUMN_NAME, product.name)
            put(CartContract.TABLE_COLUMN_PRICE, product.price)
            put(CartContract.TABLE_COLUMN_COUNT, count)
        }

        db.insert(CartContract.TABLE_NAME, null, values)
    }

    fun updateColumn(item: CartProductItem) {
        val values = ContentValues().apply {
            put(CartContract.TABLE_COLUMN_ID, item.id)
            put(CartContract.TABLE_COLUMN_IMAGE_URL, item.imageUrl)
            put(CartContract.TABLE_COLUMN_NAME, item.name)
            put(CartContract.TABLE_COLUMN_PRICE, item.price)
            put(CartContract.TABLE_COLUMN_COUNT, item.count)
        }

        db.update(
            CartContract.TABLE_NAME,
            values,
            "${CartContract.TABLE_COLUMN_ID} = ${item.id}",
            null,
        )
    }

    fun deleteColumn(cartProduct: CartProduct) {
        db.delete(
            CartContract.TABLE_NAME,
            CartContract.TABLE_COLUMN_ID + "=" + cartProduct.productId,
            null,
        )
    }

    fun findProductById(id: Int): CartProductItem? {
        val cursor = db.rawQuery(
            "SELECT * FROM ${CartContract.TABLE_NAME} WHERE id = $id",
            null,
        )

        if (cursor.moveToFirst().not()) return null
        return getCartProduct(cursor).toUi()
    }

    companion object {
        private const val DEFAULT_COUNT = 1
    }
}
