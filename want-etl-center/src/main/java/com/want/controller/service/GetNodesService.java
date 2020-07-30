/**
 * -------------------------------------------------------
 * @FileName：GetNodesService.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller.service;

import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.want.base.service.ResponseDataService;
import com.want.base.service.dto.InputArgumentDTO;
import com.want.controller.service.dto.NodeDTO;
import com.want.controller.service.dto.NodeDTO.Status;
import com.want.domain.server.Agent;
import com.want.mapper.AgentMapper;

@Service
public class GetNodesService extends ResponseDataService<Void, Void, List<NodeDTO>> {

	@Autowired
	private AgentMapper agentMapper;

	@Override
	protected void checkParameters(Void parameters) throws Exception {
	}

	@Override
	protected Void parseParameters(InputArgumentDTO argument, Void parameters) throws Exception {
		return parameters;
	}

	@Override
	protected List<NodeDTO> dataAccess(InputArgumentDTO argument, Void parameters) throws Exception {
		List<NodeDTO> nodes = new ArrayList<NodeDTO>();
		List<Agent> agents = agentMapper.findAlives();
		if (agents != null && !agents.isEmpty()) {
			for (Agent agent : agents) {
				NodeDTO dto = new NodeDTO();
				dto.setId(agent.getId());
				dto.setName(agent.getName());
				dto.setAddress(InetAddress.getByName(new URL(agent.getHost()).getHost()).getHostAddress());
				dto.setActive(agent.getActive());
				dto.setMaxActive(agent.getMaxActive());
				dto.setStatus(Status.sunny);
				nodes.add(dto);
			}
		}
		return nodes;
	}

}
