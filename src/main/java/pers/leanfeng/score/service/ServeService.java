package pers.leanfeng.score.service;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;
import pers.leanfeng.score.model.Order;
import pers.leanfeng.score.model.Serve;
import pers.leanfeng.score.repository.ServeRepository;
import pers.leanfeng.score.utils.WaitingUtil;

import java.util.Date;
import java.util.List;

/**
 * @author leanfeng
 * @date 2025年03月02日
 */
@Service
public class ServeService {
    @Autowired
    private ServeRepository serveRepository;
    String topic="Waiting";

    public List<Serve> getServeList(){
        return serveRepository.findAll();
    }

    public Date getEndTime(){
        Date endTime = WaitingUtil.getEndTime();
        if (endTime==null){
            endTime = new Date();
            WaitingUtil.setEndTime(endTime);
        }
        return endTime;
    }

    public Integer getWaitingCount(){
        return WaitingUtil.getCount();
    }


}
