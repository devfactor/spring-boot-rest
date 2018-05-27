package com.devfactor.service;

import com.devfactor.dao.MyDao;
import com.devfactor.exception.NumberNotFoundException;
import com.devfactor.model.MyNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class MyDataService {
    private static final Logger logger = LoggerFactory.getLogger(MyDataService.class);
    private final MyDao myDao;

    @Autowired
    public MyDataService(MyDao myDao) {
        this.myDao = myDao;
    }

    public List<MyNumber> listMyData() {
        logger.debug("In MyDataService.listMyData");
        List<MyNumber> result = myDao.getAllData();
        return result;
    }

    public MyNumber loadMyDataById(int id) throws NumberNotFoundException {
        logger.debug("In MyDataService.loadMyDataById for id " + id);
        MyNumber myNumber = myDao.getDataById(id);
        return myNumber;
    }

    public void updateNumbers(List<MyNumber> numbers) {
        myDao.updateNumbers(numbers);
    }

    public void createNumbers(List<MyNumber> numbers) {
        myDao.createNumbers(numbers);
    }

    public void deleteNumber(int id) {
        myDao.deleteNumber(id);
    }
}
