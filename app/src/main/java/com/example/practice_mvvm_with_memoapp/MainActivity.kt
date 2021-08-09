package com.example.practice_mvvm_with_memoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.practice_mvvm_with_memoapp.databinding.ActivityMainBinding
import com.example.practice_mvvm_with_memoapp.memo_fragment.EditorFragment
import com.example.practice_mvvm_with_memoapp.memo_fragment.ListFragment
import com.example.practice_mvvm_with_memoapp.view_model.MainViewModel
import com.example.practice_mvvm_with_memoapp.view_model.ViewModelFactory

class MainActivity : AppCompatActivity() {

	/*
	* 1.Room 설정, Fragment 세팅
	* 2.recyclerView 세팅
	* 3. CRUD
	* 4. Filter
	* CRUD의 과정 정확히 이해 위해 그림으로 그리기.
	* */

	private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
	private val viewModel by viewModels<MainViewModel> {ViewModelFactory(application)}

	private val listFragment = ListFragment()
	private val editorFragment = EditorFragment()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		setFragObserve()
	}

	private fun setFragObserve() {
		viewModel.fragLiveData.observe(
			this, {
				val targetFragment =
					when (it) {
						MainViewModel.FragmentType.LIST -> listFragment
						MainViewModel.FragmentType.EDITOR -> editorFragment

					}
				replaceFragment(targetFragment)
			}
		)
	}


	private fun replaceFragment(fragment: Fragment) {
		val fragmentTransaction = supportFragmentManager.beginTransaction()
		fragmentTransaction.replace(binding.frameLayout.id, fragment).commit()
	}

}