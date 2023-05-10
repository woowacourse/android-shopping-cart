package woowacourse.shopping.data.database.dao.product

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import woowacourse.shopping.data.database.contract.ProductContract
import woowacourse.shopping.data.database.contract.ProductContract.TABLE_NAME
import woowacourse.shopping.data.model.DataPrice
import woowacourse.shopping.data.model.DataProduct

class ProductDaoImpl(private val database: SQLiteOpenHelper) : ProductDao {
    @SuppressLint("Range") // 컬럼 index가 -1이 나올 수도 있는 상황을 무시하는 Annotation
    override fun getAll(): List<DataProduct> {
        val products = mutableListOf<DataProduct>()
        val cursor = database.writableDatabase.rawQuery(GET_ALL_QUERY, null)
        while (cursor.moveToNext()) {
            val id: Int = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            val name: String = cursor.getString(cursor.getColumnIndex(ProductContract.COLUMN_NAME))
            val price: DataPrice =
                DataPrice(cursor.getInt(cursor.getColumnIndex(ProductContract.COLUMN_PRICE)))
            val imageUrl: String =
                cursor.getString(cursor.getColumnIndex(ProductContract.COLUMN_IMAGE_URL))
            products.add(DataProduct(id, name, price, imageUrl))
        }

        return products
    }

    // 데이터 최초 초기화용 (테스트용 로직)
    fun add(product: DataProduct) {
        val contentValues = ContentValues().apply {
            // put(BaseColumns._ID, product.id)
            put(ProductContract.COLUMN_NAME, product.name)
            put(ProductContract.COLUMN_PRICE, product.price.value)
            put(ProductContract.COLUMN_IMAGE_URL, product.imageUrl)
        }

        // No Add운동 / 쿼리 짤 필요 없이 SQLiteDatabase에서 제공
        // ContentValue를 통해 일치하는 Column명에 데이터 알아서 넣어줌.
        database.writableDatabase.insert(TABLE_NAME, null, contentValues)
    }

    companion object {
        private const val GET_ALL_QUERY = """
            SELECT * FROM $TABLE_NAME ORDER BY ${BaseColumns._ID}
        """
    }
}
