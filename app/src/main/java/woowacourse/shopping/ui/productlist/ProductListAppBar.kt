package woowacourse.shopping.ui.productlist

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListAppBar(
    onCartIconClick: () -> Unit,
    count: Int,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Text(
                stringResource(R.string.app_name),
                fontWeight = FontWeight.W500,
                fontSize = 20.sp,
                color = Color(0xffffffff),
            )
        },
        actions = {
            BadgedBox(
                badge = {
                    if (count != 0) {
                        Badge(
                            containerColor = Color(0xff04C09E),
                            modifier = Modifier.offset(x = (-10).dp, y = 10.dp),
                        ) {
                            Text(
                                text = "$count",
                                fontSize = 14.sp,
                            )
                        }
                    }
                },
            ) {
                IconButton(onClick = onCartIconClick) {
                    Icon(
                        painter = painterResource(R.drawable.cart_icon),
                        contentDescription = if (count > 0) stringResource(
                            R.string.cart_description_count,
                            count,
                        ) else stringResource(R.string.cart_description_empty),
                        modifier = Modifier.padding(end = 10.dp),
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF555555),
            titleContentColor = Color.White,
            actionIconContentColor = Color.White,
        ),
        modifier = modifier,
    )
}

@Preview
@Composable
private fun ProductListAppBarPreviewWithCount() {
    ProductListAppBar(
        onCartIconClick = {},
        count = 1,
    )
}

@Preview
@Composable
private fun ProductListAppBarPreview() {
    ProductListAppBar(
        onCartIconClick = {},
        count = 0,
    )
}
