package br.com.adaca.workerutils;

import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class WorkerUtils {
    public static Map<String, Object> prepareDataToBeAdded(List<Object> data) {
        Map<String, Object> dataMapped = new HashMap<>();

        for (int index = 0; index < data.size(); index++) {
            dataMapped.put("parameter"+ index, data.get(index));
        }

        return dataMapped;
    }

    public static List<OneTimeWorkRequest> addDataToRequest(Map<String, Object> dataToBeAdded) {
        List<OneTimeWorkRequest> request = new ArrayList<>();

        try {
            for (int index = 0; index < dataToBeAdded.size(); index++) {
                request.add(new OneTimeWorkRequest.Builder(Worker.class)
                        .setConstraints(new Constraints.Builder()
                                .setRequiredNetworkType(NetworkType.CONNECTED)
                                .setRequiresBatteryNotLow(true)
                                .build())
                        .setInputData(new Data.Builder()
                                .putAll(dataToBeAdded)
                                .build())
                        .setBackoffCriteria(
                                BackoffPolicy.LINEAR,
                                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                                TimeUnit.MILLISECONDS)
                        .build());
            }
        } catch (Exception ignored) {
        }

        return request;
    }
}
