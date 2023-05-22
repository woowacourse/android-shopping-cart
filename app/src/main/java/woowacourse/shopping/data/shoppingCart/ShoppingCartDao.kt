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

    override fun getShoppingCartByPage(unit: Int, pageNumber: Int): List<ProductInCartEntity> {
        val offset = unit * (pageNumber - 1)
        val query = "SELECT * FROM $TABLE_NAME LIMIT $unit OFFSET $offset"
        val cursor = shoppingDb.rawQuery(query, null)
        val itemContainer = mutableListOf<ProductInCartEntity>()
        while (cursor.moveToNext()) {
            itemContainer.add(readProductInCart(cursor))
        }
        return itemContainer
    }

    override fun getShoppingCart(): List<ProductInCartEntity> {
        val query = "SELECT * FROM $TABLE_NAME"
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

    override fun addProductInShoppingCart(productId: Long, productQuantity: Int): Long {
        val existingQuantity = getQuantityFromShoppingCart(productId)
        val updatedQuantity = existingQuantity + productQuantity

        val contentValues = ContentValues().apply {
            put(TABLE_COLUMN_QUANTITY, updatedQuantity)
        }

        val whereClause = "$TABLE_COLUMN_PRODUCT_ID = ?"
        val whereArgs = arrayOf(productId.toString())
        val rowsAffected = shoppingDb.update(TABLE_NAME, contentValues, whereClause, whereArgs)

        if (rowsAffected == 0) {
            contentValues.apply {
                put(TABLE_COLUMN_PRODUCT_ID, productId)
            }
            return shoppingDb.insert(TABLE_NAME, null, contentValues)
        }
        return rowsAffected.toLong()
    }

    private fun getQuantityFromShoppingCart(productId: Long): Int {
        val whereClause = "$TABLE_COLUMN_PRODUCT_ID = ?"
        val whereArgs = arrayOf(productId.toString())

        val query = "SELECT $TABLE_COLUMN_QUANTITY FROM $TABLE_NAME WHERE $whereClause"
        val cursor = shoppingDb.rawQuery(query, whereArgs)

        var quantity = 0
        if (cursor.moveToFirst()) {
            val quantityIndex = cursor.getColumnIndex(TABLE_COLUMN_QUANTITY)
            quantity = cursor.getInt(quantityIndex)
        }

        cursor.close()
        return quantity
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
