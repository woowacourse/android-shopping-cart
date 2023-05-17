package woowacourse.shopping.data.database.dao.basket

import android.annotation.SuppressLint
import android.content.ContentValues
import android.provider.BaseColumns
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.database.contract.BasketContract
import woowacourse.shopping.data.model.DataPageNumber
import woowacourse.shopping.data.model.DataPrice
import woowacourse.shopping.data.model.DataProduct
import woowacourse.shopping.util.extension.safeSubList

class BasketDaoImpl(private val database: ShoppingDatabase) : BasketDao {
    @SuppressLint("Range")
    override fun getPartially(page: DataPageNumber): List<DataProduct> {
        val products = mutableListOf<DataProduct>()

        val db = database.writableDatabase
        val cursor = db.rawQuery(GET_ALL_QUERY, null)

        while (cursor.moveToNext()) {
            val id: Int = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            val name: String =
                cursor.getString(cursor.getColumnIndex(BasketContract.COLUMN_NAME))
            val price: DataPrice =
                DataPrice(cursor.getInt(cursor.getColumnIndex(BasketContract.COLUMN_PRICE)))
            val imageUrl: String =
                cursor.getString(cursor.getColumnIndex(BasketContract.COLUMN_IMAGE_URL))
            val selectedCount: Int =
                cursor.getInt(cursor.getColumnIndex(BasketContract.COLUMN_IMAGE_URL))
            products.add(DataProduct(id, name, price, imageUrl, selectedCount))
        }
        cursor.close()

        return products.safeSubList(page.start, page.end)
    }

    override fun add(product: DataProduct) {
        val contentValues = ContentValues().apply {
            put(BasketContract.COLUMN_NAME, product.name)
            put(BasketContract.COLUMN_PRICE, product.price.value)
            put(BasketContract.COLUMN_IMAGE_URL, product.imageUrl)
            put(BasketContract.COLUMN_CREATED, System.currentTimeMillis())
            put(BasketContract.COLUMN_COUNT, product.selectedCount)
        }

        database.writableDatabase.insert(BasketContract.TABLE_NAME, null, contentValues)
    }

    override fun remove(product: DataProduct) {
        database.writableDatabase.delete(
            BasketContract.TABLE_NAME,
            "${BaseColumns._ID} = ?",
            arrayOf(product.id.toString())
        )
    }

    companion object {
        private val GET_ALL_QUERY = """
            SELECT * FROM ${BasketContract.TABLE_NAME}  
        """.trimIndent()
    }
}
