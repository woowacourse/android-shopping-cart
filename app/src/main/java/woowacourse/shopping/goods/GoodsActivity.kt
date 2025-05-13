package woowacourse.shopping.goods

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityGoodsBinding
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.model.Goods.Companion.dummyGoods
import woowacourse.shopping.goods.adapter.GoodsAdapter

class GoodsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGoodsBinding
    private lateinit var adapter: GoodsAdapter
    private var goods: List<Goods> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoodsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = GoodsAdapter(dummyGoods)
        binding.rvGoods.adapter = adapter
    }
}
