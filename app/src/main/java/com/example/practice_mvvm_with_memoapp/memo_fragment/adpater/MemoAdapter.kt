package com.example.practice_mvvm_with_memoapp.memo_fragment.adpater


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.practice_mvvm_with_memoapp.databinding.ItemMemoBinding
import com.example.practice_mvvm_with_memoapp.room.entity.MemoEntity
import java.text.FieldPosition

class MemoAdapter(private val listener: OnItemClickListener) :
	ListAdapter<MemoEntity, MemoViewHolder>(
		object : DiffUtil.ItemCallback<MemoEntity>() {
			override fun areItemsTheSame(oldItem: MemoEntity, newItem: MemoEntity): Boolean =
				oldItem.id == newItem.id

			override fun areContentsTheSame(oldItem: MemoEntity, newItem: MemoEntity): Boolean =
				oldItem.title == newItem.title &&
						oldItem.description == newItem.description &&
						oldItem.date == newItem.date


		}
	) {

	interface OnItemClickListener {
		fun setOnClickListener(position: Int)
		fun setOnLongClickListener(position: Int)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder =
		MemoViewHolder(
			ItemMemoBinding.inflate(
				LayoutInflater.from(parent.context),
				parent,
				false
			), listener
		)

	override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
		holder.updateView(getItem(position))
	}


}

class MemoViewHolder(
	private val binding: ItemMemoBinding,
	listener: MemoAdapter.OnItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

	fun updateView(memo: MemoEntity) {
		binding.run {
			title.text = memo.title
			description.text = memo.description
			date.text = memo.date
		}
	}

	init {
		binding.container.let {
			it.setOnClickListener() {
				if (adapterPosition != RecyclerView.NO_POSITION) {
					listener.setOnClickListener(adapterPosition)
				}
			}
			it.setOnLongClickListener() {
				if (adapterPosition != RecyclerView.NO_POSITION) {
					listener.setOnLongClickListener(adapterPosition)
				}
				return@setOnLongClickListener true
			}
		}

	}
}

