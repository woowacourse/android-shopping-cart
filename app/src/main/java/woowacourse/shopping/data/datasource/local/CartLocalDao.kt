package woowacourse.shopping.data.datasource.local

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.database.ShoppingDBOpenHelper
import woowacourse.shopping.data.database.table.SqlCart
import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Shop

class CartLocalDao(context: Context) : CartDataSource {
    private val db: SQLiteDatabase = ShoppingDBOpenHelper(context).writableDatabase

    override fun addCartProduct(cartProduct: CartProduct) {
        insertOrUpdateCartProduct(cartProduct.product.id, cartProduct.amount)
    }

    override fun plusCartProduct(product: Product) {
        insertOrUpdateCartProduct(product.id, 1)
    }

    override fun minusCartProduct(product: Product) {
        updateOrDeleteCartProduct(product.id)
    }

    override fun deleteCartProduct(product: Product) {
        deleteCartProduct(product.id)
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

    override fun selectAll(products: List<Product>): Shop {
        val cursor = db.rawQuery(
            "SELECT * FROM ${SqlCart.name}", null
        )
        return makeShop(cursor, products)
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

    private fun makeShop(cursor: Cursor, products: List<Product>): Shop {
        val cartProducts = mutableListOf<CartProduct>()
        while (cursor.moveToNext()) {
            cartProducts.add(
                CartProduct(
                    cursor.getInt(cursor.getColumnIndexOrThrow(SqlCart.AMOUNT)),
                    products.first { it.id == cursor.getInt(cursor.getColumnIndexOrThrow(SqlCart.PRODUCT_ID)) }
                )
            )
        }
        return Shop(cartProducts)
    }
}
