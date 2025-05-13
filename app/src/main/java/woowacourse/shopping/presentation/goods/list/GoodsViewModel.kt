package woowacourse.shopping.presentation.goods.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.util.DummyData

class GoodsViewModel : ViewModel() {
    private val _goods: MutableLiveData<List<Goods>> = MutableLiveData(DummyData.GOODS)

    val goods: LiveData<List<Goods>>
        get() = _goods
}
