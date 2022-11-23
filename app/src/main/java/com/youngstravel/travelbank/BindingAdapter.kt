package com.youngstravel.travelbank

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.youngstravel.travelbank.data.Expense

/**
 * Updates the data shown in the [RecyclerView].
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Expense>?) {
    val adapter = recyclerView.adapter as ExpensesAdapter
    adapter.submitList(data)
}

@BindingAdapter("travelApiStatus")
fun bindStatus(statusImageView: ImageView, status: TravelApiStatus) {
    when (status) {
        TravelApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        TravelApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        TravelApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}
