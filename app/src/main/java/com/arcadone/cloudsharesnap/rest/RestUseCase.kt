package com.arcadone.cloudsharesnap.rest

import com.arcadone.cloudsharesnap.domain.model.ApiResult
import timber.log.Timber

/**
 * Every useCase with REST query must extend this base class,
 * where:
 * - [Arguments] is the argument type of the call
 * - [ReturnType] is the return type of the call
 */
abstract class RestUseCase<Arguments, ReturnType> where ReturnType : Any {
    /**
     * Every useCase must implement this class
     */
    internal abstract suspend fun execute(params: Arguments): ApiResult<ReturnType>

    suspend fun invoke(params: Arguments): ApiResult<ReturnType> {
        return try {
            execute(params)
        } catch (exception: Exception) {
            Timber.w("Unable to launch useCase - got an exception ${exception.message}")
            ApiResult.error(exception)
        }
    }
}
