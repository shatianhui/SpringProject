package hdu.sth.test13;

import org.springframework.cglib.core.Signature;

import java.lang.reflect.InvocationTargetException;

/**
 * description: TargetFastClass <br>
 * date: 2022/7/30 10:38 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class TargetFastClass {
    static Signature s0 = new Signature("save", "()V");
    static Signature s1 = new Signature("save", "(I)V");
    static Signature s2 = new Signature("save", "(J)V");

    /**
     * 用来获取方法的编号
     * Target
     * save()  0
     * save(int)  1
     * save(long) 2
     * @param signature  签名，包含方法的参数，返回值
     * @return
     */
    public int getIndex(Signature signature){
        if (s0.equals(signature)){
            return 0;
        }else if (s1.equals(signature)){
            return 1;
        }else if (s2.equals(signature)){
            return 2;
        }
        return -1;
    }

    /**
     * 根据编号正常调用目标方法
     * @param index
     * @param target
     * @param args
     * @return
     */
    public Object invoke(int index, Object target, Object[] args){
        if (index==0){
            ((Target)target).save();
            return null;
        }else if (index==1){
            ((Target)target).save((Integer) args[0]);
            return null;
        }else if (index==2){
            ((Target)target).save((Long) args[0]);
            return null;
        } else {
            throw new RuntimeException("无此方法");
        }
    }

    public static void main(String[] args) {
        TargetFastClass fastClass=new TargetFastClass();
        int index = fastClass.getIndex(new Signature("save", "()V"));
        fastClass.invoke(index, new Target(), new Object[0]);
    }

}
