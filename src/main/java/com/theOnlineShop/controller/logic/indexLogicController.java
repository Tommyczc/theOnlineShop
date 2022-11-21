package com.theOnlineShop.controller.logic;

import com.theOnlineShop.domain.emailVerificationEntity;
import com.theOnlineShop.domain.httpReponseEntity;
import com.theOnlineShop.domain.roleEntity;
import com.theOnlineShop.domain.userEntity;
import com.theOnlineShop.security.emailVerification.emailUtils;
import com.theOnlineShop.security.encryption.AesUtils;
import com.theOnlineShop.security.encryption.Md5Utils;
import com.theOnlineShop.service.emailListInter;
import com.theOnlineShop.service.roleListInter;
import com.theOnlineShop.service.userListInter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class indexLogicController {

    @Autowired
    private userListInter userList;

    @Autowired
    private emailListInter emailList;

    @Autowired
    private roleListInter roleList;

    @Value("${personal.EncryptionKey.aes-key}")
    private String aesKey;

    @Autowired
    private emailUtils emailTools;

    @Value("${personal.role.adminNum}")
    private int adminNum;

    @Value("${personal.role.superAdminNum}")
    private int superAdminNum;

    @RequestMapping(value=("/loginLogic"), method= RequestMethod.POST)
    public String loginLogic(userEntity user, Model model){
        String info=login(user.getUserName(),user.getPassword());
        if(!info.equals("success")){
            model.addAttribute("failInfo","Fail Information: "+info);
            return "index";
        }
        return "redirect:/theOnlineShop/welcomePage";
    }

    @RequestMapping(value=("/emailCheck"), method= RequestMethod.POST)
    @ResponseBody
    public httpReponseEntity emailCheck(@RequestParam(value="userName",required = true) String userName,
                                        @RequestParam(value="email",required = true) String email){

        try {
            userEntity user = new userEntity();
            user.setUserName(AesUtils.encrypt(userName,aesKey));
            user.setEmail(AesUtils.encrypt(email,aesKey));
            //检测邮件和用户名是否重复使用
            boolean isDuplicated = userList.emailDuplicateCheck(user);
            boolean nameIsDuplicated=userList.userNameDuplicateCheck(user);
            if(!nameIsDuplicated){
                return new httpReponseEntity("error","User name is duplicated");
            }
            else if(!isDuplicated){
                return new httpReponseEntity("error","Other user has use this email");
            }

            //生成验证码 上传到数据库 发送验证码到指定邮箱
            emailTools.emailVerification(email,"注册账号");

        }catch(Exception e){
            System.out.println(e.getMessage());
            return new httpReponseEntity("error",e.getMessage());
        }
        return new httpReponseEntity("success","Have sent the verification code to your email, please have a check.");
    }

    @RequestMapping(value=("/registerLogic"), method= RequestMethod.POST)
    @ResponseBody
    public httpReponseEntity registerLogic(@RequestParam(value="userName",required = true) String userName,
                                           @RequestParam(value="password",required = true) String password,
                                           @RequestParam(value="age",required = false) String age,
                                           @RequestParam(value="email",required = true) String email,
                                           @RequestParam(value="address",required = false) String address,
                                           @RequestParam(value="code",required = true) String code){

        //先检测验证码是否正确
        try{
            emailVerificationEntity emailEntity=new emailVerificationEntity();
            emailEntity.setEmail(AesUtils.encrypt(email,aesKey));
            emailEntity.setCode(AesUtils.encrypt(code,aesKey));
            emailEntity.setTime(new Date());
            boolean checkVeri=emailList.checkEmailVeri(emailEntity);
            if(!checkVeri){
                return new httpReponseEntity("error", "Please enter the correct verification code !!");
            }
            else{
                //开始删除验证码记录
                boolean i=emailList.deleteEmailVeri(emailEntity);
                if(!i){return new httpReponseEntity("error","Could not delete the verification record!!");}
            }

            //开始插入用户信息
            userEntity user=new userEntity(AesUtils.encrypt(userName,aesKey), Md5Utils.encrypt(password));
            user.setEmail(AesUtils.encrypt(email,aesKey));
            if(age!=null){
                user.setAge(AesUtils.encrypt(age,aesKey));
            }
            if(address!=null){
                user.setAddress(AesUtils.encrypt(address,aesKey));
            }
            boolean registered=userList.register(user);
            if(!registered){
                return new httpReponseEntity("error","Cannot register this user!");
            }

            //插入用户权限（正常是user）
            roleEntity role=new roleEntity(AesUtils.encrypt(userName,aesKey),"user");
            int i=roleList.insertRole(role);
            if(i==0){return new httpReponseEntity("error","Cannot insert role");}
            else if(i==2){return new httpReponseEntity("error","Has reach the maximun user role number!!!!");}

        }catch(Exception e){
            System.out.println(e.getMessage());
            return new httpReponseEntity("error",e.getMessage());}
        return new httpReponseEntity("success","register success!");
    }


    //shiro登录
    private String login(String username, String password){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);

        try{
            subject.login(token);
        }catch (UnknownAccountException e){
            return "user does not exist";
        }catch (AuthenticationException e) {
            return "fail authorization";
        }
        return "success";
    }
}
