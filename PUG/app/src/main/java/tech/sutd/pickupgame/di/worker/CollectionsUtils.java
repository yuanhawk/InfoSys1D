package tech.sutd.pickupgame.di.worker;

import androidx.work.ListenableWorker;

import java.util.Map;
import java.util.Objects;

import javax.inject.Provider;

public class CollectionsUtils {

    public static Provider<ChildWorkerFactory> getWorkerFactoryProviderByKey(Map<Class<? extends ListenableWorker>, Provider<ChildWorkerFactory>> map, String key) {
        for (Map.Entry<Class<? extends ListenableWorker>, Provider<ChildWorkerFactory>> entry : map.entrySet()) {
            if (Objects.equals(key, entry.getKey().getName())) {
                return entry.getValue();
            }
        }
        return null;
    }
}
