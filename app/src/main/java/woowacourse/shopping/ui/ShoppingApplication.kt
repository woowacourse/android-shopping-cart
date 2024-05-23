package woowacourse.shopping.ui

import android.app.Application
import woowacourse.shopping.data.ShoppingDatabase

class ShoppingApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        ShoppingDatabase.init(this)
    }
}
