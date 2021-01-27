package red.honey.audit.common.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import red.honey.audit.common.exception.InstanceFieldCheckException;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * @author yangzhijie
 * @date 2020/10/14 10:23
 */

public class CommonUtil {


    private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);


    /**
     * build 32 bite UUID
     *
     * @return 32UUID
     */
    public static String get32Uid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * check the instance fields are all not null
     */
    public static <T> boolean checkInstanceFieldIsNull(T instance) {
        Class clazz = instance.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object fieldValue;
            try {
                fieldValue = field.get(instance);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new InstanceFieldCheckException(e);
            }
            if (fieldValue == null) {
                throw new InstanceFieldCheckException("check the instance of " +
                        clazz + " find the filed ： " + field.getName() + " is null");
            }
        }
        return true;
    }
}
