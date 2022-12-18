package com.shubham.flowapi_ceezycode.operators

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

fun getNotes(): Flow<Note> {

    val list = listOf<Note>(
        Note(1, true, "First", "First Description"),
        Note(2, true, "Second", "Second Description"),
        Note(3, false, "Third", "Third Description")
    )

    return list.asFlow() // this is how we can convert list into flow
}

data class Note(val id: Int, val isActive: Boolean, val title: String, val description: String)
data class FormattedNote(val id: Int, val isActive: Boolean, val title: String, val description: String)