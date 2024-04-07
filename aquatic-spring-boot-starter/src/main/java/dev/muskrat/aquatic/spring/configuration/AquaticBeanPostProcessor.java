package dev.muskrat.aquatic.spring.configuration;

import dev.muskrat.aquatic.lib.common.declaration.logic.DeclarationInitializer;
import dev.muskrat.aquatic.spring.Aquatic;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Lazy;

public class AquaticBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    @Lazy
    private DeclarationInitializer declarationInitializer;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> tClass = bean.getClass();

        if (tClass.getAnnotation(Aquatic.class) != null) {
            declarationInitializer.initStep(bean);
        }

        return bean;
    }
}
