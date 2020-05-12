package com.algaworks.brewer.thymeleaf;

import com.algaworks.brewer.thymeleaf.processor.*;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import java.util.HashSet;
import java.util.Set;

public class BrewerDialect  extends AbstractProcessorDialect {

    public BrewerDialect(){
        super("AlgaWorks Brewer", "brewer", StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<>();
        processors.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));
        processors.add(new MessageElementTagProcessor(dialectPrefix));
        processors.add(new OrderElementTagProcessor(dialectPrefix));
        processors.add(new PaginationElementTagProcessor(dialectPrefix));
        processors.add(new MenuAttributeTagProcessor(dialectPrefix));
        return processors;
    }
}
