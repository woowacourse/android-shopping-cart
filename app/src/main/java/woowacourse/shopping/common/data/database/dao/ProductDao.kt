package woowacourse.shopping.common.data.database.dao

import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.common.data.database.insert
import woowacourse.shopping.common.data.database.table.SqlProduct
import woowacourse.shopping.common.model.ProductModel

class ProductDao(private val db: SQLiteDatabase) {
    fun insertProduct(product: ProductModel): Long {
        return db.use {
            val row: MutableMap<String, Any> = mutableMapOf()
            row[SqlProduct.PICTURE] = product.picture
            row[SqlProduct.TITLE] = product.title
            row[SqlProduct.PRICE] = product.price
            SqlProduct.insert(it, row)
        }
    }
}
