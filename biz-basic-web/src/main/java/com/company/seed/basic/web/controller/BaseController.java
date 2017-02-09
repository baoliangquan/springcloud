package com.company.seed.basic.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.company.seed.basic.web.bean.*;
import com.company.seed.basic.web.common.checkrequest.CheckRequestParamInterceptor;
import com.company.seed.basic.web.common.encrypt.CheckResult;
import com.company.seed.basic.web.common.encrypt.helper.RSAEncryptHelper;
import com.company.seed.basic.web.common.validation.ValidationForm;
import com.company.seed.basic.web.common.validation.ValidationResult;
import com.company.seed.basic.web.common.validation.pool.ValidationPoolBean;
import com.company.seed.basic.web.common.uitl.CommonWebUtil;
import com.company.seed.common.util.CommonStringUtil;
import com.company.seed.model.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller 基类， 所有的controller 需要从此继承
 *
 * @author yoara
 */
public class BaseController {
    public static SerializerFeature[] features = {
            SerializerFeature.WriteMapNullValue,//输出值为null的字段
            SerializerFeature.WriteNullNumberAsZero, //数值字段如果为null,输出为0
            SerializerFeature.WriteNullStringAsEmpty, //字符类型字段如果为null,输出为""
            SerializerFeature.WriteNullListAsEmpty,//List字段如果为null,输出为[]
            SerializerFeature.WriteNullBooleanAsFalse,//Boolean字段如果为null,输出为false,而非null
            SerializerFeature.DisableCircularReferenceDetect};//禁止循环引用检测

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    protected ValidationPoolBean validationPoolBean;
    @Resource
    protected RSAEncryptHelper rsaEncryptHelper;
    /**
     * 校验传递的参数是否符合要求，传递的DO须通过JSR-303标注
     * @param param 待校验参数
     */
    protected ValidationResult validationParam(ValidationForm param){
        return validationPoolBean.validationParam(param);
    }


    /**
     * 校验RSA参数加密，省事儿模式{@link #rsaCheck(String, String, boolean)}
     * @return
     */
    protected CheckResult rsaCheck(){
        String encryptStr = getString(RSAEncryptHelper.RSA_PARAM_ENCRYPTSTR);
        boolean onlyMD5 = getBoolean(RSAEncryptHelper.RSA_PARAM_ONLYMD5,false);
        String key = getString(RSAEncryptHelper.RSA_PARAM_KEY);
        return rsaCheck(encryptStr,key,onlyMD5);
    }
    /**
     * 校验RSA参数加密
     * @param encryptStr 前端加密返回的加密字符串
     * @param onlyMD5 是否仅用md5加密
     * @param key 私钥缓存key
     * @return
     */
    protected CheckResult rsaCheck(String encryptStr, String key, boolean onlyMD5){
        //组装待验证参数串
        Map<String,String>  paramMap = rsaEncryptHelper.makeCheckMap(getRequest());
        if(paramMap==null){ //如果参数为空
            return CheckResult.EMPTY_ENCRYPTS;
        }
        if(encryptStr==null){ //如果加密字符串为空
            return CheckResult.EMPTY_ENCRYPTSTR;
        }
        if(key==null){ //如果key数为空
            return CheckResult.EMPTY_ENCRYPT_KEY;
        }
        return rsaEncryptHelper.rsaCheck(encryptStr,paramMap,key,onlyMD5);
    }

    /**
     * 全局的异常处理
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler({Exception.class})
    public String exception(Exception e, HttpServletResponse response) {
        log.error("Controller 错误信息:" + e.getMessage(), e);
        return (String) outJson(response, returnWithCustomMessage(e.getMessage(),JsonCommonCodeEnum.E0005));
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public ServletContext getServletContext() {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext();
    }

    public int getInt(String name) {
        return getInt(name, 0);
    }

    public int getInt(String name, int defaultValue) {
        return CommonWebUtil.getInt(name,defaultValue);
    }

    /**
     * 查询request中的参数
     **/
    private String getRequestParameter(String name) {
        HttpServletRequest request = getRequest();
        if (request.getAttribute(CheckRequestParamInterceptor.PREFIX + name) != null) {
            return request.getAttribute(CheckRequestParamInterceptor.PREFIX + name).toString();
        }
        return request.getParameter(name);
    }


    public BigDecimal getBigDecimal(String name) {
        return getBigDecimal(name, null);
    }

    public BigDecimal getBigDecimal(String name, BigDecimal defaultValue) {
        return CommonWebUtil.getBigDecimal(name,defaultValue);
    }

    public String getString(String name) {
        return getString(name, null);
    }

    public String getString(String name, String defaultValue) {
        return CommonWebUtil.getString(name,defaultValue);
    }

    public String getFilterHtmlElementString(String name) {
        return getFilterHtmlElementString(name, null);
    }

    public String getFilterHtmlElementString(String name, String defaultValue) {
        return CommonStringUtil.filterHtmlElement(getString(name, defaultValue));
    }

    public void outPrint(HttpServletResponse response, Object result) {
        try {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print(result.toString());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public Object outJson(HttpServletResponse response, String result) {
        try {
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(result);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public String[] getIpAddr(HttpServletRequest request) {
        return CommonWebUtil.getIpAddr(request);
    }

    public String redirect(HttpServletRequest request, HttpServletResponse response, String path, String ftlName) {
        try {
            response.sendRedirect(request.getContextPath() + path);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return ftlName;
    }

    public String redirect(String path) {
        return "redirect:" + path;
    }

    public String getMACAddress(String ip) {
        return CommonWebUtil.getMACAddress(ip);
    }

    public Cookie getCookie(String cookieName) {
        return CommonWebUtil.getCookie(cookieName);
    }

    /**
     * 获取日期类型参数值
     *
     * @param name   参数名
     * @param format 日期格式
     * @return
     */
    public Date getDate(String name, String format) {
        return CommonWebUtil.getDate(name,format);
    }

    public double getDouble(String name) {
        return getDouble(name, 0);
    }

    public double getDouble(String name, double defaultValue) {
        return CommonWebUtil.getDouble(name,defaultValue);
    }

    /**
     * 如果name是1 就返回true,否则返回false
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public Boolean getBoolean(String name, boolean defaultValue) {
        return CommonWebUtil.getBoolean(name,defaultValue);
    }


    /**
     * 返回成功信息不带结果集
     **/
    protected String returnSuccessInfo() {
        return returnJsonInfoWithFilter(null, JsonCommonCodeEnum.C0000, null);
    }
    /**
     * 处理异常，返回出错信息
     **/
    protected String returnWrong(JsonReturnCodeEnum errCode) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", errCode.getStatus());
        result.put("message", errCode.getMessage());
        return JSON.toJSONString(result);
    }

    /**
     * 使用自定义消息
     **/
    protected String returnWithCustomMessage(String message, JsonReturnCodeEnum code) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", code.getStatus());
        result.put("message", message);
        return JSON.toJSONString(result);
    }

    /**
     * 返回信息
     **/
    protected String returnJsonInfo(Object json, JsonReturnCodeEnum code) {
        return returnJsonInfoWithFilter(json, code, null);
    }

    /**
     * 处理成功，返回信息带过滤器过滤属性
     **/
    protected String returnJsonInfoWithFilter(Object json,
                                              JsonReturnCodeEnum code, SerializeFilter[] filters) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", code.getStatus());
        result.put("message", code.getMessage());
        if (json != null) {
            result.put("result", json);
        }
        if (filters != null) {
            return JSON.toJSONString(result, filters, features);
        }
        return JSON.toJSONString(result, features);
    }

    /**
     * 返回指定格式信息
     **/
    protected String returnJsonInfoWithSerialize(Object json, JsonReturnCodeEnum code, SerializeConfig mapping) {
        return returnJsonInfoByMoreConfig(json, code, mapping, null);
    }

    /**
     * 返回json
     *
     * @param json             数据
     * @param code
     * @param mapping          数据字段配置
     * @param filter           过滤字段
     * @param replaceEnumBeans 替换枚举信息
     * @return
     */
    protected String returnJsonInfoByMoreConfig(Object json, JsonReturnCodeEnum code,
              SerializeConfig mapping, PropertyPreFilter filter, ReplaceEnumBean... replaceEnumBeans) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", code.getStatus());
        result.put("message", code.getMessage());
        if (json != null) {
            result.put("result", json);
        }
        boolean mappingFalg = false;
        if (mapping != null) {
            mappingFalg = true;
        }
        boolean filterFalg = false;
        if (filter != null) {
            filterFalg = true;
        }
        String resultJson = null;
        if (mappingFalg && filterFalg) {
            resultJson = JSON.toJSONString(result, mapping, filter, features);
        } else if (mappingFalg && (!filterFalg)) {
            resultJson = JSON.toJSONString(result, mapping, features);
        } else if (filterFalg && (!mappingFalg)) {
            resultJson = JSON.toJSONString(result, filter, features);
        } else if ((!filterFalg) && (!mappingFalg)) {
            resultJson = JSON.toJSONString(result, features);
        }

        return EnumFilterUtil.replaceEnum(resultJson, replaceEnumBeans);
    }

    /**
     * 获取SerializeConfig
     *
     * @param changeEnum
     * @param propertyName
     * @return
     */
    protected SerializeConfig getEnumFormatSerializer(Class<?> changeEnum, String propertyName) {
        if (changeEnum == null || propertyName == null) {
            return null;
        }
        SerializeConfig config = new SerializeConfig();
        config.put(changeEnum, new EnumFormatSerializer(propertyName));
        return config;
    }

    /**
     * 获取SerializeConfig
     *
     * @param changeEnums
     * @param propertyNames
     * @return
     */
    protected SerializeConfig getEnumFormatSerializer(Class<?>[] changeEnums, String[] propertyNames) {
        if (changeEnums == null || propertyNames == null) {
            return null;
        }
        if (changeEnums.length != propertyNames.length) {
            return null;
        }
        SerializeConfig config = new SerializeConfig();
        int len = changeEnums.length;
        for (int i = 0; i < len; i++) {
            config.put(changeEnums[i], new EnumFormatSerializer(propertyNames[i]));
        }
        return config;
    }

    /**
     * 将查询结果转化成页面page数据
     * <p>适用条件：</p>
     * <ul>
     * <li>1.使用房源列表相同的分页组件</li>
     * </ul>
     *
     * @param valueMap 包含有页面数据的返回参数，该参数将带到页面上显示
     * @param page     dao层查询返回的Pagination对象
     * @author yoara
     **/
    protected void makeResultPage(Map<String, Object> valueMap,
                                  Pagination<Object> page) {
        List<Object> roomCommentList = page.getItems();

        valueMap.put("dataList", roomCommentList);
        valueMap.put("recordCount", page.getRecordCount());
        valueMap.put("currentPage", page.getCurrentPage());
        valueMap.put("pagesize", page.getPageSize());
        valueMap.put("pageCount", page.getPageCount());
        if (page.getCurrentPage() > 1
                && page.getCurrentPage() <= page.getPageCount()) {
            valueMap.put("up", page.getCurrentPage() - 1);
        }
        if (page.getCurrentPage() >= 1
                && page.getCurrentPage() < page.getPageCount()) {
            valueMap.put("next", page.getCurrentPage() + 1);
        }
    }
}
