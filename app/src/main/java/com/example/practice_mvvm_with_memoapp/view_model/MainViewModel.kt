package com.example.practice_mvvm_with_memoapp.view_model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice_mvvm_with_memoapp.AppRepository
import com.example.practice_mvvm_with_memoapp.room.entity.MemoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainViewModel(private val repository: AppRepository) : ViewModel() {

	enum class FragmentType {
		LIST, EDITOR
	}

	private var _fragLiveData = MutableLiveData(FragmentType.LIST)
	val fragLiveData: LiveData<FragmentType>
		get() = _fragLiveData


	fun replaceFragment(fragmentType: FragmentType) {
		_fragLiveData.value = fragmentType
	}


	val listLiveData = repository.getAllMemo()

	private var _editLiveData = MutableLiveData<MemoEntity?>()
	val editLiveData: LiveData<MemoEntity?>
		get() = _editLiveData

	fun setEditMemo(position: Int) {
		val memo = listLiveData.value?.get(position) ?: throw Exception("")
		_editLiveData.value = memo
	}

	// 저장 버튼을 클릭해야한다는 개념을 가지고 있으면 뷰모델에서 구현 못함.
	// 저장 버튼을 눌렀을 때 어떻게 된다는 데이터 변동 값만 개념으로 가지고 있어야 함.
	// 예를 들어 date는 insertMemo를 할떄 입력값으로 주는게 아니라 자동으로 넣어질 거니까 ()안에 포함 x
	@RequiresApi(Build.VERSION_CODES.O)
	fun insertMemo(title: String, description: String, imageUri: String) {
		viewModelScope.launch(Dispatchers.IO) {
			val current = LocalDateTime.now()
			val formatter = DateTimeFormatter.ISO_LOCAL_DATE
			val memoDate = current.format(formatter)

			val date = memoDate
			val memo = MemoEntity(title, description, date, imageUri)

			repository.insertMemo(memo)
		}
	}


	@RequiresApi(Build.VERSION_CODES.O)
	fun updateMemo(title: String, description: String, imageUri: String){
		viewModelScope.launch(Dispatchers.IO) {
			val current = LocalDateTime.now()
			val formatter = DateTimeFormatter.ISO_LOCAL_DATE
			val memoDate = current.format(formatter)

			val date = memoDate

			val memo = editLiveData.value?.apply {
				this.title = title
				this.description = description
				this.imageUri = imageUri
				this.date = date
			} ?: throw Exception("")

			repository.updateMemo(memo)

			viewModelScope.launch(Dispatchers.Main) {
				_editLiveData.value = null
			}
		}

	}

	@RequiresApi(Build.VERSION_CODES.O)
	fun saveMemo(title: String, description: String, imageUri: String){
		if(editLiveData.value == null){
			insertMemo(title, description, imageUri)
		} else{
			updateMemo(title, description, imageUri)
		}
	}


	fun deleteMemo(position: Int) {
		viewModelScope.launch(Dispatchers.IO) {
			repository.deleteMemo(listLiveData.value?.get(position) ?: throw Exception(""))
		}
	}
}