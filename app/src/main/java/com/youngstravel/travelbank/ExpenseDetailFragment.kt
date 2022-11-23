package com.youngstravel.travelbank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.youngstravel.travelbank.databinding.FragmentExpenseDetailBinding
import com.youngstravel.travelbank.utils.parseDate

class ExpenseDetailFragment : Fragment() {

    private val navigationArgs: ExpenseDetailFragmentArgs by navArgs()
    private val viewModel: ExpenseViewModel by activityViewModels()
    private lateinit var binding: FragmentExpenseDetailBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentExpenseDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arrayIndex = navigationArgs.expenseArrayIndex
        bindData(arrayIndex)
    }

    private fun bindData(index: Int) {
        viewModel.getAnExpenseObject(index)?.let { anExpense ->
            binding.apply {
                tvMerchantName.text = anExpense.expenseVenueTitle
                tvAmount.text = getString(R.string.formatted_amount, anExpense.amount)
                tvDateValue.text = parseDate(anExpense.date, false)
                tvCategoryValue.text = anExpense.tripBudgetCategory
                btnCurrency.text = anExpense.currencyCode.uppercase()

                anExpense.attachments?.let {
                    it[0].thumbnails?.imgSrcUrl?.let { imgUrl ->
                        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
                        binding.ivReceipt.load(imgUri) {
                            placeholder(R.drawable.loading_animation)
                            error(R.drawable.receipt)
                        }
                    }
                }
            }
        }
    }

}