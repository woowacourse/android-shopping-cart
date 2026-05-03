package woowacourse.shopping.ui.cart

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartAppBar(onCloseClick: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onCloseClick) {
                Icon(
                    painter = painterResource(R.drawable.left_arrow),
                    contentDescription = stringResource(R.string.left_arrow_description),
                    tint = Color.White,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .size(16.dp),
                )
            }
        },
        title = {
            Text(
                stringResource(R.string.cart_name),
                fontWeight = FontWeight.W900,
                fontSize = 20.sp,
                color = Color(0xffffffff),
                modifier = Modifier.padding(start = 10.dp),
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
