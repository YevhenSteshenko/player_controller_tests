package _common.utils.core;

import io.qameta.allure.awaitility.AllureAwaitilityListener;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionFactory;

public class AwaitilityHelper {
    public static ConditionFactory await() {
        return Awaitility.await().conditionEvaluationListener(new AllureAwaitilityListener()).pollInSameThread();
    }

    private AwaitilityHelper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
