package pers.leanfeng.score.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.leanfeng.score.core.interceptor.ScopeLevel;
import pers.leanfeng.score.model.Serve;
import pers.leanfeng.score.service.ServeService;

import java.util.List;

/**
 * @author leanfeng
 * @date 2025年03月02日
 */
@RestController
@RequestMapping("serve")
public class ServeController {
    @Autowired
    private ServeService serveService;

    @GetMapping("serveList")
    @ScopeLevel()
    public List<Serve> getServeList(){
        return serveService.getServeList();
    }

    @GetMapping("waitingCount")
    @ScopeLevel()
    public Integer getWaitingCount(){
        return serveService.getWaitingCount();
    }
}
