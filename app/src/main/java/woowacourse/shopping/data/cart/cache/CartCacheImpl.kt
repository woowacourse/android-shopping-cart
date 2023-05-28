package woowacourse.shopping.data.cart.cache

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.ShoppingDao
import woowacourse.shopping.data.cart.CartDBContract
import woowacourse.shopping.data.cart.CartProductEntity

class CartCacheImpl(
    shoppingDao: ShoppingDao,
) : CartCache {

    private val shoppingDB: SQLiteDatabase = shoppingDao.writableDatabase

    override fun getCartProducts(): List<CartProductEntity> {
        val cartProducts = mutableListOf<CartProductEntity>()
        val query = "SELECT * FROM ${CartDBContract.TABLE_NAME}"
        val cursor = shoppingDB.rawQuery(query, null)

        cursor?.apply {
            if (moveToFirst()) {
                do {
                    cartProducts.add(cursor.getShoppingCartProduct())
                } while (moveToNext())
            }
        }
        cursor?.close()

        return cartProducts.toList()
    }

    override fun getCartProductById(id: Int): CartProductEntity {
        val cursor = shoppingDB.rawQuery(
            "select * from ${CartDBContract.TABLE_NAME} where ${CartDBContract.CART_PRODUCT_ID} = ?",
            arrayOf(id.toString())
        ).apply {
            moveToNext()
        }
        val shoppingCartProduct = cursor.getShoppingCartProduct()

        cursor.close()

        return shoppingCartProduct
    }

    override fun getCountOfCartProducts(): Int {
        var count = 0
        val cursor =
            shoppingDB.rawQuery("SELECT COUNT(*) FROM ${CartDBContract.TABLE_NAME}", null)
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()

        return count
    }

    override fun addToCart(id: Int, count: Int) {
        val selection = "id = ?"
        val selectionArgs = arrayOf(id.toString())
        val cursor = getCursorFromCartTable(
            selection = selection,
            selectionColumns = arrayOf("id"),
            selectionArgs = selectionArgs
        )

        val values = ContentValues().apply {
            put(CartDBContract.CART_PRODUCT_ID, id)
            put(CartDBContract.COUNT, count)
        }

        with(cursor) {
            if (moveToFirst()) {
                shoppingDB.update(
                    CartDBContract.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs
                )
            } else {
                shoppingDB.insert(CartDBContract.TABLE_NAME, null, values)
            }
            close()
        }
    }

    private fun getCursorFromCartTable(
        selection: String,
        selectionColumns: Array<String>,
        selectionArgs: Array<String>,
    ): Cursor {

        return shoppingDB.query(
            CartDBContract.TABLE_NAME, selectionColumns,
            selection, selectionArgs,
            null, null, null
        )
    }

    override fun removeCartProductById(id: Int) {
        shoppingDB.delete(
            CartDBContract.TABLE_NAME,
            "${CartDBContract.CART_PRODUCT_ID} = ?",
            arrayOf(id.toString())
        )
    }

    private fun Cursor.getShoppingCartProduct(): CartProductEntity {
        val id = getInt(getColumnIndexOrThrow(CartDBContract.CART_PRODUCT_ID))
        val count = getInt(getColumnIndexOrThrow(CartDBContract.COUNT))
        return CartProductEntity(
            id = id,
            count = count
        )
    }
}
