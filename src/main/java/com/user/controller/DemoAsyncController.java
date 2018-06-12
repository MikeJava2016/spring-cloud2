package com.user.controller;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.common.ApiResponse;
import com.user.service.AsyncService;
/**
 * 异步方法调用的接口
 * @author hu
 *
 */
@RestController
@RequestMapping(value = "/async")
public class DemoAsyncController {
	@Autowired
	private AsyncService asyncService;

	// @ApiOperation(value="测试异步方法调用顺序", notes="getEntityById")
	@RequestMapping(value = "/getTestDemoAsync", method = RequestMethod.GET)
	public ApiResponse<?> getEntityById() throws Exception {
		long start = System.currentTimeMillis();
		Future<String> task1 = asyncService.doTaskOne();
		Future<String> task2 = asyncService.doTaskTwo();
		Future<String> task3 = asyncService.doTaskThree();
		while (true) {
			if (task1.isDone() && task2.isDone() && task3.isDone()) {
				// 三个任务都调用完成，退出循环等待
				break;
			}
			Thread.sleep(1000);
		}
		task1.get();

		long end = System.currentTimeMillis();
		System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");
		return ApiResponse.successResponse();
	}
}
