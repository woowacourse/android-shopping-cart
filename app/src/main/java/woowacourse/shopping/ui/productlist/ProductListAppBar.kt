package woowacourse.shopping.ui.productlist

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
@Preview
@Composable
fun ProductListAppBar(modifier: Modifier = Modifier) {
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
            Icon(
                painter = painterResource(R.drawable.cart_icon),
                contentDescription = stringResource(R.string.cart_description),
                modifier = Modifier.padding(end = 10.dp),
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF555555),
            titleContentColor = Color.White,
            actionIconContentColor = Color.White,
        ),
        modifier = modifier,
    )
}
