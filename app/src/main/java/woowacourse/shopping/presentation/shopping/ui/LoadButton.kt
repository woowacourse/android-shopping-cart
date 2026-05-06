package woowacourse.shopping.presentation.shopping.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R
import woowacourse.shopping.ui.theme.Gray50

@Composable
fun LoadButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier =
            modifier
                .padding(horizontal = 30.dp),
        colors =
            ButtonColors(
                containerColor = Gray50,
                contentColor = Color.White,
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
            ),
    ) {
        Text(
            text = stringResource(R.string.load_more),
            fontSize = 18.sp,
            color = Color.White,
        )
    }
}

@Preview
@Composable
private fun LoadButtonPreview() {
    LoadButton(
        onClick = {},
    )
}
