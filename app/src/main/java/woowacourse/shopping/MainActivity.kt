package woowacourse.shopping

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import woowacourse.shopping.ui.component.screen.MainScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AndroidshoppingTheme {
                val networkMonitor = NetworkMonitor(this)
                val isConnected by networkMonitor.isConnected.collectAsStateWithLifecycle(initialValue = true)

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LaunchedEffect(isConnected) {
                        if(!isConnected) {
                            Toast.makeText(this@MainActivity, "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@MainActivity, "연결되었습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    MainScreen(
                        onItemClick = { id ->
                            val intent = Intent(this, ProductDetailActivity::class.java).apply {
                                putExtra("id", id.toString())
                            }
                            startActivity(intent)
                        },
                        onCartClick = {
                            val intent = Intent(this, CartActivity::class.java)
                            startActivity(intent)
                        },
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}
