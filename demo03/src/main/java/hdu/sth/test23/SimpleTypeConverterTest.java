package hdu.sth.test23;

import org.springframework.beans.SimpleTypeConverter;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * description: SimpleTypeConventer <br>
 * date: 2022/8/3 16:50 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class SimpleTypeConverterTest {
    public static void main(String[] args) {
        SimpleTypeConverter simpleTypeConverter=new SimpleTypeConverter();
        Double data1 = simpleTypeConverter.convertIfNecessary("123.4", Double.class);  //把字符串转化成Double类型
        Date data2 = simpleTypeConverter.convertIfNecessary("1999/07/21", Date.class);  //把字符串转换成日期类型
        System.out.println(data1);
        System.out.println(data2);
    }
}
