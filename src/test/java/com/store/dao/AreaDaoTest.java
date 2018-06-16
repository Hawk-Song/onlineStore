package com.store.dao;

import com.store.BaseTest;
import com.store.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class AreaDaoTest extends BaseTest {
    @Autowired
    private AreaDao areaDao;

    @Test
    public void TestQueryArea(){
        List<Area> areaList = areaDao.queryArea();
        assertEquals(2, areaList.size());
    }
}
