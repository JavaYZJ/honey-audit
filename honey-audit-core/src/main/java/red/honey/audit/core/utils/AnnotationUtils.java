package red.honey.audit.core.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author yangzhijie
 * @date 2021/1/19 14:29
 */
public class AnnotationUtils {

    public static Annotation getAnnotation(ProceedingJoinPoint jointPoint, Class<? extends Annotation> clazz) {
        Method method = ((MethodSignature) jointPoint.getSignature()).getMethod();
        return method.getAnnotation(clazz);
    }
}
