package com.example.topredditposts.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.topredditposts.dto.PostPageDto
import com.example.topredditposts.repo.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DataViewModel(private val repository: Repository) : ViewModel() {

    private val _state = MutableStateFlow(PostPageDto())
    val state: StateFlow<PostPageDto> = _state

    init {
        nextPage(isInitial = true)
    }

    fun nextPage(isInitial: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newState = repository.getPosts(if (isInitial) null else _state.value.nextPage)
                _state.update { ui ->
                    ui.copy(
                        posts = if (isInitial) {
                            newState.posts.map { post ->
                                post.copy(created = post.created)
                            }
                        } else {
                            _state.value.posts.plus(
                                newState.posts.map { post ->
                                    post.copy(created = post.created)
                                }
                            )
                        },
                        nextPage = newState.nextPage
                    )
                }
            } catch (ex: Exception) {
                println("Error")
            }
        }
    }

    companion object {
        fun provideFactory(repository: Repository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DataViewModel(repository) as T
                }
            }
    }
}