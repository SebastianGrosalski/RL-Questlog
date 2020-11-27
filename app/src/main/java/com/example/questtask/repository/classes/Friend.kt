package com.example.questtask.repository.classes

import java.io.Serializable

data class Friend(
        val uid: String = "",
        val email: String = "",
        val name: String = ""
    ) : Serializable {

    override fun toString(): String {
        return name
    }
}
