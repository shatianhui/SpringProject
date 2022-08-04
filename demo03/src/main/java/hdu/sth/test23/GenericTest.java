package hdu.sth.test23;

import org.springframework.core.GenericTypeResolver;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * description: GenericTest <br>
 * date: 2022/8/4 17:16 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class GenericTest {
    public static void main(String[] args) {
        // 方式1 jdk  API
        Type type = StudentDao.class.getGenericSuperclass(); // 获得带泛型的父类型
        //System.out.println(type.toString());
        // 判断是否带泛型参数  类<>  进行强转
        if (type instanceof ParameterizedType){
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            System.out.println(actualTypeArguments[0]);
        }

        // 方式2 spring API
        Class<?> typeArgument = GenericTypeResolver.resolveTypeArgument(StudentDao.class, BaseDao.class);
        System.out.println(typeArgument);
    }
}
