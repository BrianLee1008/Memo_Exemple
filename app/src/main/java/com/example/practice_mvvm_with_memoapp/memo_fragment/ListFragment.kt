package com.example.practice_mvvm_with_memoapp.memo_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice_mvvm_with_memoapp.databinding.FragListBinding
import com.example.practice_mvvm_with_memoapp.memo_fragment.adpater.MemoAdapter
import com.example.practice_mvvm_with_memoapp.view_model.MainViewModel
import com.example.practice_mvvm_with_memoapp.view_model.ViewModelFactory

class ListFragment : Fragment(),MemoAdapter.OnItemClickListener {

	private val viewModel by activityViewModels<MainViewModel> { ViewModelFactory(requireActivity().application) }

	private var _binding : FragListBinding? = null
	val binding : FragListBinding
	get() = _binding!!


	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragListBinding.inflate(inflater, container, false)
		return binding.root
	}


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setRecyclerViewObserve()
		setListener()
		initRecyclerView()

	}

	private fun initRecyclerView(){
		binding.recyclerView.let {
			it.layoutManager =
				LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
			it.addItemDecoration(
				DividerItemDecoration(
					requireContext(),
					DividerItemDecoration.VERTICAL
				)
			)
		}
	}

	private fun setRecyclerViewObserve(){
		viewModel.listLiveData.observe(
			viewLifecycleOwner, {
				(binding.recyclerView.adapter as MemoAdapter).submitList(it) // DiffUtil을 위한 갱신 코드
			}
		)
		binding.recyclerView.adapter = MemoAdapter(this) // 실제 adapter 연결 코드
	}

	override fun setOnClickListener(position: Int) {
		viewModel.run {
			setEditMemo(position)
			replaceFragment(MainViewModel.FragmentType.EDITOR)
		}
	}

	override fun setOnLongClickListener(position: Int) {
		viewModel.deleteMemo(position)
	}

	private fun setListener(){
		binding.buttonNewMemo.setOnClickListener(){
			viewModel.replaceFragment(MainViewModel.FragmentType.EDITOR)
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}




}