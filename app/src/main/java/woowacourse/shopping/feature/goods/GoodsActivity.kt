package woowacourse.shopping.feature.goods

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.databinding.ActivityGoodsBinding
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.feature.goods.adapter.GoodsAdapter
import woowacourse.shopping.feature.goodsdetails.GoodsDetailsActivity

class GoodsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGoodsBinding
    private val adapter: GoodsAdapter by lazy {
        GoodsAdapter { goods -> navigate(goods) }
    }
    val viewModel: GoodsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoodsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        binding.rvGoods.adapter = adapter

        viewModel.goods.observe(this) { value ->
            adapter.setItems(value)
        }

        viewModel.loadGoods()
    }

    private fun navigate(goods: Goods) {
        val intent = GoodsDetailsActivity.newIntent(this, goods.toUi())
        startActivity(intent)
    }
}
