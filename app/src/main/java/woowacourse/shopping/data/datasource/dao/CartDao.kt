package woowacourse.shopping.data.datasource.dao

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.database.selectRowId
import woowacourse.shopping.data.database.table.SqlCartProduct
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
            "SELECT COUNT(*) FROM ${SqlCartProduct.name}", null
        )
        return cursor.use {
            it.moveToNext()
            it.getInt(0)
        }
    }

    override fun selectAll(): Cart {
        val cursor = db.rawQuery(
            "SELECT * FROM ${SqlCartProduct.name}, ${SqlProduct.name} on ${SqlCartProduct.name}.${SqlCartProduct.PRODUCT_ID} = ${SqlProduct.name}.${SqlProduct.ID}",
            null
        )
        return makeCart(cursor)
    }

    override fun selectPage(page: Int, countPerPage: Int): Cart {
        val cursor = db.rawQuery(
            "SELECT * FROM ${SqlCartProduct.name}, ${SqlProduct.name} on ${SqlCartProduct.name}.${SqlCartProduct.PRODUCT_ID} = ${SqlProduct.name}.${SqlProduct.ID} LIMIT ${page * countPerPage}, $countPerPage",
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
            "INSERT INTO ${SqlCartProduct.name} (${SqlCartProduct.AMOUNT}, ${SqlCartProduct.PRODUCT_ID}) VALUES (1, $productId) " + "ON CONFLICT(${SqlCartProduct.PRODUCT_ID}) DO UPDATE SET ${SqlCartProduct.AMOUNT} = ${SqlCartProduct.AMOUNT} + 1"

        db.execSQL(query)
    }

    private fun updateOrDeleteCartProduct(productId: Int) {
        db.execSQL("UPDATE ${SqlCartProduct.name} SET ${SqlCartProduct.AMOUNT} = CASE WHEN ${SqlCartProduct.AMOUNT} > 1 THEN ${SqlCartProduct.AMOUNT} - 1 ELSE 0 END WHERE ${SqlCartProduct.PRODUCT_ID} = $productId")
        db.execSQL("DELETE FROM ${SqlCartProduct.name} WHERE ${SqlCartProduct.PRODUCT_ID} = $productId AND ${SqlCartProduct.AMOUNT} = 0")
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
        it.getColumnIndexOrThrow(SqlCartProduct.AMOUNT),
        makeProduct(it)
    )

    private fun makeProduct(it: Cursor) = Product(
        URL(it.getString(it.getColumnIndexOrThrow(SqlProduct.PICTURE))),
        it.getString(it.getColumnIndexOrThrow(SqlProduct.TITLE)),
        it.getInt(it.getColumnIndexOrThrow(SqlProduct.PRICE)),
    )
}
