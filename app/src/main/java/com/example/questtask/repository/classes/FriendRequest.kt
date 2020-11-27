package com.example.questtask.repository.classes

import java.io.Serializable

data class FriendRequest(
    val receiver: String = "",
    val sender: String = "",
    val status: String = "",
    val senderEmail: String = ""
) : Serializable