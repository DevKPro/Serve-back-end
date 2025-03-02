package pers.leanfeng.score.core.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// 从配置文件获取消息
@PropertySource(value = "classpath:config/exception-code.properties")   //类和配置文件关联
@ConfigurationProperties(prefix = "score") //指定文本前缀
@Component
public class ExceptionCodeConfiguration {
    //自动根据上面配置生成 codes->message 的map映射
    private Map<Integer,String> codes = new HashMap<>();

    public Map<Integer, String> getCodes() {
        return codes;
    }

    public void setCodes(Map<Integer, String> codes) {
        this.codes = codes;
    }

    public String getMessage(int code){
        String message = codes.get(code);
        return message;
    }

}
