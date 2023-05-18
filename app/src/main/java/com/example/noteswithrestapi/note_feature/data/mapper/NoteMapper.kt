package com.example.noteswithrestapi.note_feature.data.mapper

import com.example.noteswithrestapi.note_feature.data.remote.dto.NoteDto
import com.example.noteswithrestapi.note_feature.domain.model.Note


fun NoteDto.toNote(): Note = Note(
    id = this.id,
    title = this.title,
    content = this.content,
    date_updated = this.date_updated,
)