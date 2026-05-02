package woowacourse.shopping.presentation.cart.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.ui.theme.Gray50
import woowacourse.shopping.ui.theme.Green40

@Composable
fun CartPageSection(
    page: Int,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    isCanMoveNext: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            onClick = onPrevious,
            enabled = page > 1,
            colors =
                ButtonColors(
                    containerColor = Green40,
                    contentColor = Color.White,
                    disabledContentColor = Color.White,
                    disabledContainerColor = Gray50,
                ),
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "이전 페이지",
                modifier = Modifier.size(14.dp),
            )
        }
        Text(
            text = page.toString(),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Gray50,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        Button(
            enabled = isCanMoveNext,
            onClick = onNext,
            colors =
                ButtonColors(
                    containerColor = Green40,
                    contentColor = Color.White,
                    disabledContentColor = Color.White,
                    disabledContainerColor = Gray50,
                ),
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = "다음 페이지",
                tint = Color.White,
                modifier = Modifier.size(14.dp),
            )
        }
    }
}
