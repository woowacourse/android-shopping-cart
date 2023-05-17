package woowacourse.shopping.data.datasource.dao

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.database.selectRowId
import woowacourse.shopping.data.database.table.SqlCart
import woowacourse.shopping.data.database.table.SqlProduct
import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.URL

class CartDao(private val db: SQLiteDatabase) : CartDataSource {
    override fun insertCartProduct(product: Product) {
        val productRow: MutableMap<String, Any> = mutableMapOf()
        productRow[SqlProduct.PICTURE] = product.picture.value
        productRow[SqlProduct.TITLE] = product.title
        productRow[SqlProduct.PRICE] = product.price
        val productId = SqlProduct.selectRowId(db, productRow)

        insertOrUpdateCartProduct(productId)
    }

    override fun selectAllCount(): Int {
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM ${SqlCart.name}", null
        )
        return cursor.use {
            it.moveToNext()
            it.getInt(0)
        }
    }

    override fun selectAll(): Cart {
        val cursor = db.rawQuery(
            "SELECT * FROM ${SqlCart.name}, ${SqlProduct.name} on ${SqlCart.name}.${SqlCart.PRODUCT_ID} = ${SqlProduct.name}.${SqlProduct.ID}",
            null
        )
        return makeCart(cursor)
    }

    override fun selectPage(page: Int, countPerPage: Int): Cart {
        val cursor = db.rawQuery(
            "SELECT * FROM ${SqlCart.name}, ${SqlProduct.name} on ${SqlCart.name}.${SqlCart.PRODUCT_ID} = ${SqlProduct.name}.${SqlProduct.ID} LIMIT ${page * countPerPage}, $countPerPage",
            null
        )
        return makeCart(cursor)
    }

    override fun deleteCartProductByOrdinal(product: Product) {
        val productRow: MutableMap<String, Any> = mutableMapOf()
        productRow[SqlProduct.PICTURE] = product.picture.value
        productRow[SqlProduct.TITLE] = product.title
        productRow[SqlProduct.PRICE] = product.price
        val productId = SqlProduct.selectRowId(db, productRow)
        updateOrDeleteCartProduct(productId)
    }

    private fun insertOrUpdateCartProduct(productId: Int) {
        val query =
            "INSERT INTO ${SqlCart.name} (${SqlCart.AMOUNT}, ${SqlCart.PRODUCT_ID}) VALUES (1, $productId) " + "ON CONFLICT(${SqlCart.PRODUCT_ID}) DO UPDATE SET ${SqlCart.AMOUNT} = ${SqlCart.AMOUNT} + 1"

        db.execSQL(query)
    }

    private fun updateOrDeleteCartProduct(productId: Int) {
        db.execSQL("UPDATE ${SqlCart.name} SET ${SqlCart.AMOUNT} = CASE WHEN ${SqlCart.AMOUNT} > 1 THEN ${SqlCart.AMOUNT} - 1 ELSE 0 END WHERE ${SqlCart.PRODUCT_ID} = $productId")
        db.execSQL("DELETE FROM ${SqlCart.name} WHERE ${SqlCart.PRODUCT_ID} = $productId AND ${SqlCart.AMOUNT} = 0")
    }

    private fun makeCart(cursor: Cursor) = Cart(
        cursor.use {
            val cart = mutableListOf<CartProduct>()

            while (it.moveToNext()) {
                cart.add(
                    makeCartProduct(it)
                )
            }
            cart
        }
    )

    private fun makeCartProduct(it: Cursor) = CartProduct(
        it.getInt(it.getColumnIndexOrThrow(SqlCart.AMOUNT)),
        makeProduct(it)
    )

    private fun makeProduct(it: Cursor) = Product(
        URL(it.getString(it.getColumnIndexOrThrow(SqlProduct.PICTURE))),
        it.getString(it.getColumnIndexOrThrow(SqlProduct.TITLE)),
        it.getInt(it.getColumnIndexOrThrow(SqlProduct.PRICE)),
    )
}
