package woowacourse.shopping.data.shoppingCart

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import woowacourse.shopping.data.WoowaShoppingContract.ShoppingCart.TABLE_COLUMN_PRODUCT_ID
import woowacourse.shopping.data.WoowaShoppingContract.ShoppingCart.TABLE_COLUMN_QUANTITY
import woowacourse.shopping.data.WoowaShoppingContract.ShoppingCart.TABLE_NAME
import woowacourse.shopping.data.WoowaShoppingDbHelper

class ShoppingCartDao(context: Context) : ShoppingCartDataSource {
    private val shoppingDb by lazy { WoowaShoppingDbHelper(context).readableDatabase }

    override fun getProductsInShoppingCart(unit: Int, pageNumber: Int): List<ProductInCartEntity> {
        val offset = unit * (pageNumber - 1)
        val query = "SELECT * FROM $TABLE_NAME LIMIT $unit OFFSET $offset"
        val cursor = shoppingDb.rawQuery(query, null)
        val itemContainer = mutableListOf<ProductInCartEntity>()
        while (cursor.moveToNext()) {
            itemContainer.add(readProductInCart(cursor))
        }
        return itemContainer
    }

    override fun deleteProductInShoppingCart(productId: Long): Boolean {
        val selection = "$TABLE_COLUMN_PRODUCT_ID = ?"
        val selectionArgs = arrayOf("$productId")
        val deletedRows = shoppingDb.delete(TABLE_NAME, selection, selectionArgs)
        if (deletedRows == 0) return false
        return true
    }

    override fun addProductInShoppingCart(productId: Long, quantity: Int): Long {
        val data = ContentValues()
        data.put(TABLE_COLUMN_PRODUCT_ID, productId)
        data.put(TABLE_COLUMN_QUANTITY, quantity)
        return shoppingDb.insert(TABLE_NAME, null, data)
    }

    override fun getShoppingCartSize(): Int {
        val query = "SELECT COUNT(*) FROM $TABLE_NAME"
        val cursor = shoppingDb.rawQuery(query, null)
        var shoppingCartSize = 0
        if (cursor.moveToFirst()) {
            shoppingCartSize = cursor.getInt(0)
        }
        return shoppingCartSize
    }

    private fun readProductInCart(cursor: Cursor): ProductInCartEntity {
        val productId = cursor.getLong(cursor.getColumnIndexOrThrow(TABLE_COLUMN_PRODUCT_ID))
        val productQuantity =
            cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_QUANTITY))
        return ProductInCartEntity(productId, productQuantity)
    }
}
