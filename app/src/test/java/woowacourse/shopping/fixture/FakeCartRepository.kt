package woowacourse.shopping.fixture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.domain.model.Goods

class FakeCartRepository : CartRepository {
    private val goodsList = mutableListOf<Goods>()
    private val goodsLiveData = MutableLiveData<List<Goods>>()
    private val sizeLiveData = MutableLiveData<Int>()

    init {
        updateLiveData()
    }

    private fun updateLiveData() {
        goodsLiveData.value = goodsList.toList()
        sizeLiveData.value = goodsList.size
    }

    override fun getAll(): LiveData<List<Goods>> = goodsLiveData

    override fun insert(goods: Goods) {
        goodsList.add(goods)
        updateLiveData()
    }

    override fun delete(goods: Goods) {
        goodsList.removeIf { it.id == goods.id }
        updateLiveData()
    }

    override fun getPage(
        limit: Int,
        offset: Int,
    ): LiveData<List<Goods>> {
        val page = goodsList.drop(offset).take(limit)
        return MutableLiveData(page)
    }

    override fun getAllItemsSize(): LiveData<Int> = sizeLiveData
}
