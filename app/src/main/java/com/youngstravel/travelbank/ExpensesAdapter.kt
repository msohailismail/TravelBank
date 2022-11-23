package com.youngstravel.travelbank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.youngstravel.travelbank.data.Expense
import com.youngstravel.travelbank.databinding.ExpensesListItemBinding
import com.youngstravel.travelbank.utils.parseDate

class ExpensesAdapter(private val clickListener:(Int) -> Unit) :
    ListAdapter<Expense, ExpensesAdapter.ExpensesViewHolder>(DiffCallback) {

    class ExpensesViewHolder(private var binding: ExpensesListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(expense: Expense) {
            binding.tvMerchantName.text = expense.expenseVenueTitle
            binding.tvDate.text = parseDate(expense.date, true)
            binding.tvCurrency.text = itemView.context.getString(R.string.formatted_amount, expense.amount)
            binding.btnCategory.text = expense.tripBudgetCategory

            if(expense.currencyCode != "USD") {
                binding.btnCurrency.visibility = View.VISIBLE
                binding.btnCurrency.text = expense.currencyCode
            } else {
                binding.btnCurrency.visibility = View.INVISIBLE
            }

            expense.attachments?.let {
                it[0].thumbnails?.imgSrcUrl?.let { imgUrl ->
                    val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
                    binding.ivReceipt.load(imgUri) {
                        placeholder(R.drawable.loading_animation)
                        error(R.drawable.receipt)
                    }
                }
            }

            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesViewHolder {
        return ExpensesViewHolder(
            ExpensesListItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ExpensesViewHolder, position: Int) {
        val anExpense = getItem(position)
        holder.bind(anExpense)
        holder.itemView.setOnClickListener {
            clickListener(position)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem == newItem
        }
    }
}