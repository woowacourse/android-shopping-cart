package woowacourse.shopping.presentation.goods

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityGoodsBinding

class GoodsActivity : AppCompatActivity() {
    private val binding: ActivityGoodsBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_goods)
    }
    private val viewModel: GoodsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.rvGoodsList.apply {
            adapter = GoodsAdapter(viewModel.goods.value ?: listOf())
            layoutManager = GridLayoutManager(this@GoodsActivity, 2)
        }
    }
}
