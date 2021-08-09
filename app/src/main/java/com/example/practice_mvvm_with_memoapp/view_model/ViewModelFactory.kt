package com.example.practice_mvvm_with_memoapp.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practice_mvvm_with_memoapp.AppRepository
import java.lang.IllegalArgumentException

//xo ViewModel 생성기
class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

	@Suppress("UNCHECKED_CAST")
	// xo MainViewModel이 modelClass를 상속받은 것 이라면 AppRepository를 입력값으로 받는 MainViewModel을 반환한다.
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		if(modelClass.isAssignableFrom(MainViewModel::class.java)){
			return MainViewModel(AppRepository(application)) as T
		}
		throw IllegalArgumentException("")
	}
}