package com.example.practice_mvvm_with_memoapp

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.practice_mvvm_with_memoapp.room.database.MemoDataBase
import com.example.practice_mvvm_with_memoapp.room.entity.MemoEntity


class AppRepository(application: Application) {

	companion object {
		private const val MEMO_DATABASE_NAME = "MemoDataBase"
	}

	// 리포지토리 단에서 Room DB 인스턴스화
	val db = Room.databaseBuilder(
		application, MemoDataBase::class.java, MEMO_DATABASE_NAME
	).fallbackToDestructiveMigration()
		.build()

	val memoDao = db.memoDao()

	// Room DataBase CRUD 접근
	fun getAllMemo() : LiveData<List<MemoEntity>> =
		memoDao.getAll()

	fun insertMemo(memo : MemoEntity){
		memoDao.insert(memo)
	}

	fun updateMemo(memo: MemoEntity) {
		memoDao.update(memo)
	}

	fun deleteMemo(memo: MemoEntity) {
		memoDao.delete(memo)
	}

	fun deleteAll(memo : List<MemoEntity>){
		memoDao.deleteAll(memo)
	}



}