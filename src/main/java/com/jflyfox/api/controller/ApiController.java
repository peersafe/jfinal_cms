package com.jflyfox.api.controller;

import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.kit.JsonKit;
import com.jflyfox.api.form.ApiResp;
import com.jflyfox.api.form.BaseApiForm;
import com.jflyfox.api.interceptor.ApiInterceptor;
import com.jflyfox.api.service.ApiService;
import com.jflyfox.api.util.ApiUtils;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.component.util.ArticleCountCache;
import com.jflyfox.component.util.JFlyFoxUtils;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.modules.admin.article.TbArticle;
import com.jflyfox.modules.front.service.FrontCacheService;
import com.jflyfox.util.StrUtils;

@ControllerBind(controllerKey = "/api")
@Before(ApiInterceptor.class)
public class ApiController extends BaseProjectController {

	ApiService service = new ApiService();

	/**
	 * api测试入口
	 * 
	 * 2016年10月3日 下午5:47:55 flyfox 369191470@qq.com
	 */
	public void index() {
		renderJson(new ApiResp().addData("notice", "api is ok!"));
	}

	/**
	 * 开关调试日志
	 * 
	 * 2016年10月3日 下午5:47:46 flyfox 369191470@qq.com
	 */
	public void debug() {
		ApiUtils.DEBUG = !ApiUtils.DEBUG;
		renderJson(new ApiResp().addData("debug", ApiUtils.DEBUG));
	}

	/**
	 * 获取信息入口
	 * 
	 * 2016年10月3日 下午1:38:27 flyfox 369191470@qq.com
	 */
	public void action() {

		long start = System.currentTimeMillis();

		BaseApiForm from = getForm();
		if (StrUtils.isEmpty(from.getMethod())) {
			String method = getPara();
			from.setMethod(method);
		}

		// 调用接口方法
		ApiResp resp = service.action(from);
		// 没有数据输出空
		resp = resp == null ? new ApiResp() : resp;
		
		// 调试日志
		if (ApiUtils.DEBUG) {
			log.info("API DEBUG ACTION \n[from=" + from + "]" //
					+ "\n[resp=" + JsonKit.toJson(resp) + "]" //
					+ "\n[time=" + (System.currentTimeMillis() - start) + "ms]");
		}
		
		if(ApiUtils.API_RETURNJSONP){
			writeJson(resp);
		}else{
			renderJson(resp);
		}
		

	}
	/*@Before(ApiInterceptor.class)
	public TbArticle addArticleCount(TbArticle article) {
		if (article != null) {
			// 更新浏览量
			 * String key = getSessionAttr(JFlyFoxUtils.USER_KEY);
			if (key != null) {
				ArticleCountCache.addCountView(article, key);
				// 缓存访问量和评论数
				new FrontCacheService().addArticleCount(article);
			}
			
		}
		return article;
	}
*/
	public  void writeJson(Object object) {
		try {
			String callback = getRequest().getParameter("callback");

			String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			//System.out.println(json+"   ==json");
			getResponse().setContentType("text/html;charset=utf-8");
			getResponse().getWriter().write(callback+"(" + json + ")");
			getResponse().getWriter().flush();
			getResponse().getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BaseApiForm getForm() {
		BaseApiForm form = getBean(BaseApiForm.class, null);
		return form;
	}

}
