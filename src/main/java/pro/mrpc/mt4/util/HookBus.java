package pro.mrpc.mt4.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Lightweight event bus (Java port of the Python {@code HookBus}). Subscribe with {@link #on},
 * unsubscribe with {@link #off}, publish with {@link #emit}. A failing callback never breaks the caller.
 */
public class HookBus {
    private final Map<String, List<Consumer<Map<String, Object>>>> subs = new ConcurrentHashMap<>();

    public void on(String event, Consumer<Map<String, Object>> cb) {
        subs.computeIfAbsent(event, k -> new CopyOnWriteArrayList<>()).add(cb);
    }

    public void off(String event, Consumer<Map<String, Object>> cb) {
        List<Consumer<Map<String, Object>>> list = subs.get(event);
        if (list != null) list.remove(cb);
    }

    public void emit(String event, Map<String, Object> payload) {
        List<Consumer<Map<String, Object>>> list = subs.get(event);
        if (list == null) return;
        for (Consumer<Map<String, Object>> cb : list) {
            try {
                cb.accept(payload);
            } catch (Exception ignored) {
                // a hook must not break the caller
            }
        }
    }
}
