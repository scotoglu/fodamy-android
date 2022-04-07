App module contains views, viewmodels and related files. App module has base structures that are BaseFragment, BaseViewModel and BaseViewEvent. <br>
[BaseFragment](https://github.com/scotoglu/fodamy-android/blob/master/app/src/main/java/com/scoto/fodamy/ui/base/BaseFragment.kt) contains common operations that used in fragments. Handles view events.<br>
[BaseViewModel](https://github.com/scotoglu/fodamy-android/blob/master/app/src/main/java/com/scoto/fodamy/ui/base/BaseViewModel.kt) contains common operations that used in viewmodels. BaseViewModel, manage sending request and receiving response by a method which is:<br>
```
fun <T> sendRequest(
        loading: Boolean = false,
        request: suspend () -> T,
        success: (suspend (T) -> Unit)? = null,
        error: ((Exception) -> Unit)? = null
    ): Job {
        return viewModelScope.launch {
            if (loading) showDialog()
            try {
                val response = request.invoke()
                success?.invoke(response)
                hideDialog()
            } catch (ex: Exception) {
                hideDialog()
                if (error == null)
                    handleException(ex)
                else error.invoke(ex)
            }
        }
    }
```
We assume, if request is successfull then 'success' block is invoked. Otherwise, 'error' block or 'handleException' invoked. Exceptions are parsed in [BaseRepository](https://github.com/scotoglu/fodamy-android/blob/master/data/src/main/java/com/scoto/data/repositories/BaseRepository.kt). <br>
Usage: 
```
 private fun getRecipes() {
        sendRequest(
            request = {
                recipeRepository.getLastAddedPaging()
            },
            success = {
                it.cachedIn(viewModelScope).collect { pagingData ->
                    _recipes.value = pagingData
                }
            }
        )
    }
```
