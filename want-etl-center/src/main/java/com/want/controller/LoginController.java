/**
 * -------------------------------------------------------
 * @FileName：LoginController.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.want.base.service.dto.ResultDTO;
import com.want.controller.service.PasswordLoginService;
import com.want.controller.service.dto.LoginInfoDTO;
import com.want.controller.service.dto.LoginInputDTO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("login")
public class LoginController {

	@Autowired
	private PasswordLoginService passwordLoginService;

	@PostMapping(value = "/passwordLogin")
	public ResultDTO<LoginInfoDTO> passwordLogin(@RequestBody LoginInputDTO input) {
		return passwordLoginService.request(null, input);
	}
}
