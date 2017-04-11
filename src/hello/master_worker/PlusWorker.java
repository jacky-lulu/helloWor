package hello.master_worker;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
public class PlusWorker extends Worker {
    @Override
    public Object handle(Object input) {

        Integer i =(Integer)input;
        return i*i*i;
    }
}
