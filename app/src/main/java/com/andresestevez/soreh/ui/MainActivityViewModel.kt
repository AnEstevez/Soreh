package com.andresestevez.soreh.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.work.WorkManager
import com.andresestevez.soreh.framework.workers.CacheWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(workManager: WorkManager) : ViewModel() {

    internal val workInfo =
        workManager.getWorkInfosForUniqueWorkLiveData(CacheWorker.WORK_NAME).map { it[0] }
}