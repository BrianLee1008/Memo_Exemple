package com.example.practice_mvvm_with_memoapp.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.practice_mvvm_with_memoapp.room.dao.MemoDao
import com.example.practice_mvvm_with_memoapp.room.entity.MemoEntity

@Database(entities = [MemoEntity::class],version = 4)
abstract class MemoDataBase : RoomDatabase() {

	abstract fun memoDao(): MemoDao
}