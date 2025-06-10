package woowacourse.shopping.data.product.dataSource

import android.content.Context
import androidx.room.Room
import woowacourse.shopping.data.product.dao.ProductDao
import woowacourse.shopping.data.product.database.ProductDatabase
import woowacourse.shopping.data.product.entity.ProductEntity
import kotlin.concurrent.thread

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
        insertAllIfEmpty()
    }

    private fun insertAllIfEmpty() {
        thread {
            if (dao.loadAll().isEmpty()) {
                dao.insertAll(
                    listOf(
                        ProductEntity(id = 1, name = "럭키", price = 4000),
                        ProductEntity(id = 2, name = "아이다", price = 700),
                        ProductEntity(id = 3, name = "설백", price = 1_000),
                        ProductEntity(id = 4, name = "줌마", price = 1_000),
                        ProductEntity(id = 5, name = "잭슨", price = 20_000),
                        ProductEntity(id = 6, name = "곰도로스", price = 300),
                        ProductEntity(id = 7, name = "봉추", price = 3_800),
                        ProductEntity(id = 8, name = "비앙카", price = 36_000),
                        ProductEntity(id = 9, name = "비앙카1", price = 36_000),
                        ProductEntity(id = 10, name = "비앙카2", price = 36_000),
                        ProductEntity(id = 11, name = "비앙카3", price = 36_000),
                        ProductEntity(id = 12, name = "비앙카4", price = 36_000),
                        ProductEntity(id = 13, name = "비앙카5", price = 36_000),
                        ProductEntity(id = 14, name = "비앙카6", price = 36_000),
                        ProductEntity(id = 15, name = "비앙카7", price = 36_000),
                        ProductEntity(id = 16, name = "비앙카8", price = 36_000),
                        ProductEntity(id = 17, name = "비앙카9", price = 36_000),
                        ProductEntity(id = 18, name = "비앙카10", price = 36_000),
                        ProductEntity(id = 19, name = "비앙카11", price = 36_000),
                        ProductEntity(id = 20, name = "비앙카12", price = 36_000),
                        ProductEntity(id = 21, name = "비앙카13", price = 36_000),
                        ProductEntity(id = 22, name = "비앙카14", price = 36_000),
                        ProductEntity(id = 23, name = "비앙카15", price = 36_000),
                        ProductEntity(id = 24, name = "비앙카16", price = 36_000),
                        ProductEntity(id = 25, name = "비앙카17", price = 36_000),
                    ),
                )
            }
        }
    }

    override fun load(): List<ProductEntity> = dao.loadAll()

    override fun getById(id: Long): ProductEntity? = dao.load(id)
}
