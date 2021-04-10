package com.gy.rural_convenience_platform.mapper;

import com.gy.rural_convenience_platform.entity.Worker;
import org.springframework.stereotype.Repository;
import tk.mybatis.MyMapper;

import java.util.List;
import java.util.Map;

@Repository
public interface WorkerMapper extends MyMapper<Worker> {
    void insertWorker(Worker worker);

    Integer insertWorkerImg(Map<String, Object> imgMap);

    Worker[] queryWorker(Map dataMap);

    Integer takeOrders(Map<String, Object> map);

    Integer updWorker(Map<String, String> dataMap);

    List<Worker> getWorkers(Map<String, Object> map);

    Worker getWorkerById(Map<String, Object> map);
}