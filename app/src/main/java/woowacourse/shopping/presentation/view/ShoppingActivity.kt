package woowacourse.shopping.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.presentation.view.catalog.CatalogFragment

class ShoppingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShoppingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShoppingBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        setWindowInsets()
        setActionBar()
        if (savedInstanceState == null) navigateToScreen()
    }

    private fun setWindowInsets() {
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setActionBar() {
        supportActionBar
    }

    private fun navigateToScreen() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.shopping_fragment_container, CatalogFragment::class.java, null)
        }
    }
}
