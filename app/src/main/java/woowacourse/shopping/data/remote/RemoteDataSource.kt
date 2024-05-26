package woowacourse.shopping.data.remote

import com.google.gson.reflect.TypeToken
import okhttp3.Response
import woowacourse.shopping.data.local.entity.CartProductEntity

interface RemoteDataSource {

    fun findProductByPagingWithMock(
        offset: Int,
        pageSize: Int,
    ): List<CartProductEntity>
}