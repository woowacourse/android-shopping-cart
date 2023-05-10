package woowacourse.shopping.common.data.database.dao

import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.common.data.database.insert
import woowacourse.shopping.common.data.database.table.SqlProduct
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.URL

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

    fun selectAll(): Products {
        val cursor = db.use {
            it.rawQuery(
                "SELECT * FROM ${SqlProduct.name}", null
            )
        }
        return Products(
            cursor.use {
                val products = mutableListOf<Product>()
                while (it.moveToNext()) products.add(
                    Product(
                        URL(it.getString(it.getColumnIndexOrThrow(SqlProduct.PICTURE))),
                        it.getString(it.getColumnIndexOrThrow(SqlProduct.TITLE)),
                        it.getInt(it.getColumnIndexOrThrow(SqlProduct.PRICE)),
                    )
                )

                products
            }
        )
    }
}
