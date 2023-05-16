package woowacourse.shopping.data.database.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.database.selectRowId
import woowacourse.shopping.data.database.table.SqlCart
import woowacourse.shopping.data.database.table.SqlProduct
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.URL
import java.time.LocalDateTime

class CartDao(private val db: SQLiteDatabase) {
    fun insertCartProduct(cartProduct: CartProduct) {
        val productRow: MutableMap<String, Any> = mutableMapOf()
        productRow[SqlProduct.PICTURE] = cartProduct.product.picture.value
        productRow[SqlProduct.TITLE] = cartProduct.product.title
        productRow[SqlProduct.PRICE] = cartProduct.product.price

        val row = ContentValues()
        row.put(SqlCart.TIME, cartProduct.time.toString())
        row.put(SqlCart.PRODUCT_ID, SqlProduct.selectRowId(db, productRow))
        db.insert(SqlCart.name, null, row)
    }

    fun selectAll(): Cart {
        val cursor = db.rawQuery(
            "SELECT * FROM ${SqlCart.name}, ${SqlProduct.name} on ${SqlCart.name}.${SqlCart.PRODUCT_ID} = ${SqlProduct.name}.${SqlProduct.ID} " +
                "ORDER BY ${SqlCart.TIME} ASC",
            null
        )
        return createCart(cursor)
    }

    private fun createCart(cursor: Cursor) = Cart(
        cursor.use {
            val cart = mutableListOf<CartProduct>()
            while (it.moveToNext()) {
                cart.add(createCartProduct(it))
            }
            cart
        }
    )

    private fun createCartProduct(cursor: Cursor) = CartProduct(
        LocalDateTime.parse(cursor.getString(cursor.getColumnIndexOrThrow(SqlCart.TIME))),
        Product(
            URL(cursor.getString(cursor.getColumnIndexOrThrow(SqlProduct.PICTURE))),
            cursor.getString(cursor.getColumnIndexOrThrow(SqlProduct.TITLE)),
            cursor.getInt(cursor.getColumnIndexOrThrow(SqlProduct.PRICE)),
        )
    )

    fun selectAllCount(): Int {
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM ${SqlCart.name}",
            null
        )
        return cursor.use {
            it.moveToNext()
            it.getInt(0)
        }
    }

    fun selectPage(page: Int, sizePerPage: Int): Cart {
        val cursor = db.rawQuery(
            "SELECT * FROM ${SqlCart.name}, ${SqlProduct.name} on ${SqlCart.name}.${SqlCart.PRODUCT_ID} = ${SqlProduct.name}.${SqlProduct.ID} " +
                "ORDER BY ${SqlCart.TIME} ASC " +
                "LIMIT ${page * sizePerPage}, $sizePerPage",
            null
        )
        return createCart(cursor)
    }

    fun deleteCartProductByTime(time: LocalDateTime) {
        db.delete(SqlCart.name, "${SqlCart.TIME} = ?", arrayOf(time.toString()))
    }
}
