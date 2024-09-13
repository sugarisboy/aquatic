package dev.muskrat.aquatic.example.logs;

import ch.qos.logback.core.OutputStreamAppender;
import dev.muskrat.aquatic.lib.common.execution.logic.impl.TestExecutorImpl;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CustomAppend<E> extends OutputStreamAppender<E>  {

    private static final Map<String, List<String>> logs = new ConcurrentHashMap<>();

    @Override
    protected void append(E eventObject) {
        super.append(eventObject);


        UUID testRunId = TestExecutorImpl.getTestRunId();
        if (testRunId != null && !StringUtils.isEmpty(testRunId)) {
            if (!logs.containsKey(testRunId)) {
                logs.put(testRunId.toString(), new LinkedList<>());
            }
            logs.get(testRunId).add(eventObject.toString());
        }
    }

    public static String popLogByRunId(String testRunId) {
        if (testRunId == null) {
            return "testRunId was is empty";
        }
        List<String> strings = logs.get(testRunId);
        if (CollectionUtils.isEmpty(strings)) {
            return "log is empty";
        }
        String join = String.join("\n", strings);
        logs.remove(testRunId);
        return join;
    }
}
