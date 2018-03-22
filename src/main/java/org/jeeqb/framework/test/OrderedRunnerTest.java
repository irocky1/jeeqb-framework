package org.jeeqb.framework.test;

import org.jeeqb.framework.HelperLoader;
import org.jeeqb.framework.test.annotation.TestOrder;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by rocky on 2018/3/22.
 */
public class OrderedRunnerTest extends BlockJUnit4ClassRunner{

    private static List<FrameworkMethod> testMethodList;

    /**
     * Creates a BlockJUnit4ClassRunner to run {@code klass}
     *
     * @param klass
     * @throws InitializationError if the test class is malformed.
     */
    public OrderedRunnerTest(Class<?> klass) throws InitializationError {
        super(klass);
        //init helpers
        HelperLoader.init();
    }

    @Override
    protected List<FrameworkMethod> computeTestMethods() {
        if (testMethodList == null) {
            // 获取带有 Test 注解的方法
            testMethodList = super.computeTestMethods();
            // 获取测试方法上的 Order 注解，并对所有的测试方法重新排序
            Collections.sort(testMethodList, new Comparator<FrameworkMethod>() {
                @Override
                public int compare(FrameworkMethod m1, FrameworkMethod m2) {
                    TestOrder o1 = m1.getAnnotation(TestOrder.class);
                    TestOrder o2 = m2.getAnnotation(TestOrder.class);
                    if (o1 == null || o2 == null) {
                        return 0;
                    }
                    return o1.value() - o2.value();
                }
            });
        }
        return testMethodList;
    }

}
