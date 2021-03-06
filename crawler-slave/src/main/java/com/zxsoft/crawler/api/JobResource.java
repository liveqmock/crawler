package com.zxsoft.crawler.api;

import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

import org.restlet.data.Form;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zxsoft.crawler.api.JobStatus.State;

public class JobResource extends ServerResource {
	
	private static Logger LOG = LoggerFactory.getLogger(JobResource.class);
	
	public static final String PATH = "jobs";
	public static final String DESCR = "任务管理";

	@Get("json")
	public Object retrieve() throws Exception {
		String cid = null;
		String jid = null;
		String cmd = null;
		String state = null; // 状态
		Form form = getQuery();
		cid = (String) getRequestAttributes().get(Params.CRAWL_ID);
		jid = (String) getRequestAttributes().get(Params.JOB_ID);
		cmd = (String) getRequestAttributes().get(Params.CMD);
		state = (String) getRequestAttributes().get(Params.JOB_STATE);
		if (form != null) {
			String v = form.getFirstValue(Params.CRAWL_ID);
			if (v != null)
				cid = v;
			v = form.getFirstValue(Params.JOB_ID);
			if (v != null)
				jid = v;
			v = form.getFirstValue(Params.CMD);
			if (v != null)
				cmd = v;
			v = form.getFirstValue(Params.JOB_STATE);
			if (v != null)
				state = v;
		}
		
		if (state != null) {
			if (state.equals("history")) {
				return SlaveApp.jobMgr.list(cid, State.FINISHED);
			} else if (state.equals("running")) {
				return SlaveApp.jobMgr.list(cid, State.RUNNING);
			} else {
				return SlaveApp.jobMgr.list(cid, State.ANY);
			}
		}

		// NO parameters
		if (cid == null && jid == null && cmd == null) {
			return SlaveApp.jobMgr.list();
		}

		if (jid == null) {
			return SlaveApp.jobMgr.list(cid, State.ANY);
		} else {
			if (cmd == null) {
				return SlaveApp.jobMgr.get(cid, jid);
			}

			// handle stop / abort / get
			if (cmd.equals(Params.JOB_CMD_STOP)) {
				return SlaveApp.jobMgr.stop(cid, jid);
			} else if (cmd.equals(Params.JOB_CMD_ABORT)) {
				return SlaveApp.jobMgr.abort(cid, jid);
			} else if (cmd.equals(Params.JOB_CMD_GET)) {
				return SlaveApp.jobMgr.get(cid, jid);
			} else {
				throw new Exception("Unknown command: " + cmd);
			}
		}
	}

	/**
	 * 创建新任务, 参数<code>args</code>包含任务类型<code>JOB_TYPE</code>和其它参数<code>ARGS</code>.
	 * <p>其它参数<code>ARGS</code>包含:
	 * <ol>
	 * <li>url 网址</li>
	 * <li>prevFetchTime 上次抓取时间,可选配置</li>
	 * <li>comment 版块名称</li>
	 * <li>provinceId 省份代码, 如果没有则为0</li>
	 * <li>cityId 城市代码, 如果没有则为0</li>
	 * <li>areaId 区域代码, 如果没有则为0</li>
	 * <li>crawlId(可选参数) 爬虫编号(唯一标识)</li>
	 * </ol>
	 */
	@Put("json")
	public Object create(Map<String, Object> args) throws Exception {
	        if (args == null)
                        return new JobCode(52, "parameters is null").toString();
	        
		JobCode jobCode = null;
		try {
			jobCode = SlaveApp.jobMgr.create(args);
		} catch (RejectedExecutionException e) {
			LOG.error(e.getMessage());
			jobCode = new JobCode(53, e.getMessage());
		} catch (Exception e) {
                        LOG.error("", e);
                }
		return jobCode.toString();
	}
}
