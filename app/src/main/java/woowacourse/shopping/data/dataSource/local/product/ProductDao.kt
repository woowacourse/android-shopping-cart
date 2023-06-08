package woowacourse.shopping.data.dataSource.local.product

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import woowacourse.shopping.data.db.WoowaShoppingContract.Product.TABLE_COLUMN_ITEM_IMAGE
import woowacourse.shopping.data.db.WoowaShoppingContract.Product.TABLE_COLUMN_NAME
import woowacourse.shopping.data.db.WoowaShoppingContract.Product.TABLE_COLUMN_PRICE
import woowacourse.shopping.data.db.WoowaShoppingContract.Product.TABLE_NAME
import woowacourse.shopping.data.db.WoowaShoppingDbHelper
import woowacourse.shopping.data.entity.ProductEntity

class ProductDao(context: Context) : ProductDataSource {
    private val shoppingDb by lazy { WoowaShoppingDbHelper(context).readableDatabase }

    override fun getProductEntity(id: Long): ProductEntity? {
        val query: String = "SELECT * FROM $TABLE_NAME WHERE ${BaseColumns._ID} = $id"
        val cursor: Cursor = shoppingDb.rawQuery(query, null)
        if (!cursor.moveToFirst()) return null
        return readProduct(cursor)
    }

    override fun getProductEntities(unit: Int, lastIndex: Int): List<ProductEntity> {
        val query: String = "SELECT * FROM $TABLE_NAME LIMIT $unit OFFSET $lastIndex"
        val cursor: Cursor = shoppingDb.rawQuery(query, null)
        val itemContainer = mutableListOf<ProductEntity>()
        while (cursor.moveToNext()) {
            itemContainer.add(readProduct(cursor))
        }
        return itemContainer
    }

    override fun addProductEntity(
        name: String,
        price: Int,
        itemImage: String,
    ): Long {
        val data = ContentValues()
        data.put(TABLE_COLUMN_NAME, name)
        data.put(TABLE_COLUMN_PRICE, price)
        data.put(TABLE_COLUMN_ITEM_IMAGE, itemImage)
        return shoppingDb.insert(TABLE_NAME, null, data)
    }

    private fun readProduct(cursor: Cursor): ProductEntity {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_NAME))
        val itemImage = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_ITEM_IMAGE))
        val price = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_PRICE))
        return ProductEntity(id, name, itemImage, price)
    }
}
