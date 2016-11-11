package app.data;

import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Created by shaopengxiang on 2016/11/10.
 */
public class Shiyi {

    @Id
    public String id;

    public String word;
    public String yin;
    public String data;

    public Shiyi() {
    }

    public Shiyi(String word, String yin, String data) {
        this.word = word;
        this.yin = yin;
        this.data = data;
    }
}
