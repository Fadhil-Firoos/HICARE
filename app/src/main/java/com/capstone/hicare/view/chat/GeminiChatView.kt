package com.capstone.hicare.view.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.TextPart
import kotlinx.coroutines.launch
import androidx.compose.ui.res.painterResource
import com.capstone.hicare.R

@Composable
fun GeminiChatView(
    apiKey: String,
    appThemColor: Color = Color.Green,
    chatContext: List<GeminiContent> = emptyList()
) {
    val lazyColumnListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val isLoading = remember { mutableStateOf(false) }
    val chatDataList = remember { mutableStateOf(listOf<ChatMember>()) }

    val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = apiKey
    )
    val chat = generativeModel.startChat(history = chatContext.map {
        Content(role = it.role, parts = listOf(TextPart(text = it.text)))
    })

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = appThemColor,
        ) {
            Text(
                text = "HICARE",
                color = Color.Black,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(10f)
            )

            Text(
                text = "Ask to Kiana, our specialist Lettuce",
                color = Color.Black,
                fontSize = 12.sp,
                modifier = Modifier.padding(16.dp, top = 50.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(8.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyColumnListState
            ) {
                items(chatDataList.value) { chat ->
                    ChatBubble(
                        text = chat.text,
                        memberType = chat.memberType,
                        appThemColor = appThemColor
                    )
                }
            }
        }

        RoundedCornerTextFieldWithSend(
            modifier = Modifier.fillMaxWidth(),
            onSendClick = {
                coroutineScope.launch {
                    chatDataList.value += listOf(ChatMember(MemberType.USER, it))
                    lazyColumnListState.animateScrollToItem(chatDataList.value.size - 1)
                    isLoading.value = true
                    try {
                        val response = chat.sendMessage(it)
                        chatDataList.value += listOf(
                            ChatMember(
                                MemberType.BOT,
                                response.text ?: ""
                            )
                        )
                        lazyColumnListState.animateScrollToItem(chatDataList.value.size - 1)
                        isLoading.value = false
                    } catch (ex: Exception) {
                        isLoading.value = false
                        ex.printStackTrace()
                    }
                }
            },
            isLoading.value,
            appThemColor
        )
    }
}

@Composable
fun ChatBubble(
    text: String,
    memberType: MemberType,
    appThemColor: Color
) {
    val bubbleColor = if (memberType == MemberType.USER) {
        Color(0xFFB9F5D3)
    } else {
        appThemColor
    }

    val alignment = if (memberType == MemberType.USER) {
        Alignment.End
    } else {
        Alignment.Start
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = if (memberType == MemberType.USER) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (memberType == MemberType.BOT) {
            Icon(
                painter = painterResource(id = R.drawable.hutao),
                contentDescription = "Bot Avatar",
                modifier = Modifier.size(40.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Box(
            modifier = Modifier
                .background(
                    color = bubbleColor,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                text = text,
                color = Color.Black,
                fontSize = 16.sp
            )
        }

        if (memberType == MemberType.USER) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Rounded.AccountCircle,
                contentDescription = "User Avatar",
                modifier = Modifier.size(40.dp),
                tint = Color.Unspecified
            )
        }
    }
}


@Composable
fun RoundedCornerTextFieldWithSend(
    modifier: Modifier = Modifier,
    onSendClick: (String) -> Unit, // Callback for send button click
    isLoading: Boolean,
    appThemColor: Color
) {
    val focusRequester = remember { FocusRequester() }
    val textState = remember { mutableStateOf("") }

    Row(modifier = modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            label = { Text("Apa yang ingin anda ketahui?") },
            maxLines = 6,
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFB9F5D3),
                focusedLabelColor = Color.Black,
                unfocusedBorderColor = Color.Green,
                unfocusedLabelColor = Color.Green,
                cursorColor = Color.Black
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(36.dp),
                strokeWidth = 4.dp,
                color = Color.Green,
            )
        } else {
            Button(
                modifier = Modifier.size(50.dp),
                contentPadding = PaddingValues(0.dp),
                enabled = textState.value.isNotBlank(),
                onClick = {
                    onSendClick(textState.value)
                    textState.value = ""
                },
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = appThemColor
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send"
                )
            }
        }
    }
    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
    }
}

data class GeminiContent(var role: String, var text: String)
data class ChatMember(var memberType: MemberType, var text: String)
enum class MemberType {BOT, USER}