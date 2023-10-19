package com.adelvanchik.mydays.Data.parameters

import com.adelvanchik.mydays.Domain.entity.Parameters

class ParametersMapper {

    fun entityParametersToParametersDBModel(myParameters: Parameters): ParametersDBModel = ParametersDBModel(
        data = myParameters.data,
        day = myParameters.day,
        month = myParameters.month,
        year = myParameters.year,
        weight = myParameters.weight,
        height = myParameters.height,
    )

    fun entityParametersDBModelToParameters(myParameters: ParametersDBModel): Parameters = Parameters(
        data = myParameters.data,
        day = myParameters.day,
        month = myParameters.month,
        year = myParameters.year,
        weight = myParameters.weight,
        height = myParameters.height,
    )

    fun listParametersDBModelToParameters(list: List<ParametersDBModel?>): List<Parameters> = list.filterNotNull()
        .map{entityParametersDBModelToParameters(it)}
}