package woowacourse.shopping.feature.productlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListAppBar(
    onCartIconClick: () -> Unit,
    cartQuantities: Int,
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 10.dp),
            ) {
                IconButton(
                    onClick = onCartIconClick,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.cart_icon),
                        contentDescription = stringResource(R.string.cart_description),
                    )
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xff04c09e))
                        .size(20.dp),
                ) {
                    Text(
                        cartQuantities.toString(),
                        color = Color.White,
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        style = LocalTextStyle.current.copy(
                            platformStyle = PlatformTextStyle(includeFontPadding = false),
                            lineHeightStyle = LineHeightStyle(
                                alignment = LineHeightStyle.Alignment.Center,
                                trim = LineHeightStyle.Trim.Both,
                            ),
                        ),
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
private fun ProductListAppBarPreview() {
    ProductListAppBar(
        {},
        cartQuantities = 2,
    )
}
