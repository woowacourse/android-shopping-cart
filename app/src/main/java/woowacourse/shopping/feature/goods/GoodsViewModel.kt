package woowacourse.shopping.feature.goods

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Goods

class GoodsViewModel : ViewModel() {
    private val _goods = MutableLiveData<List<Goods>>()
    val goods: LiveData<List<Goods>> get() = _goods

    fun loadGoods() {
        _goods.value = Goods.dummyGoods
    }
}
