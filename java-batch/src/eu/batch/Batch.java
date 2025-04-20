package eu.batch;

public interface Batch {

    void process(String filePath) throws Exception;
    void write(String filePath, String content) throws Exception;
    String read(String filePath) throws Exception;

}
