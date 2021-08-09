package com.example.practice_mvvm_with_memoapp.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.example.practice_mvvm_with_memoapp.room.entity.MemoEntity

@Dao
interface BaseDao<T> {

	@Insert
	fun insert(t : T)

	@Update
	fun update(t : T)

	@Delete
	fun delete(t : T)

	@Delete
	fun deleteAll(t : List<MemoEntity>)

}