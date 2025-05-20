package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.ClothesStoreDatabase
import woowacourse.shopping.providers.ClothesStoreDatabaseProvider

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ClothesStoreDatabaseProvider.init(ClothesStoreDatabase.getInstance(this))
    }
}
