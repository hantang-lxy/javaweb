package cn.kgc.tangcco.util.reflect;

import java.lang.reflect.Field;

/**
 * @author 李雪阳
 * @version 1.0
 */
public class BaseReflect {
    public static <T> T fatherToChildWithFiled(T father, T child) throws IllegalAccessException {
        //获取所有父类的属性
        Field[] declaredFields = father.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            //获取父类该属性的值
            Object obj = field.get(father);
            if (obj != null) {
                //为子类的该属性赋值
                field.set(child, obj);
            }
        }
        return child;
    }
}
