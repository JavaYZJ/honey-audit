package red.honey.audit.core.utils;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import red.honey.audit.common.exception.GetBeanException;

import java.util.Map;
import java.util.Set;

/**
 * @author yangzhijie
 * @date 2021/1/20 10:00
 */
public class SpiOrIocUtils {

    private static Logger logger = LoggerFactory.getLogger(SpiOrIocUtils.class);


    @Nullable
    public static <T> T getBean(ApplicationContext applicationContext, Class<T> clazz) throws GetBeanException {
        try {
            T bean = getFromIoc(applicationContext, clazz);
            if (bean == null) {
                synchronized (SpiOrIocUtils.class) {
                    bean = getFromSpi(clazz);
                    if (bean != null) {
                        bean = registerSpiBeanToIoc(applicationContext, bean);
                    }
                }
            }
            return bean;
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("get the bean from spi or spring ioc happen error:", e);
            }
            throw new GetBeanException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <S> S registerSpiBeanToIoc(ApplicationContext applicationContext, S bean) {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        RootBeanDefinition beanDefinition = new RootBeanDefinition(bean.getClass());
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, beanFactory);
        Object o = beanFactory.getBean(bean.getClass());
        bean = (S) o;
        return bean;
    }


    @SuppressWarnings("unchecked")
    @Nullable
    public static <S, T> S getFromSpi(Class<T> clazz) {
        Set<T> supportedExtensionInstances = ExtensionLoader.getExtensionLoader(clazz).getSupportedExtensionInstances();
        if (CollectionUtils.isEmpty(supportedExtensionInstances)) {
            return null;
        }
        for (T supportedExtensionInstance : supportedExtensionInstances) {
            return (S) supportedExtensionInstance;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static <S, T> S getFromIoc(ApplicationContext applicationContext, Class<T> clazz) {
        Map<String, T> beans = applicationContext.getBeansOfType(clazz);
        if (CollectionUtils.isEmpty(beans)) {
            return null;
        }
        for (T t : beans.values()) {
            return (S) t;
        }
        return null;
    }


}
