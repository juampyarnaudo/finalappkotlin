package com.jparnaudo.finalapp.models

import java.util.*

data class Message(val authorId: String, val message: String, val profileImageURL: String, val sentAT: Date)
