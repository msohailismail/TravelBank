package com.youngstravel.travelbank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.youngstravel.travelbank.databinding.FragmentExpensesBinding

class ExpensesFragment : Fragment() {
    private val viewModel: ExpenseViewModel by activityViewModels()
    private lateinit var binding: FragmentExpensesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExpensesBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the ExpenseViewModel
        binding.viewModel = viewModel

        // Sets the adapter of the photosGrid RecyclerView
        binding.rvExpenses.adapter = ExpensesAdapter { expenseArrayIndex ->
            val action = ExpensesFragmentDirections
                .actionExpensesFragmentToExpenseDetailFragment(expenseArrayIndex)
            findNavController().navigate(action)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.totalExpense.observe(viewLifecycleOwner) {
            binding.tvSumOfExpenses.text = getString(R.string.totalExpense, it)
        }
    }
}