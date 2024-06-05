package woowacourse.shopping.data.local

import woowacourse.shopping.data.local.db.AppDatabase
import woowacourse.shopping.data.remote.OkHttpDataSource
import woowacourse.shopping.domain.Repository

object RepositoryInjector {
    var repository: Repository =
        RepositoryImpl(
            RoomDataSource(AppDatabase.instance.cartProductDao(), AppDatabase.instance.recentProductDao()),
            OkHttpDataSource(AppDatabase.instance.cartProductDao()),
        )
        private set

    fun setInstance(repository: Repository) {
        this.repository = repository
    }
}
