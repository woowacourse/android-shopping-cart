package woowacourse.shopping.view.data

import android.content.Context
import androidx.room.Room
import woowacourse.shopping.data.product.entity.ProductEntity
import woowacourse.shopping.data.product.storage.ProductsDataSource

object LocalProductsDataSource : ProductsDataSource {
    private lateinit var dao: ProductDao

    fun init(applicationContext: Context) {
        val db =
            Room
                .databaseBuilder(
                    applicationContext,
                    ProductDatabase::class.java,
                    "products",
                ).build()

        dao = db.dao()
    }

    override fun load(): List<ProductEntity> = dao.load()
}
