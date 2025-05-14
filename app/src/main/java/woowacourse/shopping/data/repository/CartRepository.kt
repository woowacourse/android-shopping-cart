package woowacourse.shopping.data.repository

import androidx.lifecycle.LiveData
import woowacourse.shopping.data.CartEntity

interface CartRepository {
    fun getAll(): LiveData<List<CartEntity>>
}
