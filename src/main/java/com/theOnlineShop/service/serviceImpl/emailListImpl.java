package com.theOnlineShop.service.serviceImpl;

import com.theOnlineShop.domain.emailVerificationEntity;
import com.theOnlineShop.service.emailListInter;
import org.springframework.stereotype.Service;

@Service
public class emailListImpl implements emailListInter {


    @Override
    public boolean emailIsExist(emailVerificationEntity email) {
        return false;
    }

    @Override
    public void updateEmailVeri(emailVerificationEntity email) {

    }
}
