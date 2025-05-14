package woowacourse.shopping.feature.goodsdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Goods

class GoodsDetailViewModel : ViewModel() {
    private val _goods = MutableLiveData<List<Goods>>()
    val goods: LiveData<List<Goods>> get() = _goods

    fun loadGoods() {
    }
}
