package com.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.common.ApiResponse;
import com.github.pagehelper.PageHelper;
import com.user.entity.User;
import com.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	//url :http://localhost:8080/user/
	// 处理"/users/"的GET请求，用来获取用户列表
	// 还可以通过@RequestParam从页面中传递参数来进行查询条件或者翻页信息的传递
	//produces可以定制返回的response的媒体类型和字符集
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	public ApiResponse<?> listUser() {
		PageHelper.startPage(1, 1);
		List<User> user = userService.listUser();
		return ApiResponse.successResponse(user);
	}

	// 处理"/users/"的POST请求，用来创建User
	// 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ApiResponse<?> postUser(@RequestBody User user) {
		 userService.put(user.getId(), user);
		 ApiResponse<?> apiResponse = ApiResponse.successResponse();
		 apiResponse.setReason("处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User处理\"/users/\"的POST请求，用来创建User");
		 return apiResponse;
	}
	
	@RequestMapping(value = "/parameter", method = RequestMethod.POST)
	public ApiResponse<?> postUser(HttpServletRequest request) {
		
		 User user = new User();
		 user.setId(request.getParameter("id"));
		 user.setAge(request.getParameter("age"));
		 user.setPassword(request.getParameter("password"));
		 user.setUserName(request.getParameter("userName"));
		 userService.put(user .getId(), user);
		 ApiResponse<?> apiResponse = ApiResponse.successResponse();
		 return apiResponse;
	}

	// 处理"/users/{id}"的GET请求，用来获取url中id值的User信息
	// url中的id可通过@PathVariable绑定到函数的参数中
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ApiResponse<?> getUser(@PathVariable String id) {
		User user = userService.get(id);
		return ApiResponse.successResponse(user);
	}

	/*// 处理"/users/{id}"的PUT请求，用来更新User信息
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ApiResponse<?> putUser( @RequestBody User user) {
		User u = userService.get(user.getId());
		u.setUserName(user.getUserName());
		u.setAge(user.getAge());
		userService.update(u.getId(), u);·
		return ApiResponse.successResponse();
	}*/

	// 处理"/users/{id}"的DELETE请求，用来删除User
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ApiResponse<?> deleteUser(@PathVariable String id) {
		userService.remove(id);
		return ApiResponse.successResponse();
	}
	
}
