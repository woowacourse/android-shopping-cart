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
        val productId = getProductId(cartProduct.product)

        val row = ContentValues()
        row.put(SqlCart.TIME, cartProduct.time.toString())
        row.put(SqlCart.PRODUCT_ID, productId)
        row.put(SqlCart.AMOUNT, cartProduct.amount)
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
        cursor.getInt(cursor.getColumnIndexOrThrow(SqlCart.AMOUNT)),
        isChecked = true,
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

    fun deleteCartProduct(cartProduct: CartProduct) {
        val productId = getProductId(cartProduct.product)

        db.delete(SqlCart.name, "${SqlCart.PRODUCT_ID} = ?", arrayOf(productId.toString()))
    }

    fun getTotalAmount(): Int {
        val cursor = db.rawQuery(
            "SELECT SUM(${SqlCart.AMOUNT}) FROM ${SqlCart.name}",
            null
        )
        return cursor.use {
            it.moveToNext()
            it.getInt(0)
        }
    }

    fun selectCartProductByProduct(product: Product): CartProduct? {
        val productId = getProductId(product)
        return selectByProductId(productId)
    }

    private fun selectByProductId(productId: Int): CartProduct? {
        val cursor = db.rawQuery(
            """
                |SELECT * FROM ${SqlCart.name}, ${SqlProduct.name} on ${SqlCart.name}.${SqlCart.PRODUCT_ID} = ${SqlProduct.name}.${SqlProduct.ID}
                |WHERE ${SqlCart.PRODUCT_ID} = $productId
            """.trimMargin(),
            null
        )

        var cartProduct: CartProduct? = null
        return cursor.use {
            while (cursor.moveToNext()) {
                cartProduct = createCartProduct(it)
            }
            cartProduct
        }
    }

    fun updateCartProduct(cartProduct: CartProduct) {
        val productId = getProductId(cartProduct.product)

        val row = ContentValues()
        row.put(SqlCart.TIME, cartProduct.time.toString())
        row.put(SqlCart.PRODUCT_ID, productId)
        row.put(SqlCart.AMOUNT, cartProduct.amount)

        db.update(
            SqlCart.name,
            row,
            "${SqlCart.PRODUCT_ID} = ?",
            arrayOf(productId.toString())
        )
    }

    private fun getProductId(product: Product): Int {
        val productRow: MutableMap<String, Any> = mutableMapOf()
        productRow[SqlProduct.PICTURE] = product.picture.value
        productRow[SqlProduct.TITLE] = product.title
        productRow[SqlProduct.PRICE] = product.price

        return SqlProduct.selectRowId(db, productRow)
    }

    fun getTotalPrice(): Int {
        val cursor = db.rawQuery(
            """
                |SELECT SUM(${SqlProduct.PRICE} * ${SqlCart.AMOUNT}) FROM ${SqlCart.name}, ${SqlProduct.name} 
                |ON ${SqlCart.name}.${SqlCart.PRODUCT_ID} = ${SqlProduct.name}.${SqlProduct.ID}
            """.trimMargin(),
            null
        )

        return cursor.use {
            if (it.moveToNext()) it.getInt(0)
            else 0
        }
    }
}
