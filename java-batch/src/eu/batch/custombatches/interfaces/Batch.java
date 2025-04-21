package eu.batch.custombatches.interfaces;

public interface Batch<T> {

    void process(T t) throws Exception;
    void write(T t, Object object) throws Exception;
    String read(T t) throws Exception;

}
