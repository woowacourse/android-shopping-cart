package woowacourse.shopping.feature.productdetail

import android.R.attr.name
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R
import woowacourse.shopping.feature.common.state.ProductUiModel
import woowacourse.shopping.feature.productdetail.viewmodel.ProductDetailLoadingState

@Composable
fun RecentProductLetter(
    loadingState: ProductDetailLoadingState,
    onClickRecentProduct: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (loadingState) {
        ProductDetailLoadingState.None -> Box {}
        ProductDetailLoadingState.Loading,
        -> Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        is ProductDetailLoadingState.Error,
        -> Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(vertical = 5.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = stringResource(R.string.product_detail_error_description),
                tint = Color(0xFFFF9800),
                modifier = Modifier.size(32.dp),
            )
            Text(
                text = stringResource(R.string.product_detail_entry_error),
                fontWeight = FontWeight.W500,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp),
            )
        }

        is ProductDetailLoadingState.Success -> Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(18.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, Color(0xffaaaaaa), shape = RoundedCornerShape(8.dp))
                .clickable(onClick = { onClickRecentProduct(loadingState.product.id) }),
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 16.dp),
            ) {
                Text(
                    "마지막으로 본 상품",
                    fontWeight = FontWeight.W700,
                    fontSize = 12.sp,
                    color = Color(0xff04c09e),
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    loadingState.product.name,
                    fontWeight = FontWeight.W400,
                    fontSize = 18.sp,
                    color = Color(0xff555555),
                )
            }
        }
    }
}

@Preview
@Composable
fun RecentProductLetterPreview(modifier: Modifier = Modifier) {
    RecentProductLetter(
        loadingState = ProductDetailLoadingState.Success(
            ProductUiModel(
                name = "asdqwe",
                price = 1000,
                imageUrl = "",
                id = "",
                quantity = 0,
            ),
        ),
        onClickRecentProduct = {},
    )
}

@Preview
@Composable
fun RecentProductErrorLetterPreview(modifier: Modifier = Modifier) {
    RecentProductLetter(
        loadingState = ProductDetailLoadingState.Error(
            "asd",
        ),
        onClickRecentProduct = {},
    )
}
