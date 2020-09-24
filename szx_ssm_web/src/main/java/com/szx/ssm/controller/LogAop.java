package com.szx.ssm.controller;
import com.szx.ssm.domain.SysLog;
import com.szx.ssm.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import java.lang.reflect.Method;
import java.util.Date;

@Component//不是controller，只是一个bean
@Aspect
public class LogAop {
   // @Autowired
   // private HttpServletRequest request;
    @Autowired
    private ISysLogService sysLogService;

    private Date visitTime;//开始时间
    private Class clazz;//访问的类
    private Method method;//访问的方法

    //前置通知,主要是获取开始时间，执行的类是哪一个，执行的是哪一个方法
    @Before("execution(* com.szx.ssm.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException{
        visitTime = new Date();//当前时间就是开始访问的时间
        clazz = jp.getTarget().getClass();//具体访问的类对象
        String methodName = jp.getSignature().getName();//获取访问方法的名称
        Object[] args = jp.getArgs();//获取访问的方法的参数
        if (args == null || args.length == 0) {
            method = clazz.getMethod(methodName);//只能获取无参数的方法
        }else{
            Class[] classArgs = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                classArgs[i] = args[i].getClass();
            }

            clazz.getMethod(methodName,classArgs);
        }
    }
    //后置通知
    @After("execution(* com.szx.ssm.controller.*.*(..))")
    public void doAfter(JoinPoint jp) throws Exception{
        long time = new Date().getTime() - visitTime.getTime();//获取了访问的时常
        /**
         * 通过反射去操作url
         */
        String url ="";
        if (clazz != null && method!= null && clazz != LogAop.class){
            //获取类上的注解值@RequestMapping("/orders")
            RequestMapping classAnnotation = (RequestMapping)clazz.getAnnotation(RequestMapping.class);
            if (classAnnotation != null){
                String[] classValue = classAnnotation.value();
                //获取方法上的@RequestMapping("")
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                if (methodAnnotation != null){
                    String[] methodValue = methodAnnotation.value();
                    url = classValue[0] + methodValue[0];

                    //获取访问的IP
                    // String ip = request.getRemoteAddr();
                    //获取当前操作的用户
                    SecurityContext context = SecurityContextHolder.getContext();//从上下文获取当前的登陆的用户
                    User user = (User)context.getAuthentication().getPrincipal();
                    String username = user.getUsername();
                    //将日志相关信息封装到SysLog中
                    SysLog sysLog = new SysLog();
                    sysLog.setExecutionTime(time);
                    //sysLog.setIp(ip);
                    sysLog.setUrl(url);
                    sysLog.setUsername(username);
                    sysLog.setVisitTime(visitTime);
                    sysLog.setMethod("[类名]" + clazz.getName() +"[方法名]" + method.getName());

                    //调用Service完成操作
                    sysLogService.save(sysLog);
                }
            }

        }

    }


}
