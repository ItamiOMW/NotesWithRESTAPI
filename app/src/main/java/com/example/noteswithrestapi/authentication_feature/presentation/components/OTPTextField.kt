package com.example.noteswithrestapi.authentication_feature.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OTPTextField(
    modifier: Modifier = Modifier,
    textValue: () -> String = { "" },
    onTextValueChange: (String) -> Unit,
    charColor: Color = MaterialTheme.colorScheme.tertiary,
    underlineColor: Color = MaterialTheme.colorScheme.primary,
    charBackground: Color = Color.Transparent,
    charSize: TextUnit = 26.sp,
    containerSize: Dp = charSize.value.dp * 2,
    length: Int = 6,
    enabled: Boolean = true,
    password: Boolean = false,
    passwordChar: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
) {
    BasicTextField(
        modifier = modifier,
        value = textValue(),
        onValueChange = {
            if (it.length <= length) {
                onTextValueChange.invoke(it)
            }
        },
        enabled = enabled,
        keyboardOptions = keyboardOptions,
        decorationBox = {
            Row(horizontalArrangement = Arrangement.spacedBy(0.dp)) {
                repeat(length) { index ->
                    CharView(
                        index = index,
                        text = textValue(),
                        charColor = charColor,
                        underlineColor = underlineColor,
                        charSize = charSize,
                        containerSize = containerSize,
                        charBackground = charBackground,
                        password = password,
                        passwordChar = passwordChar,
                    )
                }
            }
        })
}

@Composable
private fun CharView(
    index: Int,
    text: String,
    charColor: Color,
    underlineColor: Color,
    charSize: TextUnit,
    containerSize: Dp,
    charBackground: Color = Color.Transparent,
    password: Boolean = false,
    passwordChar: String = "",
) {

     val modifier = Modifier
        .width(containerSize)
        .background(charBackground)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val char = when {
            index >= text.length -> ""
            password -> passwordChar
            else -> text[index].toString()
        }
        Text(
            text = char,
            color = charColor,
            modifier = modifier.wrapContentHeight(),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = charSize,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(2.dp))
        Box(
            modifier = Modifier
                .background(underlineColor)
                .height(1.5.dp)
                .width(containerSize / 1.75f)
        )
    }
}
