package woowacourse.shopping.data.datasource.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.database.selectRowId
import woowacourse.shopping.data.database.table.SqlCart
import woowacourse.shopping.data.database.table.SqlProduct
import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartOrdinalProduct
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.URL

class CartDao(private val db: SQLiteDatabase) : CartDataSource {
    override fun insertCartProduct(cartOrdinalProduct: CartOrdinalProduct) {
        val productRow: MutableMap<String, Any> = mutableMapOf()
        productRow[SqlProduct.PICTURE] = cartOrdinalProduct.cartProduct.product.picture.value
        productRow[SqlProduct.TITLE] = cartOrdinalProduct.cartProduct.product.title
        productRow[SqlProduct.PRICE] = cartOrdinalProduct.cartProduct.product.price

        val row = ContentValues()
        row.put(SqlCart.ORDINAL, cartOrdinalProduct.ordinal)
        row.put(SqlCart.PRODUCT_ID, SqlProduct.selectRowId(db, productRow))
        db.insert(SqlCart.name, null, row)
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

    override fun deleteCartProductByOrdinal(ordinal: Int) {
        db.delete(SqlCart.name, "${SqlCart.ORDINAL} = ?", arrayOf(ordinal.toString()))
    }

    private fun makeCart(cursor: Cursor) = Cart(
        cursor.use {
            val cart = mutableListOf<CartOrdinalProduct>()

            while (it.moveToNext()) {
                cart.add(
                    makeCartProduct(it)
                )
            }
            cart
        }
    )

    private fun makeCartProduct(it: Cursor) = CartOrdinalProduct(
        it.getInt(it.getColumnIndexOrThrow(SqlCart.ORDINAL)), CartProduct(0, makeProduct(it))
    )

    private fun makeProduct(it: Cursor) = Product(
        URL(it.getString(it.getColumnIndexOrThrow(SqlProduct.PICTURE))),
        it.getString(it.getColumnIndexOrThrow(SqlProduct.TITLE)),
        it.getInt(it.getColumnIndexOrThrow(SqlProduct.PRICE)),
    )
}
