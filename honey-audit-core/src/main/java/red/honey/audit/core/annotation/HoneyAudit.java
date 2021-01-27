package red.honey.audit.core.annotation;

import red.honey.audit.common.constant.OperationType;

import java.lang.annotation.*;

/**
 * @author yangzhijie
 * @date 2021/1/19 9:39
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HoneyAudit {

    /**
     * the application id,default ""
     */
    String appId() default "";

    /**
     * the operator id,default ""
     */
    String operatorId() default "";

    /**
     * the operator name, default ""
     */
    String operatorName() default "";

    /**
     * the operator remote Ip, default ""
     */
    String operationIp() default "";

    /**
     * the operation type
     *
     * @see OperationType
     */
    OperationType operationType() default OperationType.VIEW;

    /**
     * the operation method ,default ""
     */
    String operationMethod() default "";

    /**
     * the operation parameters ,default ""
     */
    String[] operationParameters() default "";


}
