package hdu.sth.test13;

import org.springframework.cglib.core.Signature;

/**
 * description: ProxyFastClass <br>
 * date: 2022/7/30 11:28 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class ProxyFastClass {

    static Signature s0 = new Signature("superSave", "()V");
    static Signature s1 = new Signature("superSave", "(I)V");
    static Signature s2 = new Signature("superSave", "(J)V");

    /**
     * 用来获取代理类原始方法的编号
     * Proxy
     * saveSuper()  0
     * saveSuper(int)  1
     * saveSuper(long) 2
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
     * @param proxy
     * @param args
     * @return
     */
    public Object invoke(int index, Object proxy, Object[] args){
        if (index==0){
            ((Proxy)proxy).superSave();
            return null;
        }else if (index==1){
            ((Proxy)proxy).superSave((Integer) args[0]);
            return null;
        }else if (index==2){
            ((Proxy)proxy).superSave((Long) args[0]);
            return null;
        } else {
            throw new RuntimeException("无此方法");
        }
    }

    public static void main(String[] args) {
        ProxyFastClass fastClass=new ProxyFastClass();
        int index = fastClass.getIndex(new Signature("superSave", "()V"));
        System.out.println(index);
        fastClass.invoke(index, new Proxy(), new Object[0]);
    }

}
