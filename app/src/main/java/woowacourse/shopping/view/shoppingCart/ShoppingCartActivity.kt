package woowacourse.shopping.view.shoppingCart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingCartBinding

class ShoppingCartActivity : AppCompatActivity() {
    private val binding: ActivityShoppingCartBinding by lazy {
        ActivityShoppingCartBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.shoppingCartRoot)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.adapter = ShoppingCartProductAdapter(emptyList())
        binding.onClickBackButton = ::finish
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, ShoppingCartActivity::class.java)
    }
}
