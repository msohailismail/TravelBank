package com.youngstravel.travelbank.data

data class Expense(val id: String,
                   val amount: Double,
                   val date: String,
                   val expenseVenueTitle: String,
                   val currencyCode: String,
                   val tripBudgetCategory: String,
                   val attachments: List<Attachment>?)