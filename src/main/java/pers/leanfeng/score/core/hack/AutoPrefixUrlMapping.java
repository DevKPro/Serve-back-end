package pers.leanfeng.score.core.hack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;


public class AutoPrefixUrlMapping extends RequestMappingHandlerMapping {
    @Value("${score.api-package}")
    private String apiPackagePath;
    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo mappingInfo = super.getMappingForMethod(method, handlerType);
        if(mappingInfo!=null){
            String prefix = this.getPrefix(handlerType);
//            RequestMappingInfo newMappingInfo = RequestMappingInfo.paths(prefix).build().combine(mappingInfo);  // 版本不同
            RequestMappingInfo newMappingInfo = RequestMappingInfo.paths(prefix).options(super.getBuilderConfiguration()).build().combine(mappingInfo);
            return newMappingInfo;
        }
        return mappingInfo;
    }
    private String getPrefix(Class<?> handlerType){
        String packageName = handlerType.getPackageName();
        String dotPath = packageName.replace(this.apiPackagePath, "");
        return dotPath.replace(".","/");
    }
}
