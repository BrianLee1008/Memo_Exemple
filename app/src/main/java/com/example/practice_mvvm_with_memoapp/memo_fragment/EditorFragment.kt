package com.example.practice_mvvm_with_memoapp.memo_fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.practice_mvvm_with_memoapp.databinding.FragEditorBinding
import com.example.practice_mvvm_with_memoapp.view_model.MainViewModel
import com.example.practice_mvvm_with_memoapp.view_model.ViewModelFactory

class EditorFragment : Fragment() {

	private val viewModel by activityViewModels<MainViewModel>{ ViewModelFactory(requireActivity().application) }

	private lateinit var callback : OnBackPressedCallback

	private var _binding : FragEditorBinding? = null
	val binding : FragEditorBinding
		get() = _binding!!



	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragEditorBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		callback = object : OnBackPressedCallback(true){
			override fun handleOnBackPressed() {
				binding.title.text.clear()
				binding.description.text.clear()
				viewModel.replaceFragment(MainViewModel.FragmentType.LIST)
			}
		}
		requireActivity().onBackPressedDispatcher.addCallback(this, callback)
	}


	@RequiresApi(Build.VERSION_CODES.O)
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setListener()
		setEditLiveDataObserver()

	}

	@RequiresApi(Build.VERSION_CODES.O)
	private fun setListener(){
		binding.buttonSave.setOnClickListener(){
			viewModel.saveMemo(
				binding.title.text.toString(),
				binding.description.text.toString(),
				""
			)
			binding.title.text.clear()
			binding.description.text.clear()
			viewModel.replaceFragment(MainViewModel.FragmentType.LIST)
		}
	}

	private fun setEditLiveDataObserver(){
		viewModel.editLiveData.observe(
			viewLifecycleOwner,{
				binding.run {
					title.setText(it?.title)
					description.setText(it?.description)

				}
			}
		)
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onDetach() {
		super.onDetach()
		callback.remove()
	}

}