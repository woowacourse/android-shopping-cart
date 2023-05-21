package woowacourse.shopping.data.datasource.local

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.database.selectRowId
import woowacourse.shopping.data.database.table.SqlCart
import woowacourse.shopping.data.database.table.SqlProduct
import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Shop
import woowacourse.shopping.domain.URL

class CartLocalDao(private val db: SQLiteDatabase) : CartDataSource {
    override fun addCartProduct(cartProduct: CartProduct) {
        val productRow: MutableMap<String, Any> = mutableMapOf()
        productRow[SqlProduct.PICTURE] = cartProduct.product.picture.value
        productRow[SqlProduct.TITLE] = cartProduct.product.title
        productRow[SqlProduct.PRICE] = cartProduct.product.price

        val productId = SqlProduct.selectRowId(db, productRow)
        insertOrUpdateCartProduct(productId, cartProduct.amount)
    }

    override fun plusCartProduct(product: Product) {
        val productRow: MutableMap<String, Any> = mutableMapOf()
        productRow[SqlProduct.PICTURE] = product.picture.value
        productRow[SqlProduct.TITLE] = product.title
        productRow[SqlProduct.PRICE] = product.price

        val productId = SqlProduct.selectRowId(db, productRow)
        insertOrUpdateCartProduct(productId, 1)
    }

    override fun minusCartProduct(product: Product) {
        val productRow: MutableMap<String, Any> = mutableMapOf()
        productRow[SqlProduct.PICTURE] = product.picture.value
        productRow[SqlProduct.TITLE] = product.title
        productRow[SqlProduct.PRICE] = product.price

        val productId = SqlProduct.selectRowId(db, productRow)
        updateOrDeleteCartProduct(productId)
    }

    override fun deleteCartProduct(product: Product) {
        val productRow: MutableMap<String, Any> = mutableMapOf()
        productRow[SqlProduct.PICTURE] = product.picture.value
        productRow[SqlProduct.TITLE] = product.title
        productRow[SqlProduct.PRICE] = product.price

        val productId = SqlProduct.selectRowId(db, productRow)
        deleteCartProduct(productId)
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

    override fun selectAll(): Shop {
        val cursor = db.rawQuery(
            "SELECT * FROM ${SqlCart.name}, ${SqlProduct.name} on ${SqlCart.name}.${SqlCart.PRODUCT_ID} = ${SqlProduct.name}.${SqlProduct.ID}",
            null
        )
        return makeCart(cursor)
    }

    override fun selectPage(page: Int, countPerPage: Int): Shop {
        val cursor = db.rawQuery(
            "SELECT * FROM ${SqlCart.name}, ${SqlProduct.name} on ${SqlCart.name}.${SqlCart.PRODUCT_ID} = ${SqlProduct.name}.${SqlProduct.ID} LIMIT ${page * countPerPage}, $countPerPage",
            null
        )
        return makeCart(cursor)
    }

    private fun insertOrUpdateCartProduct(productId: Int, amount: Int) {
        val query =
            "INSERT INTO ${SqlCart.name} (${SqlCart.AMOUNT}, ${SqlCart.PRODUCT_ID}) VALUES ($amount, $productId) " + "ON CONFLICT(${SqlCart.PRODUCT_ID}) DO UPDATE SET ${SqlCart.AMOUNT} = ${SqlCart.AMOUNT} + $amount"

        db.execSQL(query)
    }

    private fun updateOrDeleteCartProduct(productId: Int) {
        db.execSQL("UPDATE ${SqlCart.name} SET ${SqlCart.AMOUNT} = CASE WHEN ${SqlCart.AMOUNT} > 1 THEN ${SqlCart.AMOUNT} - 1 ELSE 0 END WHERE ${SqlCart.PRODUCT_ID} = $productId")
        db.execSQL("DELETE FROM ${SqlCart.name} WHERE ${SqlCart.PRODUCT_ID} = $productId AND ${SqlCart.AMOUNT} = 0")
    }

    private fun deleteCartProduct(productId: Int) {
        db.execSQL("DELETE FROM ${SqlCart.name} WHERE ${SqlCart.PRODUCT_ID} = $productId")
    }

    private fun makeCart(cursor: Cursor) = Shop(
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
