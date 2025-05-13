package woowacourse.shopping.feature.goods

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.databinding.ActivityGoodsBinding
import woowacourse.shopping.feature.goods.adapter.GoodsAdapter

class GoodsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGoodsBinding
    val viewModel: GoodsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoodsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        viewModel.goods.observe(this) { value ->
            binding.rvGoods.adapter = GoodsAdapter(value)
        }

        viewModel.loadGoods()
    }
}
