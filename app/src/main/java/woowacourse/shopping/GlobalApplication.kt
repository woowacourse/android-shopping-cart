package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.ClothesStoreDatabase
import woowacourse.shopping.providers.ClothesStoreDatabaseProvider
import woowacourse.shopping.providers.ThreadProvider

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ClothesStoreDatabaseProvider.init(ClothesStoreDatabase.getInstance(this))
    }

    override fun onTerminate() {
        super.onTerminate()
        ClothesStoreDatabaseProvider.terminate()
        ThreadProvider.terminate()
    }
}
