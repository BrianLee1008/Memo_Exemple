package com.example.practice_mvvm_with_memoapp.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.practice_mvvm_with_memoapp.room.entity.MemoEntity

@Dao
interface MemoDao : BaseDao<MemoEntity> {
	@Query("SELECT * FROM memo ORDER BY id DESC, date DESC ")
	fun getAll(): LiveData<List<MemoEntity>>
}