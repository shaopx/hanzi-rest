package app;

import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Created by shaopengxiang on 2016/11/9.
 */
public class Testcollections {

    @Id
    public String id;

    public String key;
    public List<String> words;

    public Testcollections() {
    }

    public Testcollections(String key, List<String> words) {
        this.key = key;
        this.words = words;
    }


}
