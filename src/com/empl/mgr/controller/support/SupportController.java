package com.empl.mgr.controller.support;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.empl.mgr.annotation.SecureValid;
import com.empl.mgr.constant.MethodType;
import com.empl.mgr.constant.SessionKey;
import com.empl.mgr.model.TeAccount;
import com.empl.mgr.service.AccountService;
import com.empl.mgr.service.DepartmentService;
import com.empl.mgr.service.EmployeesService;
import com.empl.mgr.service.ModuleService;
import com.empl.mgr.service.PositionService;
import com.empl.mgr.support.JSONReturn;
import com.empl.mgr.support.RandomValidateCode;
import com.empl.mgr.utils.EncryptUtil;
import com.empl.mgr.utils.MailInfo;
import com.empl.mgr.utils.SendMail;

@Scope
@Controller
public class SupportController extends AbstractController {

	@Autowired
	private ModuleService moduleService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private PositionService positionService;
	@Autowired
	private EmployeesService employeesService;

	@RequestMapping(value = "0/findVerifydCode")
	public void findVerifydCode(HttpServletRequest request, HttpServletResponse response) {
		RandomValidateCode.getRandcode(request, response);
	}

	@ResponseBody
	@RequestMapping(value = "0/acctLogin")
	public JSONReturn login(@RequestParam String name, @RequestParam String pass, @RequestParam String verify,
			HttpServletRequest request) {
		String sessionVerify = (String) request.getSession().getAttribute(SessionKey.VALIDATE_CODE);
		if (StringUtils.isEmpty(sessionVerify) || !verify.equalsIgnoreCase(sessionVerify))
			return JSONReturn.buildFailure("登录失败, 验证码出错!");
		return accountService.login(name, pass, request);
	}

	@ResponseBody
	@RequestMapping(value = "findBreadcrumb")
	public JSONReturn findBreadcrumb(String moduleCode) {
		return moduleService.findBreadcrumb(moduleCode);
	}

	@ResponseBody
	@RequestMapping(value = "exit")
	public JSONReturn exit(HttpSession httpSession) {
		return accountService.exit(httpSession);
	}

	@ResponseBody
	@RequestMapping(value = "mdoifyPass")
	public JSONReturn mdoifyPass(@RequestParam String oldpassword,@RequestParam String password, HttpSession httpSession) {
		return accountService.mdoifyPass(oldpassword,password, acctName(httpSession));
	}
	
	/**
	 * 找回密码发送邮件
	 * */
	@ResponseBody
	@RequestMapping(value = "0/passEmail")
	public JSONReturn sendmail(@RequestParam String acctName,@RequestParam String code,HttpServletRequest request) {
		String sessionVerify = (String) request.getSession().getAttribute(SessionKey.VALIDATE_CODE);
		if (StringUtils.isEmpty(sessionVerify) || !code.equalsIgnoreCase(sessionVerify))
			return JSONReturn.buildFailure("验证码错误，请重新输入!");
        try {
            TeAccount account=accountService.findAccountByName(acctName);
            if (account!=null) {
                String secretKey = UUID.randomUUID().toString(); // 密钥
                Timestamp outDate = new Timestamp(System.currentTimeMillis() + 30 * 60 * 1000);// 30分钟后过期
                long date = outDate.getTime() / 1000 * 1000;// 忽略毫秒数  mySql 取出时间是忽略毫秒数的
                account.setOutDate(outDate);
                account.setValidataCode(secretKey);
                accountService.saveAccount(account);
                String key =account.getAcctName() + "$" + date + "$" + secretKey;
                String digitalSignature = EncryptUtil.encodeMD5String(key);// 数字签名
                String path = request.getContextPath();
                String basePath = request.getScheme() + "://"
                        + request.getServerName() + ":"
                        + request.getServerPort() + path + "/";
                String resetPassHref = basePath + "mgr/0/checkLink?sid="
                        + digitalSignature +"&userName="+account.getAcctName();
                String emailContent = "请勿回复本邮件.点击下面的链接,重设密码<br/><a href="
                        + resetPassHref + " target='_BLANK'>" + resetPassHref
                        + "</a>  或者    <a href=" + resetPassHref
                        + " target='_BLANK'>点击我重新设置密码</a>"
                        + "<br/>tips:本邮件超过30分钟,链接将会失效，需要重新申请'找回密码'";
                //--设置邮件服务器开始  
                MailInfo mailinfo=new MailInfo();
                mailinfo.setMailServerHost("smtp.163.com");  
                mailinfo.setMailServerPort("25");  
                mailinfo.setValidate(true);  
                mailinfo.setUserName("ssyabgpt");  
                mailinfo.setPassword("ssyabgpt163com");  
                //--设置邮件服务器结束  
                mailinfo.setFromAddress("ssyabgpt@163.com");//邮件发送者的地址  
                //设置接受用户  
                //String []ToAddress={account.getEmail()};
                String []ToAddress={"343785939@qq.com"};
                mailinfo.setToAddress(ToAddress);
                mailinfo.setSubject("办公平台密码找回邮件"); 
                mailinfo.setContent(emailContent);
                SendMail sm=new SendMail();  
                if (sm.sendHtmlMail(mailinfo)) {
                    return JSONReturn.buildSuccess("重置密码邮件已经发送，请登录邮箱进行重置！");
                }
            } else {
            	return JSONReturn.buildFailure("用户名不存在！");
            }
        } catch (Exception e) {
            // TODO: handle exception 
            e.printStackTrace();
        }
        return JSONReturn.buildFailure("系统出现异常，请稍候重试！");
    }
	
	/**
	 * 验证找回密码链接
	 * @throws IOException 
	 * @throws ServletException 
	 * */
	@ResponseBody
	@RequestMapping(value="0/checkLink")
	public void checkResetLink(@RequestParam String sid,@RequestParam String userName,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		if (sid.equals("")  || userName.equals("")) {
        	request.setAttribute("mag", "链接不完整,请重新生成");
        	request.getRequestDispatcher("/error.jsp").forward(request,response);
        }
        TeAccount account=accountService.findAccountByName(userName);
        if (account!=null) {
            Timestamp outDate = (Timestamp) account.getOutDate();
             if(outDate.getTime() <= System.currentTimeMillis()){ //表示已经过期
            	request.setAttribute("mag", "链接已经过期,请重新申请找回密码.");
             	request.getRequestDispatcher("/error.jsp").forward(request,response);
             }
             String key = account.getAcctName()+"$"+outDate.getTime()/1000*1000+"$"+account.getValidataCode();//数字签名
             String digitalSignature = EncryptUtil.encodeMD5String(key);// 数字签名
              if(!digitalSignature.equals(sid)) {
            	request.setAttribute("mag", "链接不正确,是否已经过期了?重新申请吧.");
               	request.getRequestDispatcher("/error.jsp").forward(request,response);
              }else {
                //链接验证通过 转到修改密码页面
            	request.setAttribute("userName", account.getAcctName());
            	request.setAttribute("key", account.getValidataCode());
                request.getRequestDispatcher("/success.jsp").forward(request,response);
            }
        }else {
        	request.setAttribute("mag", "链接错误,无法找到匹配用户,请重新申请找回密码.");
            request.getRequestDispatcher("/error.jsp").forward(request,response);
        }
    }
	
	/**
	 * 找回密码修改密码
	 * */
	@ResponseBody
	@RequestMapping(value = "0/updatePass")
	public JSONReturn updatePass(@RequestParam String key,@RequestParam String userName,@RequestParam String password) {
		if (StringUtils.isEmpty(key)||StringUtils.isEmpty(userName)||StringUtils.isEmpty(password)) {
			return JSONReturn.buildFailure("信息不完整！");
		}
		TeAccount account=accountService.findAccountByName(userName);
		if (account!=null) {
			if (!key.equals(account.getValidataCode())) {
				return JSONReturn.buildFailure("请不要非法操作！");
			}
			account.setAcctPassword(EncryptUtil.encodeMD5String(password));
			account.setValidataCode("");
			accountService.saveAccount(account);
			return JSONReturn.buildSuccess("密码修改成功！");
		}else{
			return JSONReturn.buildFailure("无法找到匹配用户！");
		}
	}

	/**
	 * 获取所有部门列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findDepartment")
	public JSONReturn findDepartment() {
		return departmentService.findAllDepartment();
	}

	/**
	 * 根据部门ID号获取职位列表
	 * @param deptId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findPositionByDeptId")
	public JSONReturn findPositionByDeptId(@RequestParam long deptId) {
		return positionService.findPositionByDeptId(deptId);
	}
	
	@ResponseBody
	@RequestMapping(value = "uploadImg")
	public Map<String, Object> uploadImg(MultipartFile imgFile, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONReturn jsonReturn = employeesService.uploadImg(imgFile, request, response);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", jsonReturn.isHead() ? 0 : 1);
		map.put(jsonReturn.isHead() ? "url" : "message", jsonReturn.getBody());
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "findEmployeesRecord")
	@SecureValid(type = MethodType.FIND, desc = "获取员工相关记录", code = { "01001", "01002", "01004" })
	public JSONReturn findEmployeesRecord(@RequestParam long emplId) {
		return employeesService.findEmployeesRecord(emplId);
	}

	@RequestMapping(value="modifyInfo")
	public void modifyInfo(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String acctName=(String)request.getSession().getAttribute(SessionKey.MODULEACCTNAME);
		TeAccount account = accountService.findAccountByName(acctName);
		request.getSession().setAttribute("acctId", account.getAcctId());
		request.getSession().setAttribute("acctName", acctName);
		request.getSession().setAttribute("nickName", account.getAcctNickname());
		request.getSession().setAttribute("mobile", account.getMobile());
		request.getSession().setAttribute("telephone", account.getTelephone());
		request.getSession().setAttribute("email", account.getEmail());
		request.getRequestDispatcher("/modify_info.jsp").forward(request, response);
	}
}
