package cn.gotoil.bill.config.thymeleaf;


import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by think on 2017/7/5.
 */
public class SecureDialect extends AbstractProcessorDialect {

    private static final String DIALECT_NAME = "Secure Dialect";


    public SecureDialect() {
        // We will set this dialect the same "dialect processor" precedence as
        // the Standard Dialect, so that processor executions can interleave.
        super(DIALECT_NAME, "secure", StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(new MyStringProcessor(dialectPrefix));
        processors.add(new MyNumberProcessor(dialectPrefix));
        processors.add(new MyRoleProcessor(dialectPrefix));
        processors.add(new MyPermissionProcessor(dialectPrefix));

        return processors;
    }
}
