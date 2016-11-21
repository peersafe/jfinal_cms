package com.jflyfox.api.util;

import java.util.HashMap;
import java.util.Map;

import com.jflyfox.api.constant.ApiConstant;
import com.jflyfox.api.form.ApiResp;
import com.jflyfox.api.service.IApiLogic;
import com.jflyfox.api.service.impl.ApiV100Logic;
import com.jflyfox.util.Config;

public class ApiUtils {

	private static final Map<String, IApiLogic> map = new HashMap<String, IApiLogic>();
	
	/**
	 * API是否返回JSONP格式数据
	 */
	public static final boolean API_RETURNJSONP = Config.getToBoolean("CMS.API_RETURNJSONP");
	/**
	 * 调试日志
	 */
	public static boolean DEBUG = false;
	
	static {
		map.put("1.0.0", new ApiV100Logic());
		map.put("1.0.1", new ApiV100Logic());
	}
	
	public static IApiLogic getApiLogic(String version){
		return map.get(version);
	}
		
	/**
	 * api关闭resp
	 * 
	 * 2016年9月29日 上午11:44:38 flyfox 369191470@qq.com
	 * 
	 * @return
	 */
	public static ApiResp getServerMaintain() {
		return new ApiResp().setCode(ApiConstant.CODE_SERVER_MAINTAIN).setMsg(ApiConstant.MSG_SERVER_MAINTAIN);
	}
	
	/**
	 * 版本错误返回resp
	 * 
	 * 2016年9月29日 上午11:44:38 flyfox 369191470@qq.com
	 * 
	 * @return
	 */
	public static ApiResp getVersionErrorResp() {
		return new ApiResp().setCode(ApiConstant.CODE_VERSION_ERROR).setMsg(ApiConstant.MSG_VERSION_ERROR);
	}
	
	/**
	 * ip黑名单返回resp
	 * 
	 * 2016年9月29日 上午11:44:38 flyfox 369191470@qq.com
	 * 
	 * @return
	 */
	public static ApiResp getIpBlackResp() {
		return new ApiResp().setCode(ApiConstant.CODE_IP_BLACK).setMsg(ApiConstant.MSG_IP_BLACK);
	}
	
	/**
	 * 方法调用错误resp
	 * 
	 * 2016年9月29日 上午11:44:38 flyfox 369191470@qq.com
	 * 
	 * @return
	 */
	public static ApiResp getMethodError() {
		return new ApiResp().setCode(ApiConstant.CODE_METHOD_ERROR).setMsg(ApiConstant.MSG_METHOD_ERROR);
	}
	
}
