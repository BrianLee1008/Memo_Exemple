package com.example.practice_mvvm_with_memoapp.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memo")
data class MemoEntity(
	@ColumnInfo(name = "title") var title: String,
	@ColumnInfo(name = "description") var description: String,
	@ColumnInfo(name = "date") var date: String,
	@ColumnInfo(name = "imageUri") var imageUri: String,

	@PrimaryKey(autoGenerate = true) val id: Int = 0

) {
}