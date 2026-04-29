package woowacourse.shopping.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.ui.theme.topAppBarColor
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
fun CartScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current as? Activity
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Image(
                        painter = painterResource(R.drawable.arrow_left_alt_24px),
                        contentDescription = "leftArrowIcon",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                context?.finish()
                            },
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                },
                title = {
                    Text(
                        text = "Cart",
                        fontWeight = FontWeight.W500,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = topAppBarColor,
                        titleContentColor = Color.White,
                    ),
            )
        },
        containerColor = Color.White,
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
            LazyColumn() {
                items(CartRepository.getProducts()) { productAndCount ->
                    CartItem(
                        productAndCount = productAndCount
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun CartScreenPreview() {
    CartScreen()
}