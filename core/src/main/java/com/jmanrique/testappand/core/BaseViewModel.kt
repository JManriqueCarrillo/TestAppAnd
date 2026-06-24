package com.jmanrique.testappand.core

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected fun <ResultType> executeUseCase(
        targetStateFlow: MutableStateFlow<UiState<ResultType>>,
        useCaseCall: suspend () -> Either<Failure, ResultType>,
        onSuccess: ((data: ResultType) -> Unit)? = null,
        onError: ((failure: Failure) -> Unit)? = null
    ) {
        targetStateFlow.value = UiState.Loading
        viewModelScope.launch {
            try {
                useCaseCall().fold(
                    ifLeft = { failure ->
                        targetStateFlow.value = UiState.Error(
                            message = failure.getErrorMessage(),
                            throwable = failure.getAssociatedThrowable()
                        )
                        onError?.invoke(failure)
                    },
                    ifRight = { data ->
                        targetStateFlow.value = UiState.Success(data)
                        onSuccess?.invoke(data)
                    }
                )
            } catch (e: CancellationException) {
                targetStateFlow.value = UiState.Idle
                Log.d("BaseViewModel", "UseCase execution cancelled: ${e.message}")
                throw e
            } catch (e: Exception) {
                val errorMsg = "An unexpected error occurred: ${e.localizedMessage}"
                targetStateFlow.value = UiState.Error(message = errorMsg, throwable = e)
                onError?.invoke(Failure.GenericError(errorMsg, e))
                Log.e("BaseViewModel", "Unexpected error during UseCase execution", e)
            }
        }
    }
}
