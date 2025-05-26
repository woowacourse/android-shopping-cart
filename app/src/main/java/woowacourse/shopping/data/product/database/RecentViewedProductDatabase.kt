package woowacourse.shopping.data.product.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.shopping.data.product.LocalDateConverters
import woowacourse.shopping.data.product.dao.RecentViewedProductDao
import woowacourse.shopping.data.product.entity.RecentViewedProductEntity

@Database(entities = [RecentViewedProductEntity::class], version = 1)
@TypeConverters(LocalDateConverters::class)
abstract class RecentViewedProductDatabase : RoomDatabase() {
    abstract fun dao(): RecentViewedProductDao
}
