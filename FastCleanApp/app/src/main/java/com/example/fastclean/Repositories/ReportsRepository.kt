package com.example.fastclean.Repositories

import com.example.fastclean.Models.Reports.Report
import com.example.fastclean.RestService.RetrofitService

class ReportsRepository constructor(private val retrofitService : RetrofitService) {

    fun reportCliente(report: Report) = retrofitService.reportCliente(report)

    fun reportFuncionario(report: Report) = retrofitService.reportFuncionario(report)

    fun reportClienteChat(report: Report) = retrofitService.reportClienteChat(report)

    fun reportFuncionarioChat(report: Report) = retrofitService.reportFuncionarioChat(report)

}