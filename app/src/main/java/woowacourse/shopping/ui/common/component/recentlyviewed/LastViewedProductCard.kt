package woowacourse.shopping.ui.common.component.recentlyviewed

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.R
import woowacourse.shopping.ui.ShoppingTypography
import woowacourse.shopping.ui.theme.ShoppingColors

@Composable
fun LastViewedProductCard(
    name: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .size(height = 80.dp, width = 324.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(5.dp),
                ).border(
                    width = 1.dp,
                    color = ShoppingColors.Gray2,
                    shape = RoundedCornerShape(5.dp),
                ).clickable(onClick = onClick)
                .padding(horizontal = 18.dp, vertical = 16.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Column {
            Text(
                text = stringResource(R.string.last_viewed_product_label),
                color = ShoppingColors.BrandGreen,
                style = ShoppingTypography.highlightedCaption,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = name,
                color = ShoppingColors.Gray4,
                style = ShoppingTypography.productName,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LastViewedProductCardPreview() {
    LastViewedProductCard(
        name = "PET보틀-정사각형(500ml)",
        onClick = {},
    )
}
