package com.theOnlineShop.controller.logic;

import com.theOnlineShop.domain.httpReponseEntity;
import com.theOnlineShop.domain.userEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class indexLogicController {
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

        System.out.println(userName+" "+email);
        return new httpReponseEntity("success","Have sent the verification code to your email, please have a check.");
    }


    //shiro登录
    private String login(String txtUsername, String txtPassword){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(txtUsername,txtPassword);

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
