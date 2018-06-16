package com.store.service.impl;

import com.store.dao.AreaDao;
import com.store.entity.Area;
import com.store.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //tell springIOC that this class need its management
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;
    @Override
    public List<Area> getAreaList() {

        return areaDao.queryArea();
    }
}
