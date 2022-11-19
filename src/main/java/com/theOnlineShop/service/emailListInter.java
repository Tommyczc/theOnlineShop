package com.theOnlineShop.service;

import com.theOnlineShop.domain.emailVerificationEntity;

public interface emailListInter {
public boolean emailIsExist(emailVerificationEntity email);
public void updateEmailVeri(emailVerificationEntity email);
}
